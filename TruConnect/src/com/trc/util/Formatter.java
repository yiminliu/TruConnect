package com.trc.util;

public final class Formatter {

	public static String formatDollarAmount(Double inAmount) {
		return formatDollarAmount(Double.toString(inAmount));
	}

	public static String formatDollarAmount(String inAmount) {
		String[] amount = inAmount.split("\\.");
		if (amount.length > 1) {
			switch (amount[1].length()) {
			case 0:
				amount[1] = "00";
				break;
			case 1:
				amount[1] = amount[1] + "0";
				break;
			case 2:
				break;
			default:
				return null;
			}
			return amount[0] + "." + amount[1];
		}
		return inAmount + ".00";
	}
}
