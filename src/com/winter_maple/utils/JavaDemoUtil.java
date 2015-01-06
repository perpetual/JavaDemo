package com.winter_maple.utils;

public class JavaDemoUtil {

	public static final String JAVA_SEPARATOR = System.getProperty("line.separator");

	/**
	 * �ж��ַ����Ƿ�Ϊnull����Ϊ�մ�(��trim)
	 * 
	 * @param s
	 *            ���봮
	 * @return �Ƿ�Ϊnull����Ϊ�մ�
	 */
	public static boolean isNullOrEmptyWithTrim(String s) {
		return (s == null || "".equals(s.trim()));
	}
	
	/**
	 * �ж��ַ����Ƿ�Ϊnull����Ϊ�մ�����trim��
	 * 
	 * @param s
	 *            ���봮
	 * @return �Ƿ�Ϊnull����Ϊ�մ�
	 */
	public static boolean isNullOrEmptyWithoutTrim(String s) { 
		return (s == null || "".equals(s));
	} 
}
