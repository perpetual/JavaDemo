package com.winter_maple.utils;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {
	private FileOutputStream mFOS = null;
	private HSSFWorkbook mWB = null;// ����������
	private HSSFSheet mSheet = null;// ����������

	public void open(String fileName) {

		try {
			mFOS = new FileOutputStream(fileName);
			mWB = new HSSFWorkbook();// ����������
			mSheet = mWB.createSheet();// ����������
			mWB.setSheetName(0, "sheet");// ���ù�������
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
			HSSFCell cell = row.createCell(columnIndex, HSSFCell.CELL_TYPE_STRING);// ����һ��
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
