package com.bizplay.refactoring.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bizplay.refactoring.constant.Genre;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PerformanceCalculatorFactory {
	private final List<PerformanceCalculator> calculators;
	
	public PerformanceCalculator getCalculator(Genre genre) {
		return calculators.stream().filter(calculator -> calculator.getGenre().equals(genre))
							.findFirst()
							.orElseThrow(() -> new RuntimeException());
	}
}
