package com.winter_maple;

import java.util.HashMap;
import java.util.Map;



public class JavaDemo {
	
	public static void main(String[] args) {
		System.out.println("Hello World");
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
