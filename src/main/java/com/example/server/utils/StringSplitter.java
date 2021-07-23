package com.example.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSplitter {

	public static int compareStrings(String string1, String string2) {
		List<Object> splitFile1 = splitString(string1);
		List<Object> splitFile2 = splitString(string2);

		if (splitFile1.size() == 1 && splitFile2.size() == 1) { //for two strings
			return string1.compareTo(string2);
		}

		for (int i = 0; i < splitFile1.size() && i < splitFile2.size(); i++) {
			Object obj1 = splitFile1.get(i);
			Object obj2 = splitFile2.get(i);
			if (obj1 instanceof String && obj2 instanceof String) {
				String str1 = (String) obj1;
				String str2 = (String) obj2;
				if (str1.length() < str2.length() && str2.startsWith(str1)) return 1;
				if (str2.length() < str1.length() && str1.startsWith(str2)) return 1;
				int code = str1.compareTo(str2);
				if (code != 0) return code;
			} else if (obj1 instanceof Long && obj2 instanceof String) {
				return 1;
			} else if (obj1 instanceof String && obj2 instanceof Long) {
				return -1;
			} else if (obj1 instanceof Long && obj2 instanceof Long) {
				Long num1 = (Long) obj1;
				Long num2 = (Long) obj2;
				int code = num1.compareTo(num2);
				if (code != 0) return code;
			}
		}
		return 0;
	}

	public static List<Object> splitString(String string) {
		StringBuilder builder = new StringBuilder(string);
		List<Object> list = new ArrayList<>();
		Pattern numberPatter = Pattern.compile("^\\d+");
		Pattern stringPattern = Pattern.compile("^\\D+");

		while (builder.length() > 0) {
			Matcher matcherNumber = numberPatter.matcher(builder);
			Matcher matcherString = stringPattern.matcher(builder);
			if (matcherNumber.find()) {
				String number = builder.substring(0, matcherNumber.end());
				list.add(Long.parseLong(number));
				builder.delete(0, number.length());
			} else if (matcherString.find()) {
				String substring = builder.substring(0, matcherString.end());
				list.add(substring);
				builder.delete(0, substring.length());
			}
		}
		return list;
	}
}
