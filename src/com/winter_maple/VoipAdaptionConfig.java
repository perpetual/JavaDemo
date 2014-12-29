package com.winter_maple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VoipAdaptionConfig {

	
	/**
	 * @param args
	 * @throws  
	 */
	public static void main(String[] args) {

		File file = new File("qcdeviceinfo.conf");
		try {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String lineString = null;
				while (null != (lineString = reader.readLine())) {
					lineString = lineString.trim();
					System.out.println(lineString);
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
	}

}
