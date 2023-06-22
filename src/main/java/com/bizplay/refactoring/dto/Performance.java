package com.bizplay.refactoring.dto;

import lombok.Data;

@Data
public class Performance {
	private String playID;
	private int audience;
	
	private String playName;
	private int amount;
	private int point;
}
