package com.bizplay.refactoring.constants;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	public static final Map<String, Play> plays;
	static {
		plays = new HashMap<>();
		plays.put("hamlet", new Play("Hamlet", "tragedy"));
		plays.put("as-like", new Play("As You Uke It", "comedy"));
		plays.put("othello", new Play("othello", "tragedy"));
    }
}
