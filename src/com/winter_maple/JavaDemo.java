package com.winter_maple;

import java.util.HashMap;
import java.util.Map;

import com.winter_maple.utils.ExcelUtil;



public class JavaDemo {
	
	public static void main(String[] args) {
		ExcelUtil eu = new ExcelUtil();
		eu.open("xxx.xls");
		eu.writeExcel(0, 0, "fefeefe");
		eu.close();
	}
}

class Example {

	private static Example example = new Example();
	
	private static Map<Integer,Boolean> test = 
		new HashMap<Integer, Boolean>();
	
	private Example()
	{
		test.put(1, true);
	}
	
	public static Example getInstance()
	{
		return example;
	}
}
