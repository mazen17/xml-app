package com.georgiev.xml.xml_app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.georgiev.xml.entity.CopySheets;
import com.georgiev.xml.entity.SheetCopyUtil;

public class SheetsTest {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		FileInputStream in = new FileInputStream("C:/test/test.xlsx");
		Workbook wb = WorkbookFactory.create(in);
		Sheet sheetOld = wb.getSheetAt(0);

		
		FileOutputStream out1 = new FileOutputStream("C:/test/test2.xlsx");
		XSSFWorkbook wb1 = new XSSFWorkbook();
		XSSFSheet sheetNew = wb1.createSheet();

		CopySheets.copySheets(sheetNew, sheetOld);

		wb1.write(out1);
		out1.close();
	}

}
