package com.bizplay.refactoring.controller;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizplay.refactoring.dto.Invoice;
import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;

@RestController
@RequestMapping("/api/v1/invoices")
public class RefactoringController {
private final Map<String, Play> plays;
	
	public RefactoringController(){
		plays = new HashMap<>();
		plays.put("hamlet", new Play("Hamlet", "tragedy"));
		plays.put("as-like", new Play("As You Uke It", "comedy"));
		plays.put("othello", new Play("othello", "tragedy"));
	}
	
	@PostMapping()
	public String invoices(@RequestBody Invoice invoice) throws Exception {
		return statement(invoice, plays);
	}
	
	private String statement(Invoice invoice, Map<String, Play> plays) throws Exception {
		int totalAmount = 0;
		int totalPoint = 0;
		String result = String.format("청구 내역 (고객명: %s)\n", invoice.getCustomer());
		
		// 달러 형식 포매터
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		formatter.setMinimumFractionDigits(2);
		
		for(Performance perf : invoice.getPerformances()) {
			Play play = plays.get(perf.getPlayID());
			int thisAmount = 0;
			
			// 장르에 따라 금액 계산
			switch(play.getType()) {
				case "tragedy":
					thisAmount = 40000;
					if(perf.getAudience() > 30) {
						thisAmount += 1000 * (perf.getAudience() -30);
					}
					break;
				case "comedy":
					thisAmount = 30000;
					if(perf.getAudience() > 20) {
						thisAmount += 10000 + 500 * (perf.getAudience() - 20);
					}
					thisAmount += 300 * perf.getAudience();
					break;
				default:
					throw new Exception("알 수 없는 장르: " + play.getType());
			}
			
			// 포인트 계산
			totalPoint += Math.max(perf.getAudience() - 30, 0);
			if(play.getType().equals("comedy")) totalPoint += Math.floor(perf.getAudience() / 5);
			
			result += String.format(" %s: %s (%d)석\n", play.getName(), formatter.format(thisAmount / 100), perf.getAudience());
			totalAmount += thisAmount;
		}
		
		result += String.format("총액: %s\n", formatter.format(totalAmount / 100));
		result += String.format("적립 포인트: %d점\n", totalPoint);
		return result;
	}
}
