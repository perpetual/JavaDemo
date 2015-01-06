package com.winter_maple.utils;

public class JavaDemoUtil {

	public static final String JAVA_SEPARATOR = System.getProperty("line.separator");

	/**
	 * 判断字符串是否为null或者为空串(先trim)
	 * 
	 * @param s
	 *            输入串
	 * @return 是否为null或者为空串
	 */
	public static boolean isNullOrEmptyWithTrim(String s) {
		return (s == null || "".equals(s.trim()));
	}
	
	/**
	 * 判断字符串是否为null或者为空串（不trim）
	 * 
	 * @param s
	 *            输入串
	 * @return 是否为null或者为空串
	 */
	public static boolean isNullOrEmptyWithoutTrim(String s) { 
		return (s == null || "".equals(s));
	} 
}
