package com.bizplay.refactoring.dto;

import com.bizplay.refactoring.constant.Genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Play {
	private String name;
	private Genre genre;
}
