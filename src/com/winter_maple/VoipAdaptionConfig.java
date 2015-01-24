package com.winter_maple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winter_maple.utils.ExcelUtil;
import com.winter_maple.utils.JavaDemoUtil;
import com.winter_maple.utils.SamplePair;

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
		Pattern p[] = { Pattern.compile("^\\[deviceinfo(\\d{1,4})\\]"),
				Pattern.compile("^key=(.*)"), Pattern.compile("^val=(.*)") };
		Matcher m = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String readLineString = null;
			Pattern tempPattern = null;
			Matcher tempMatcher = null;
			String tempString = null;
			ExcelUtil excelUtil = new ExcelUtil();
			int excelRowIndex = 1;
			int excelColumnIndex = 0;
			excelUtil.open("VoipAdaptionConfig.xls");
			excelUtil.writeExcel(0, 0, "deviceID");
			excelUtil.writeExcel(0, 1, "厂商");
			excelUtil.writeExcel(0, 2, "机型");
			excelUtil.writeExcel(0, 3, "版本");
			excelUtil.writeExcel(0, 4, "适配项");
			ArrayList<SamplePair<String, String>> keyList = loadStrings("keys.txt");
			ArrayList<SamplePair<String, String>> valueList = loadStrings("values.txt");
			File writeFile = new File("result.txt");
			writeFile.delete();
			writeFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(writeFile, true);
			while (null != (readLineString = reader.readLine())) {
				readLineString = readLineString.trim();
				String writeLineString = "";
				String excelString = "";
				for (int i = 0; i < p.length; ++i) {
					m = p[i].matcher(readLineString);
					boolean isFind = m.find();
					if (isFind && 0 == i && m.groupCount() > 0) {	//deviceid
						if (writeFile.length() > 0) {
							writeLineString += JavaDemoUtil.JAVA_SEPARATOR;
							++excelRowIndex;
							excelColumnIndex = 0;
						}
						excelString = m.group(1);
						excelUtil.writeExcel(excelRowIndex, excelColumnIndex++, excelString);
						writeLineString += excelString + ";";
					} else if (isFind && 1 == i && m.groupCount() > 0) {	//
						tempString = m.group(1);
						for (int keyIndex = 0; keyIndex < keyList.size(); ++keyIndex) {
							String tempPatternString = String.format(KEY_EXPRESSION,
									keyList.get(keyIndex).mFirst, keyList.get(keyIndex).mSecond);
							
							excelString = "";
							tempPattern = Pattern.compile(tempPatternString);
							tempMatcher = tempPattern.matcher(tempString);
							if (tempMatcher.find() && tempMatcher.groupCount() > 0) {
								excelString += tempMatcher.group(1);
							}
							excelUtil.writeExcel(excelRowIndex, excelColumnIndex++, excelString);
							if (keyIndex + 1 == keyList.size()) {
								excelString += ";";
							} else {
								excelString += ";";
							}
							writeLineString += excelString;
						}
					} else if (isFind && 2 == i && m.groupCount() > 0) {
						tempString = m.group(1);
						excelString = "";
						for (int valueIndex = 0; valueIndex < valueList.size(); ++valueIndex) {
							String valueType = valueList.get(valueIndex).mFirst;
							String tempPatternString = String.format(VALUE_EXPRESSION,
									valueType, valueType);
							tempPattern = Pattern.compile(tempPatternString);
							tempMatcher = tempPattern.matcher(tempString);
							if (tempMatcher.find() && tempMatcher.groupCount() > 0) {
								excelString += getValueAlias(valueType, valueList.get(valueIndex).mSecond, tempMatcher.group(1));
							}
							if (valueIndex + 1 == valueList.size()) {
								excelUtil.writeExcel(excelRowIndex, excelColumnIndex++, excelString);
								excelString += ";";
							} else {
								excelString += ",";
							}
						}
						writeLineString += excelString;
					}
				}
				System.out.print(writeLineString);
				fos.write(writeLineString.getBytes());
			}

			reader.close();
			fos.flush();
			fos.close();
			excelUtil.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		}
	}

	private static ArrayList<SamplePair<String, String>> loadStrings(String str) {
		try {
			File file = new File(str);
			ArrayList<SamplePair<String, String>> stringList = new ArrayList<SamplePair<String, String>>();
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
				if (!JavaDemoUtil.isNullOrEmptyWithTrim(first)) {
					SamplePair<String, String> pair =  new SamplePair<String, String>(first, second);
					stringList.add(pair);
				}
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
	private static String getValueAlias(String valueType, String valueAlias, String value) {
		if (JavaDemoUtil.isNullOrEmptyWithTrim(valueType)) {
			return "";
		}
		valueType = valueType.trim();
		value = value.trim();
		String alias = null;
		alias = JavaDemoUtil.isNullOrEmptyWithTrim(valueAlias) ? valueType : valueAlias;

		return alias + ":" + value;
	}
}
