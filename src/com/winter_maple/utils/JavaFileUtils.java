package com.winter_maple.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;



public class JavaFileUtils {
	public static boolean copyFile(String srcPath, String destPath) {

		FileChannel in = null;
		FileChannel out = null;
		try {
			File file = new File(destPath);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			in = new FileInputStream(srcPath).getChannel();
			File outFile = new File(destPath);
			out = new FileOutputStream(outFile).getChannel();
			in.transferTo(0, in.size(), out);
			return true;
		} catch (Throwable e) {
			return false;
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	public static final int FILE_TYPE = 0;
	public static final int DIR_TYPE = 1;
	public static final int ALL_TYPE = 2;

	public static List<String> getAllFileName(String path, final int type,
			boolean isFullPath) {
		return getAllFileName(path, type, isFullPath, null);
	}

	public static List<String> getAllFileName(String path, final int type,
			boolean isFullPath, FilenameFilter fileNameFilter) {
		File file = new File(path);
		List<String> list = new ArrayList<String>();

		if (file.isFile()) {
			if (isFullPath) {
				list.add(file.getAbsolutePath());
			} else {
				list.add(file.getName());
			}
			return list;
		} else if (file.isDirectory()) {
			List<String> pathList = new ArrayList<String>();
			traverseDirectory(file.getAbsolutePath(), isFullPath, type,
					pathList, fileNameFilter);
			list.addAll(pathList);
			return list;
		}
		return new ArrayList<String>();
	}

	/**
	 * Ë½ÓÐº¯Êý
	 */
	private static void traverseDirectory(String parentPath,
			boolean isFullPath, final int type, List<String> pathList,
			FilenameFilter fileNameFilter) {
		if (null == pathList) {
			return;
		}

		File file = new File(parentPath);
		if (file.isDirectory()) {
			if (ALL_TYPE == type || DIR_TYPE == type) {
				if (isFullPath) {
					pathList.add(file.getAbsolutePath());
				} else {
					pathList.add(file.getName());
				}
			}
			File[] files = null;
			if (null == fileNameFilter) {
				files = file.listFiles();
			} else {
				files = file.listFiles(fileNameFilter);
			}
			for (File tempFile : files) {
				traverseDirectory(tempFile.getAbsolutePath(), isFullPath, type,
						pathList, fileNameFilter);
			}
		} else if (file.isFile() && (ALL_TYPE == type || FILE_TYPE == type)) {
			if (isFullPath) {
				pathList.add(file.getAbsolutePath());
			} else {
				pathList.add(file.getName());
			}
		}
	}
}
