package com.bizplay.refactoring.controller;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bizplay.refactoring.dto.Invoice;
import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;
import com.bizplay.refactoring.dto.StatementData;

@Controller
@RequestMapping("/api/v2/invoices")
public class RefactoringController2 {
	private final Map<String, Play> plays;
	
	public RefactoringController2(){
		plays = new HashMap<>();
		plays.put("hamlet", new Play("Hamlet", "tragedy"));
		plays.put("as-like", new Play("As You Uke It", "comedy"));
		plays.put("othello", new Play("othello", "tragedy"));
	}
	
	
	@PostMapping("/text")
	@ResponseBody
	public String text(@RequestBody Invoice invoice) throws Exception {
		StatementData statementData = getStatementData(invoice);
		return renderPlainText(statementData);
	}
	
	@PostMapping("/html")
	public ModelAndView html(@RequestBody Invoice invoice, ModelAndView mav) throws Exception {
		StatementData statementData = getStatementData(invoice);
		mav.setViewName("Bill");
		mav.addObject("data", statementData);
		return mav;
	}
	
	private StatementData getStatementData(Invoice invoice) throws Exception {
		StatementData statementData = new StatementData();
		statementData.setCustomer(invoice.getCustomer());
		statementData.setPerformances(invoice.getPerformances().stream().map((performance) -> {
			try {
				performance.setPlayName(playFor(performance).getName());
				performance.setAmount(amountFor(performance));
			}catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			
			return performance;
		}).toList());
		statementData.setTotalAmount(getTotalAmount(statementData));
		statementData.setTotalPoint(getTotalPoint(statementData));
		
		return statementData;
	}
	
	private String renderPlainText(StatementData data) throws Exception{
		String result = String.format("청구 내역 (고객명: %s)\n", data.getCustomer());
		
		for(Performance performance : data.getPerformances()) {
			result += String.format(" %s: %s (%d)석\n", performance.getPlayName(), usd(performance.getAmount()), performance.getAudience());
		}
		
		result += String.format("총액: %s\n", usd(data.getTotalAmount()));
		result += String.format("적립 포인트: %d점\n", data.getTotalPoint());
		return result;
	}
	
	private Play playFor(Performance perf) throws Exception{
		Play result = plays.get(perf.getPlayID());

		if(result == null) {
			throw new Exception("알 수 없는 공연 ID : " + perf.getPlayID());
		}
		
		return plays.get(perf.getPlayID());
	}

	private int amountFor(Performance performance) throws Exception{
		int result = 0;
		
		switch(playFor(performance).getType()) {
			case "tragedy":
				result = 40000;
				if(performance.getAudience() > 30) {
					result += 1000 * (performance.getAudience() -30);
				}
				break;
			case "comedy":
				result = 30000;
				if(performance.getAudience() > 20) {
					result += 10000 + 500 * (performance.getAudience() - 20);
				}
				result += 300 * performance.getAudience();
				break;
			default:
				throw new Exception("알 수 없는 장르: " + playFor(performance).getType());
		}
		
		return result;
	}
	
	private int volumeCreditFor(Performance performance) throws Exception{
		int result = 0;
		
		result += Math.max(performance.getAudience() - 30, 0);
		if(playFor(performance).getType().equals("comedy")) {
			result += Math.floor(performance.getAudience() / 5);
		} 
		
		return result;
	}
	
	private String usd(int number) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		formatter.setMinimumFractionDigits(2);
		
		return formatter.format(number / 100);
	}
	
	private int getTotalPoint(StatementData data) throws Exception {
		int result = 0;
		
		for(Performance performance : data.getPerformances()) {
			result += volumeCreditFor(performance);
		}
		
		return result;
	}
	
	private int getTotalAmount(StatementData data) throws Exception{
		int result = 0;
		
		for(Performance perf : data.getPerformances()) {
			result += amountFor(perf);
		}
		
		return result;
	}
}
