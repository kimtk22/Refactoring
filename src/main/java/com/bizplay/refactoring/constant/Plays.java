package com.bizplay.refactoring.constant;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bizplay.refactoring.dto.Play;

@Component
public class Plays {
	private final Map<String, Play> plays;
	
	public Plays(){
		plays = new HashMap<>();
		plays.put("hamlet", new Play("Hamlet", Genre.TRAGEDY));
		plays.put("as-like", new Play("As You Uke It", Genre.COMEDY));
		plays.put("othello", new Play("othello", Genre.TRAGEDY));
	}
	
	public Play get(String PerformanceId) {
		return plays.get(PerformanceId);
	}
}
