package com.winter_maple;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winter_maple.utils.JavaFileUtils;

public class CrashFilter {

	public static void main(String[] args) {
		String parentDir = "/Users/Gary/Log";
		String todayDir = "20130814";

		List<String> historyLogList = JavaFileUtils.getAllFileName(parentDir, JavaFileUtils.DIR_TYPE, false);
		historyLogList.remove(todayDir);
		Set<String> historyLogSet = new HashSet<String>(historyLogList);
		for (String str : historyLogList) {
			System.out.println(str);
		}
		File curDir = new File(parentDir + "/" + todayDir);		
		File[] files = curDir.listFiles(new FilenameFilter() {			
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".info") && !arg1.contains("56000009");
			}
		});
		
		if (null == files || files.length < 1) {
			return;
		}
		
		BufferedReader reader = null;
		String lineString = null;
		try {
			for (File file : files) {
				reader = new BufferedReader(new FileReader(file));
				final String stackTopPrefix = "error_";
				String lineNum = new String();
				String phoneNum = new String();
				final String numberPrefix = "uin[";
				final String numberPostfix = "]";
				Set<String> fileID2Set = new TreeSet<String>();
				final String otherPrefix = new String("/lib/");
				final String otherPostfix = new String(".so");
				boolean isFirst = true;
				while (null != (lineString = reader.readLine())) {
					int start = 0;
					int end = 0;
					lineString = lineString.trim();

					if (lineString.startsWith("at ") && lineString.contains("ProGuard")) {
						Pattern p = Pattern.compile("\\((.*:((.*)))\\)");
						if (null == p) {
							continue;
						}
						Matcher m = p.matcher(lineString);
						while (m.find()) {
							int groupCount = m.groupCount();
							for (int i = 2; i < groupCount; ++i) {
								lineNum += m.group(i);
							}
						}
					} else if (lineString.startsWith("#") && lineString.contains(otherPrefix)) {
						start = lineString.indexOf(otherPrefix) + otherPrefix.length();
						end = lineString.indexOf(otherPostfix) + otherPostfix.length();
						fileID2Set.add(lineString.substring(start, end));
					}
				}

				if (lineNum.length() < 1) {
					String temp = new String();
					for (String str : fileID2Set) {
						temp += str + "|";
					}
					lineNum = "other/" + temp;
				} else if (lineNum.length() > 100) {
					lineNum = lineNum.substring(100);
				}
				
				if (historyLogSet.contains(lineNum) || historyLogSet.contains("_" + lineNum)) {
					if (!lineNum.equals("other")) {
						lineNum = "_" + lineNum;
					}
				}

				String newFilePath = file.getParent();
				newFilePath += "/" + lineNum + "/" + file.getName();
				if (JavaFileUtils.copyFile(file.getPath(), newFilePath)) {
					System.out.println(newFilePath);
					file.delete();
				} else {
					System.out.println(false);
				}
			}
		} catch (Exception e) {
			System.out.println("file read failed");
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (Exception e2) {
					System.out.println("file close failed");
				}
			}
		}
	}
}
