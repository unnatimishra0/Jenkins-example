package com.nagarro.training.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Customer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long custId;
	 private String firstName;
	 private String lastName;
	 private String email;
	private String accountNumber;
	
	transient AccountDetails accountDetails;





}
