package com.bizplay.refactoring.util;

import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class PerformanceCalculator {
	Play play;
	Performance performance;
	
	public abstract int amountFor();
	public abstract int volumeCreditFor();
}
