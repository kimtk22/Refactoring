package com.bizplay.refactoring.util;

import org.springframework.stereotype.Component;

import com.bizplay.refactoring.constant.Genre;
import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;

import lombok.Getter;

@Component
public class TragedyCalculator extends PerformanceCalculator{

	public TragedyCalculator() {
		super(Genre.TRAGEDY);
	}

	@Override
	public int getAmount(Performance performance) {
		int result = 40000;
		
		if(performance.getAudience() > 30) {
			result += 1000 * (performance.getAudience() -30);
		}
		
		return result;
	}

	@Override
	public int getPoint(Performance performance) {
		return Math.max(performance.getAudience() - 30, 0);
	}
	
}
