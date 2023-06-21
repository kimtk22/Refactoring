package com.bizplay.refactoring.dto;

import lombok.Data;

@Data
public class Performance {
	private String playID;
	private String playName;
	private int audience;
	private int amount;
	private int point;
}
