package com.bizplay.refactoring;

import static org.junit.jupiter.api.Assertions.*;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class SwitchTest {

	@Test
	void test() {
		double yourNumber = 1234.56;
        
        Locale locale = new Locale("en", "US");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        formatter.setMinimumFractionDigits(2);
        
        String formattedValue = formatter.format(yourNumber);
        
        System.out.println(String.format(" %s: %s (%d)석\n", "Hamlet", formatter.format(10000 / 100), 50));
	}

}
