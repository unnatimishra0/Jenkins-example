package com.nagarro.training.service;

import java.util.List;

import com.nagarro.training.exceptions.AccountDetailsNotFoundException;
import com.nagarro.training.exceptions.NoCustomerException;
import com.nagarro.training.exceptions.UpdateNotFoundException;
import com.nagarro.training.model.Customer;

public interface CustomerService {

	// Add customer
	Customer saveCustomer(Customer customer);

	// Fetch all customer
	List<Customer> getAllCustomers();

	// Fetch single customer details
	Customer getCustomer(Long id) throws NoCustomerException;

	// Upadte customer details
	Customer updateCustomerAndAccount(Customer customerDetails) throws UpdateNotFoundException;

	Customer getCustomerDetailsfromaccountNumber(String accountNumber) throws NoCustomerException;

	void deleteCustomerAndAccount(Long customerId) throws NoCustomerException;

	Customer addCustomerWithAccount(Customer customerDetails) throws AccountDetailsNotFoundException;

	boolean validateCustomerDetails(Customer customerDetails);

	Customer getCustomerDetails(Long customerId) throws NoCustomerException;

}
