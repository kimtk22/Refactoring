package com.bizplay.refactoring.util;

import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;

public class ComedyCalculator extends PerformanceCalculator {
	public ComedyCalculator(Performance performance, Play play) {
		super(play, performance);
	}

	@Override
	public int amountFor() {
		int result = 30000;
		
		if(this.performance.getAudience() > 20) {
			result += 10000 + 500 * (this.performance.getAudience() - 20);
		}
		result += 300 * this.performance.getAudience();
		
		return result;
	}

	@Override
	public int volumeCreditFor() {
		int result = Math.max(this.performance.getAudience() - 30, 0);
		
		result += Math.floor(this.performance.getAudience() / 5);
		
		return result;
	}
	

}
