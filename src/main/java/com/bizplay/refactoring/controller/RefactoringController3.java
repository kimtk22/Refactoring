package com.bizplay.refactoring.controller;

import java.util.HashMap;
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
import com.bizplay.refactoring.util.ComedyCalculator;
import com.bizplay.refactoring.util.Currency;
import com.bizplay.refactoring.util.PerformanceCalculator;
import com.bizplay.refactoring.util.TragedyCalculator;

@Controller
@RequestMapping("/api/v3/invoices")
public class RefactoringController3 {
	private final Map<String, Play> plays;
	
	public RefactoringController3(){
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
				PerformanceCalculator calculator = createPerformanceCalculator(performance, playFor(performance));
				performance.setPlayName(playFor(performance).getName());
				performance.setAmount(calculator.amountFor());
				performance.setPoint(calculator.volumeCreditFor());
			}catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
			
			return performance;
		}).toList());
		statementData.setTotalAmount(getTotalAmount(statementData));
		statementData.setTotalPoint(getTotalPoint(statementData));
		
		return statementData;
	}
	
	private PerformanceCalculator createPerformanceCalculator(Performance performance, Play play) throws Exception {
		switch(play.getType()) {
			case "tragedy" :
				return new TragedyCalculator(performance, play);
			case "comedy" :
				return new ComedyCalculator(performance, play);
			default:
				throw new Exception("알 수 없는 장르: " + play.getType()); 
		}
		
	}
	
	private String renderPlainText(StatementData data) throws Exception{
		String result = String.format("청구 내역 (고객명: %s)\n", data.getCustomer());
		
		for(Performance performance : data.getPerformances()) {
			result += String.format(" %s: %s (%d)석\n", performance.getPlayName(), Currency.usd(performance.getAmount()), performance.getAudience());
		}
		
		result += String.format("총액: %s\n", Currency.usd(data.getTotalAmount()));
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

	private int getTotalPoint(StatementData data) throws Exception {
		int result = 0;
		
		for(Performance performance : data.getPerformances()) {
			result += performance.getPoint();
		}
		
		return result;
	}
	
	private int getTotalAmount(StatementData data) throws Exception{
		int result = 0;
		
		for(Performance performance : data.getPerformances()) {
			result += performance.getAmount();
		}
		
		return result;
	}
}
