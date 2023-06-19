package com.bizplay.refactoring.dto;

import java.util.List;

import lombok.Data;

@Data
public class Invoice {
	private String customer;
	private List<Performance> performances;
}
