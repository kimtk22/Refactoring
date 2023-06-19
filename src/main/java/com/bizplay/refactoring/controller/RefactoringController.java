package com.bizplay.refactoring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizplay.refactoring.constants.Constant;
import com.bizplay.refactoring.constants.Play;
import com.bizplay.refactoring.dto.Invoice;
import com.bizplay.refactoring.dto.Performance;

@RestController
@RequestMapping("/api/invoices")
public class RefactoringController {
	
	@PostMapping()
	public String invoices(@RequestBody List<Invoice> invoices) {
		Map<String, Play> plays = new HashMap<>();
		plays.put("hamlet", new Play("Hamlet", "tragedy"));
		plays.put("as-like", new Play("As You Uke It", "comedy"));
		plays.put("othello", new Play("othello", "tragedy"));
		
		
		
		
		return "";
	}
	
	private String statement(Invoice invoice, Map<String, Play> plays) {
		int totalAmount = 0;
		int volumeCredits = 0;
		String result = String.format("청구내역 (고객명: %s)\n", invoice.getCustomer());
		
		for(Performance performance : invoice.getPerformances()) {
			Play play = plays.get(performance.getPlayID());
			int thisAmount = 0;
			
		}
	}
}
