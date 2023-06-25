package com.bizplay.refactoring.controller;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizplay.refactoring.constant.Genre;
import com.bizplay.refactoring.constant.Plays;
import com.bizplay.refactoring.dto.Invoice;
import com.bizplay.refactoring.dto.Performance;
import com.bizplay.refactoring.dto.Play;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/invoices")
public class RefactoringController {
	private final Plays plays;

	@PostMapping
	@ResponseBody
	public String invoices(@RequestBody Invoice invoice) throws Exception {
		return statement(invoice);
	}
	
	private String statement(Invoice invoice) throws Exception {
		// 초기 변수
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
			switch(play.getGenre()) {
				case TRAGEDY:
					thisAmount = 40000;
					if(perf.getAudience() > 30) {
						thisAmount += 1000 * (perf.getAudience() -30);
					}
					break;
				case COMEDY:
					thisAmount = 30000;
					if(perf.getAudience() > 20) {
						thisAmount += 10000 + 500 * (perf.getAudience() - 20);
					}
					thisAmount += 300 * perf.getAudience();
					break;
				default:
					throw new Exception("알 수 없는 장르: " + play.getGenre());
			}
			
			// 포인트 계산
			totalPoint += Math.max(perf.getAudience() - 30, 0);
			if(play.getGenre().equals(Genre.COMEDY)) totalPoint += Math.floor(perf.getAudience() / 5);
			
			// 각 공연 정보(이름, 금액, 관객 수)
			result += String.format(" %s: %s (%d)석\n", play.getName(), formatter.format(thisAmount / 100), perf.getAudience());
			
			// 총 금액 계산
			totalAmount += thisAmount;
		}
		
		result += String.format("총액: %s\n", formatter.format(totalAmount / 100));
		result += String.format("적립 포인트: %d점\n", totalPoint);
		return result;
	}
}
