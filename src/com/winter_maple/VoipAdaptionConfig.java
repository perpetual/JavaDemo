package com.winter_maple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.tools.apt.mirror.apt.FilerImpl;
import com.sun.tools.javac.util.Pair;
import com.winter_maple.utils.JavaDemoUtil;

public class VoipAdaptionConfig {

	private static final String KEY_EXPRESSION = ".*%s name=\"(.*?)\".*";
	private static final String VALUE_EXPRESSION = ".*<%s>(\\d{1,3}?)</%s>.*";
	private static String KEYS[] = { "MANUFACTURER", "MODEL", "VERSION_RELEASE" };
	private static String VALUES[] = { "incallringstreamtype", "outcallringstreamtype",
			"phonemodenonvoip", "speakermodenonvoip", "streamtype", "speakerstreamtype", "phonemode", "speakermode", "phonemode",
			"speakermode" };

	/**
	 * @param args
	 * @throws
	 */
	public static void main(String[] args) {

		File file = new File("qcdeviceinfo.conf");
		Pattern p[] = { Pattern.compile("^\\[(deviceinfo\\d{1,4})\\]"),
				Pattern.compile("^key=(.*)"), Pattern.compile("^val=(.*)") };
		Matcher m = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String readLineString = null;
			Pattern tempPattern = null;
			Matcher tempMatcher = null;
			String tempString = null;
			ArrayList<Pair<String, String>> keyList = loadStrings("keys.txt");
			ArrayList<Pair<String, String>> valueList = loadStrings("values.txt");
			File writeFile = new File("result.txt");
			writeFile.delete();
			writeFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(writeFile, true);
			while (null != (readLineString = reader.readLine())) {
				readLineString = readLineString.trim();
				String writeLineString = "";
				for (int i = 0; i < p.length; ++i) {
					m = p[i].matcher(readLineString);
					boolean isFind = m.find();
					if (isFind && 0 == i && m.groupCount() > 0) {	//deviceid
						writeLineString += JavaDemoUtil.JAVA_SEPARATOR;
						writeLineString += m.group(1) + ";";
					} else if (isFind && 1 == i && m.groupCount() > 0) {	//
						tempString = m.group(1);
						for (int keyIndex = 0; keyIndex < keyList.size(); ++keyIndex) {
							String tempPatternString = String.format(KEY_EXPRESSION,
									keyList.get(keyIndex).fst, keyList.get(keyIndex).fst);
							// System.out.println(tempPatternString);
							tempPattern = Pattern.compile(tempPatternString);
							tempMatcher = tempPattern.matcher(tempString);
							if (tempMatcher.find() && tempMatcher.groupCount() > 0) {
								writeLineString += tempMatcher.group(1);
							}
							if (keyIndex + 1 == keyList.size()) {
								writeLineString += ";";
							} else {
								writeLineString += ";";
							}
						}
					} else if (isFind && 2 == i && m.groupCount() > 0) {
						tempString = m.group(1);
						for (int valueIndex = 0; valueIndex < valueList.size(); ++valueIndex) {
							String valueType = valueList.get(valueIndex).fst;
							String tempPatternString = String.format(VALUE_EXPRESSION,
									valueType, valueType);
							// System.out.println(tempPatternString);
							tempPattern = Pattern.compile(tempPatternString);
							tempMatcher = tempPattern.matcher(tempString);
							if (tempMatcher.find() && tempMatcher.groupCount() > 0) {
								writeLineString += valueList.get(valueIndex).snd + getValueAlias(valueType, tempMatcher.group(1));
							}
							if (valueIndex + 1 == valueList.size()) {
								writeLineString += ";";
							} else {
								writeLineString += ",";
							}
						}
					}
				}
				System.out.print(writeLineString);
				fos.write(writeLineString.getBytes());
			}

			reader.close();
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		}
	}

	private static ArrayList<Pair<String, String>> loadStrings(String str) {
		try {
			File file = new File(str);
			ArrayList<Pair<String, String>> stringList = new ArrayList<Pair<String, String>>();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String readLineString = null;
			while (null != (readLineString = reader.readLine())) {
				readLineString = readLineString.trim();
				String[] tempStrings = readLineString.split(",");
				String first = "";
				String second = "";
				if (null != tempStrings && tempStrings.length > 0 && null != tempStrings[0]) {
					first = tempStrings[0].trim();
				}
				if (null != tempStrings && tempStrings.length > 1 && null != tempStrings[1]) {
					second = tempStrings[1].trim();
				}
				Pair<String, String> pair =  new Pair<String, String>(first, second);
				stringList.add(pair);
			}
			return stringList;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}
	
	/**
	 * @param value
	 * @return
	 */
	private static String getValueAlias(String valueType, String value) {
		if (null == valueType && valueType.length() < 1) {
			return "";
		}
		valueType = valueType.trim();
		value = value.trim();
		String alias = "unknown";
		if (valueType.contains("streamtype")) {
		} else if (valueType.contentEquals("micmode")) {
			
		} else if (valueType.contentEquals("mode")) {
			
		}
		return alias = value;
	}
}
