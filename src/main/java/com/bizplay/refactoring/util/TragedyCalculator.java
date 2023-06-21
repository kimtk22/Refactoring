package com.bizplay.refactoring.util;

import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;

public class TragedyCalculator extends PerformanceCalculator{

	public TragedyCalculator(Performance performance, Play play) {
		super(play, performance);
	}

	@Override
	public int amountFor() {
		int result = 40000;
		
		if(this.performance.getAudience() > 30) {
			result += 1000 * (this.performance.getAudience() -30);
		}
		
		return result;
	}

	@Override
	public int volumeCreditFor() {
		return Math.max(performance.getAudience() - 30, 0);
	}
	
}
