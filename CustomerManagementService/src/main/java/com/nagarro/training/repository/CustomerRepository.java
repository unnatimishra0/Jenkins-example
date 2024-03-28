package com.nagarro.training.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.training.model.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long>{
	
	Optional <Customer> findByAccountNumber(String accountNumber);

	boolean existsByAccountNumber(String accountNumber);

}
