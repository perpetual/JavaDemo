package com.winter_maple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;

import com.winter_maple.utils.JavaFileUtils;

public class CrashFilter3 {
	private static final String PARENT_PATH = "/Users/Gary/Log/20131204/";
	private static final String LINE_PREFIX = "88888.";
	private static final String LINE_POSTFIX = ".info";

	public static void main(String[] args) {
		File curDirFileName = new File(PARENT_PATH);
		File[] srcFiles = curDirFileName.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".txt");
			}
		});
		
		for (File srcFile : srcFiles) {
			Set<String> logFileNameSet = getSourceFileNameList(srcFile);
			String dirName = srcFile.getName();
			int start = dirName.indexOf(".");
			if (start > 0) {
				dirName = dirName.substring(0, start);
			}
			for (String logFileName : logFileNameSet) {
				JavaFileUtils.copyFile(PARENT_PATH + logFileName, PARENT_PATH + dirName + "/" + logFileName);
			}
			System.out.println("------------------");
		}
	}

	private static Set<String> getSourceFileNameList(final File srcFile) {
		Set<String> set = new HashSet<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(srcFile));
			String lineString = null;
			String fileName = null;
			while (null != (lineString = reader.readLine())) {
				int start = lineString.indexOf(LINE_PREFIX);
				int end = lineString.indexOf(LINE_POSTFIX) + LINE_POSTFIX.length();
				if (start >= 0 && end > 0) {
					fileName = lineString.substring(start, end);
					if (null != fileName && fileName.length() > 0) {
						set.add(fileName);
					}
				}
			}
		} catch (Exception e) {
		}
		return set;
	}
}
