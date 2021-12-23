package com.denisio.app.utils;

public enum SortingDirection {
	DESC, ASC;

	public static SortingDirection safeValueOf(final String value) {
		try {
			return SortingDirection.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException ex) {
			return ASC;
		}
	}
}