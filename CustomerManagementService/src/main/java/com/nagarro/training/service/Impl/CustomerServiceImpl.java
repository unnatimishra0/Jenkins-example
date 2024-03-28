package com.nagarro.training.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.training.exceptions.NoCustomerException;
import com.nagarro.training.exceptions.UpdateNotFoundException;
import com.nagarro.training.exceptions.AccountDetailsNotFoundException;
import com.nagarro.training.exceptions.IllegalArgumentException;

import com.nagarro.training.model.AccountDetails;
import com.nagarro.training.model.Customer;
import com.nagarro.training.repository.CustomerRepository;
import com.nagarro.training.service.AccountClient;
import com.nagarro.training.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountClient accountClient;

	@Override
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomer(Long id) throws NoCustomerException {
		// TODO Auto-generated method stub
		return customerRepository.findById(id).orElseThrow(() -> new NoCustomerException("customer with id not found"));
	}

	@Transactional
	public Customer updateCustomerAndAccount(Customer customer) throws UpdateNotFoundException {
		// Validate customer ID
		if (customer.getCustId() == null) {
			throw new UpdateNotFoundException("Customer ID cannot be null for the update operation.");
		}

		// Fetch and update the existing customer
		Customer existingCustomer = customerRepository.findById(customer.getCustId())
				.orElseThrow(() -> new UpdateNotFoundException(
						"Cannot update non-existing customer with ID: " + customer.getCustId()));

		if (customer.getFirstName() != null) {
			existingCustomer.setFirstName(customer.getFirstName());
		}
		if (customer.getLastName() != null) {
			existingCustomer.setLastName(customer.getLastName());

		}
		customerRepository.save(existingCustomer);
		System.out.println(existingCustomer + "hii");

		AccountDetails updatedAccountDetails = accountClient.updateAccount(existingCustomer);

		return existingCustomer;
	}

	@Override
	public Customer getCustomerDetailsfromaccountNumber(String accountNumber) throws NoCustomerException {
		// TODO Auto-generated method stub
		return customerRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new NoCustomerException("Customer not found"));
	}

	public void deleteCustomerAndAccount(Long customerId) throws NoCustomerException {
		// check if the customer exists before attempting deletion
		if (customerRepository.existsById(customerId)) {
			accountClient.deleteAccountwithCustomer(customerId);

			customerRepository.deleteById(customerId);
		} else {
			throw new NoCustomerException("Customer not found with id: " + customerId);
		}
	}

	@Override
	public Customer addCustomerWithAccount(Customer customerDetails) throws AccountDetailsNotFoundException {
		String accountNumber = customerDetails.getAccountNumber();
		if (!isValidAccountNumberFormat(accountNumber)) {
			throw new AccountDetailsNotFoundException("Account number must be of 10 digits and contain only numbers.");
		}
		if (customerRepository.existsByAccountNumber(accountNumber)) {
			throw new AccountDetailsNotFoundException("Account number already exists.");
		}
		Customer customer = new Customer();
		customer.setCustId(customerDetails.getCustId());
		customer.setAccountNumber(accountNumber);
		customer.setFirstName(customerDetails.getFirstName());
		customer.setLastName(customerDetails.getLastName());

		customer = customerRepository.save(customer);

		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setCustId(customer.getCustId());
		accountDetails.setAccountNumber(accountNumber);

		AccountDetails createdAccount = accountClient.addAccount(accountDetails);

		customerDetails.setCustId(customer.getCustId());
		customerDetails.setAccountNumber(createdAccount.getAccountNumber());
		return customerDetails;
	}

	private boolean isValidAccountNumberFormat(String accountNumber) {
		return accountNumber != null && accountNumber.matches("\\d{10}");
	}

	@Override
	public boolean validateCustomerDetails(Customer customerDetails) {
		System.out.println("customer details : " + customerDetails.getCustId());
		Optional<Customer> customer = customerRepository.findByAccountNumber(customerDetails.getAccountNumber());
		if (customer.isPresent())
			return true;
		return false;

	}

	private boolean matches(Customer customer, Customer customerDetails) {
		if (!customer.getFirstName().equalsIgnoreCase(customerDetails.getFirstName())) {
			return false;
		}
		if (!customer.getAccountNumber().equals(customerDetails.getAccountNumber())) {
			return false;
		}
		return true;
	}

	@Override
	public Customer getCustomerDetails(Long customerId) throws NoCustomerException {
		return customerRepository.findById(customerId).map(customer -> {
			Customer details = new Customer();
			details.setCustId(customer.getCustId());
			details.setFirstName(customer.getFirstName());
			details.setLastName(customer.getLastName());
			details.setEmail(customer.getEmail());
			return details;
		}).orElseThrow(() -> new NoCustomerException("Customer not found with ID: " + customerId));
	}
}
