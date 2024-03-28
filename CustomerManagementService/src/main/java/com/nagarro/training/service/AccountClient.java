package com.nagarro.training.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nagarro.training.model.AccountDetails;
import com.nagarro.training.model.Customer;

@FeignClient(name = "Account-Service")
public interface AccountClient {
	// deletes account if customer is deleted
	@DeleteMapping("accounts/customers/{cust_id}")
	AccountDetails deleteAccountwithCustomer(@PathVariable Long cust_id);

	@PostMapping("/accounts")
	AccountDetails addAccount(@RequestBody AccountDetails accountDetails);

	@PutMapping("/accounts/update")
	AccountDetails updateAccount(@RequestBody Customer existingCustomer);

	@GetMapping("/accounts/{customerId}")
	AccountDetails getAccountDetailsByCustomerId(@PathVariable("customerId") Long customerId);

}