package com.denisio.app.utils;

public enum OrderBy {
	PRICE,
	TARIFF_PLAN_NAME;

	public static OrderBy safeValueOf(final String value) {
		try {
			return OrderBy.valueOf(String.join("_", value.split(" ")).toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			return TARIFF_PLAN_NAME;
		}
	}
}