package com.nagarro.training.model;

import lombok.Data;

@Data
public class AccountDetails {

	private long id;

	private String accountNumber;

	private double currentBalance;

	private String bankName;

	private String ownerName;

	private Long custId;

}
