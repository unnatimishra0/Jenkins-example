package com.nagarro.training.dto;

import com.nagarro.training.model.AccountDetails;
import com.nagarro.training.model.Customer;

import lombok.Data;
@Data
public class CustomerAccountUpdateRequest {
	
	 private Customer customer;
	    private AccountDetails accountDetails;

}
