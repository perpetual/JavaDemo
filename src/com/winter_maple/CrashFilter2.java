package com.winter_maple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winter_maple.utils.JavaFileUtils;

public class CrashFilter2 {
	static final String PARENT_DIR = "/Users/Gary/Log";
	static final String TODAY_DIR = "20131219";
	static final int MAX_LENGTH = 100;

	public static final String SO_TCC = "pbktcc";
	public static final String SO_CORE = "qqpbcore";
	public static final String SO_PY = "pbkpy";
	public static final String SO_XLOG = "xlog";
	public static final String SO_NETWORK = "network";
	public static final String SO_LBS = "lbs";
	public static final String SO_VAD = "vad";
	public static final String SO_TRSR_ARM = "trsr";
	public static final String SO_MMProtocal = "MMProtocalJni";
	public static final String SO_ENCRYPT = "encrypt";

	public static void main(String[] args) {
		// List<String> historyLogList =
		// JavaFileUtils.getAllFileName(PARENT_DIR,
		// JavaFileUtils.DIR_TYPE, false);
		List<String> historyLogList = new ArrayList<String>();
		historyLogList.remove(TODAY_DIR);
		Set<String> historyLogSet = new HashSet<String>(historyLogList);
		Set<String> removedLogSet = new HashSet<String>();
		for (String str : historyLogList) {
			if (Pattern.compile("(?i)[a-z]").matcher(str).find()) {
				removedLogSet.add(str);
			} else {
				System.out.println(str);
			}
		}
		historyLogSet.removeAll(removedLogSet);
		File curDir = new File(PARENT_DIR + "/" + TODAY_DIR);
		File[] files = curDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".info") && !arg1.contains("000009");
			}
		});

		if (null == files || files.length < 1) {
			return;
		}

		BufferedReader reader = null;
		String lineString = null;
		Set<String> phoneNumSet = new HashSet<String>();
		Set<String> libPhoneNumSet = new HashSet<String>();
		Set<String> selfLibPhoneNumSet = null;
		Map<String, Set<String>> selfLibMap = new HashMap<String, Set<String>>();

		try {
			for (File file : files) {
				reader = new BufferedReader(new FileReader(file));
				String stackTop = new String();
				final String stackTopPrefix = "error_";
				String lineNum = new String();
				String phoneNum = new String();
				String tempPhoneNum = new String();
				final String numberPrefix = "uin[";
				final String numberPostfix = "]";
				Set<String> fileID2Set = new TreeSet<String>();
				final String otherPrefix = new String("/lib/");
				final String otherPostfix = new String(".so");
				boolean isFirstNumber = true;
				boolean isFirstStackTop = true;
				while (null != (lineString = reader.readLine())) {
					int start = 0;
					int end = 0;
					int len = 0;
					lineString = lineString.trim();
					start = lineString.indexOf(numberPrefix)
							+ numberPrefix.length();
					end = lineString.indexOf(numberPostfix)
							+ numberPostfix.length() - 1;
					len = end - start;
					if (start >= numberPrefix.length() && end >= start && len > 0) {
						tempPhoneNum = lineString.substring(start, end);
						phoneNumSet.add(tempPhoneNum);
						if (isFirstNumber) {
							phoneNum = tempPhoneNum;
							isFirstNumber = false;
						}
					}
					if (phoneNum.length() < 1) {
						phoneNum = "0";
					}

					if (isFirstStackTop) {
						start = lineString.indexOf(stackTopPrefix);
						if (start > 0) {
							stackTop = lineString.substring(start
									+ stackTopPrefix.length(),
									lineString.length());
							if (stackTop.length() < 1) {
								stackTop = "NONE.";
							} else {
								stackTop = stackTop.replace("/", "|");
								stackTop = stackTop.replace(":", "|");
								stackTop = stackTop.replace("$", "|");
							}
							isFirstStackTop = false;
						}
					}

					if (lineString.contains("***")) {
						libPhoneNumSet.add(tempPhoneNum);
					}
					
					if (lineString.startsWith("#")
							&& lineString.contains(otherPrefix)) {
						start = lineString.indexOf(otherPrefix)
								+ otherPrefix.length();
						end = lineString.indexOf(otherPostfix)
								+ otherPostfix.length();
						String LibName = lineString.substring(start, end);
						fileID2Set.add(LibName);
						if (LibName.contains(SO_TCC)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_TCC);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_TCC, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_CORE)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_CORE);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_CORE, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_PY)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_PY);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_PY, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_XLOG)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_XLOG);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_XLOG, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_NETWORK)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_NETWORK);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_NETWORK, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_LBS)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_LBS);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_LBS, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_VAD)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_VAD);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_VAD, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_TRSR_ARM)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_TRSR_ARM);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_TRSR_ARM, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_MMProtocal)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_MMProtocal);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_MMProtocal,
										selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
						if (LibName.contains(SO_ENCRYPT)) {
							selfLibPhoneNumSet = selfLibMap.get(SO_ENCRYPT);
							if (null == selfLibPhoneNumSet) {
								selfLibPhoneNumSet = new HashSet<String>();
								selfLibMap.put(SO_ENCRYPT, selfLibPhoneNumSet);
							}
							selfLibPhoneNumSet.add(tempPhoneNum);
						}
					} else if (lineString.startsWith("at ")
							&& lineString.contains("ProGuard")) {
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
					}
				}

				if (lineNum.length() < 1) {
					for (String str : fileID2Set) {
						lineNum += str + "|";
					}
					if (lineNum.length() < 1) {
						lineNum = "0";
					}
				}

				if (stackTop.length() > MAX_LENGTH) {
					stackTop = stackTop.substring(0, MAX_LENGTH - 1);
				}
				if (lineNum.length() > MAX_LENGTH) {
					lineNum = lineNum.substring(0, MAX_LENGTH - 1);
				}
				if (phoneNum.length() > MAX_LENGTH) {
					phoneNum = phoneNum.substring(0, MAX_LENGTH - 1);
				}

				if (historyLogSet.contains(lineNum)
						|| historyLogSet.contains("_" + lineNum)) {
					if (!lineNum.equals("other") && !lineNum.equals("0")) {
						lineNum = "_" + lineNum;
					}
				}

				lineNum = stackTop + "/" + lineNum + "/" + phoneNum;

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
			System.out.println(e.toString());
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (Exception e2) {
					System.out.println("file close failed");
				}
			}
			System.out.println("" + libPhoneNumSet.size() + "/"
					+ phoneNumSet.size() + ":" + (float) libPhoneNumSet.size()
					/ phoneNumSet.size());
			for (Iterator<Map.Entry<String, Set<String>>> it = selfLibMap
					.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, Set<String>> entry = (Map.Entry<String, Set<String>>) it
						.next();
				Set<String> set = entry.getValue();
				if (set.size() > 0) {
					System.out.println(entry.getKey() + ":" + set.size() + "/" + libPhoneNumSet.size() + ":" + (float)set.size() / libPhoneNumSet.size());
				}
			}
			System.out.println(phoneNumSet);
		}
	}
}
