package com.winter_maple.utils;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {
	private FileOutputStream mFOS = null;
	private HSSFWorkbook mWB = null;// 创建工作薄
	private HSSFSheet mSheet = null;// 创建工作表

	public void open(String fileName) {

		try {
			mFOS = new FileOutputStream(fileName);
			mWB = new HSSFWorkbook();// 创建工作薄
			mSheet = mWB.createSheet();// 创建工作表
			mWB.setSheetName(0, "sheet");// 设置工作表名
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void writeExcel(int rowIndex, int columnIndex, String value) {
		try {
			HSSFRow row = mSheet.getRow(rowIndex);
			if (null == row) {
				row = mSheet.createRow(rowIndex);
			}
			HSSFCell cell = row.createCell(columnIndex, HSSFCell.CELL_TYPE_STRING);// 新增一列
			cell.setCellValue(value);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void close() {
		try {
			mWB.write(mFOS);
			mFOS.close();			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
