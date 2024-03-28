package com.nagarro.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.training.model.Customer;
import com.nagarro.training.service.CustomerService;
import com.nagarro.training.exceptions.AccountDetailsNotFoundException;
import com.nagarro.training.exceptions.NoCustomerException;
import com.nagarro.training.exceptions.UpdateNotFoundException;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

//get single customer details
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getSingleUser(@PathVariable Long id) throws NoCustomerException {
		Customer customer = customerService.getCustomer(id);
		return ResponseEntity.ok(customer);
	}

//get all customers
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomer() {
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}


	// update customer details
	@PutMapping("/{customerId}/update")
	public ResponseEntity<?> updateCustomerAndAccount(@PathVariable Long customerId, @RequestBody Customer customer)
			throws UpdateNotFoundException {
		// Setting the customer ID from the path variable ensures we are updating the
		// correct customer.
		customer.setCustId(customerId);
		System.out.print(customerId);


		Customer updatedCustomer = customerService.updateCustomerAndAccount(customer);
		System.out.print(updatedCustomer);
		return ResponseEntity.ok(updatedCustomer);


	}

	// delete customer and account
	@DeleteMapping("accounts/{cust_id}")
	public ResponseEntity<String> deleteCustomerAndAccount(@PathVariable Long cust_id) throws NoCustomerException {
		customerService.deleteCustomerAndAccount(cust_id);
		return ResponseEntity.ok().body("Customer and associated account successfully deleted");

	}

	// add customer and account

	@PostMapping("/add-with-account")
	public ResponseEntity<?> addCustomerWithAccount(@RequestBody Customer customerDetails)
			throws AccountDetailsNotFoundException {
		Customer updatedCustomerDetails = customerService.addCustomerWithAccount(customerDetails);
		return ResponseEntity.ok(updatedCustomerDetails);

	}

//called using feign client
	@PostMapping("/validate")
	public ResponseEntity<Boolean> validateCustomerDetails(@RequestBody Customer customerDetails) {
		System.out.println(customerDetails.getAccountNumber());
		boolean isValid = customerService.validateCustomerDetails(customerDetails);
		return ResponseEntity.ok(isValid);
	}
	// called using feign client by account service

	@GetMapping("/details/{customerId}")
	public ResponseEntity<Customer> getCustomerDetails(@PathVariable Long customerId) throws NoCustomerException {

		Customer customerDetails = customerService.getCustomerDetails(customerId);
		return ResponseEntity.ok(customerDetails);

	}

	// called using feign client by account service
	@GetMapping("/account/{accountNumber}")

	public ResponseEntity<Customer> getCustomerDetailsfromaccountNumber(@PathVariable String accountNumber)
			throws NoCustomerException {
		Customer customer = customerService.getCustomerDetailsfromaccountNumber(accountNumber);
		return ResponseEntity.ok(customer);
	}

}
