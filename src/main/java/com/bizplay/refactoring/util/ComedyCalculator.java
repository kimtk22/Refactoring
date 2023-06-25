package com.bizplay.refactoring.util;

import org.springframework.stereotype.Component;

import com.bizplay.refactoring.constant.Genre;
import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;

import lombok.Getter;

@Component
public class ComedyCalculator extends PerformanceCalculator {
	public ComedyCalculator() {
		super(Genre.COMEDY);
	}

	@Override
	public int getAmount(Performance performance) {
		int result = 30000;
		
		if(performance.getAudience() > 20) {
			result += 10000 + 500 * (performance.getAudience() - 20);
		}
		result += 300 * performance.getAudience();
		
		return result;
	}

	@Override
	public int getPoint(Performance performance) {
		int result = Math.max(performance.getAudience() - 30, 0);
		
		result += Math.floor(performance.getAudience() / 5);
		
		return result;
	}
	

}
