package com.bizplay.refactoring.util;

import java.text.NumberFormat;
import java.util.Locale;

public final class Currency {
	public static String usd(int number) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		formatter.setMinimumFractionDigits(2);
		
		return formatter.format(number / 100);
	}
}
