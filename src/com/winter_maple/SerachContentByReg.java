package com.winter_maple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winter_maple.utils.JavaFileUtils;

public class SerachContentByReg {

	static final String PARENT_DIR = "/Users/Gary/Log";
	static final int GROUP_INDEX = 2;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> filePathList = JavaFileUtils.getAllFileName(PARENT_DIR,
				JavaFileUtils.FILE_TYPE, true, new FilenameFilter() {

					@Override
					public boolean accept(File arg0, String arg1) {
						if (arg0.isDirectory()) {
							return true;
						} else {
							return arg1.endsWith(".info");
						}
					}
				});

		BufferedReader reader = null;
		String lineString = null;
//		Pattern p = Pattern.compile("(.*c1:\\[(.+?)\\])");
		Pattern p = Pattern.compile("(.*(lib.*\\.so))");
		Set<String> resultSet = new HashSet<String>();
		
		for (String str : filePathList) {
			try {
				reader = new BufferedReader(new FileReader(new File(str)));
				while (null != (lineString = reader.readLine())) {
					if (null == p) {
						continue;
					}
					Matcher m = p.matcher(lineString);
					while (m.find()) {
						int groupCount = m.groupCount();
						System.out.println(m.group(GROUP_INDEX));
						int resultCount = resultSet.size();
						resultSet.add(m.group(GROUP_INDEX));
						if (resultCount < resultSet.size()) {
							System.out.println(m.group(GROUP_INDEX));
						}
					}
				}
			} catch (Exception e) {
			}
		}
		System.out.println(resultSet);
	}

}
