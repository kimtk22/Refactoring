package com.bizplay.refactoring.util;

import com.bizplay.refactoring.constant.Genre;
import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class PerformanceCalculator {
	private Genre genre;
	
	public abstract int getAmount(Performance performance);
	public abstract int getPoint(Performance performance);
}
