package com.bizplay.refactoring.dto;

import java.util.List;

import lombok.Data;

@Data
public class StatementData {
	private String customer;
	private List<Performance> performances;
	private int totalAmount;
	private int totalPoint;
}
