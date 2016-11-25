package com.georgiev.xml.xml_app;

import java.io.FileOutputStream;

import javax.xml.bind.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.georgiev.xml.entity.Customer;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Start");
		JAXBContext jc = JAXBContext.newInstance(Customer.class);

		Customer customer = new Customer();
		customer.setFirstName("Jane");
		customer.setLastName("Doe");
		customer.setID(123);
		customer.setAddress("test");

		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(customer, System.out);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow((short) 0);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("Default Palette");

		// apply some colors from the standard palette,
		// as in the previous examples.
		// we'll use red text on a lime background

		HSSFCellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIME.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		style.setFont(font);

		cell.setCellStyle(style);

		// save with the default palette
		FileOutputStream out = new FileOutputStream("default_palette.xls");
		wb.write(out);
		out.close();

		// now, let's replace RED and LIME in the palette
		// with a more attractive combination
		// (lovingly borrowed from freebsd.org)

		cell.setCellValue("Modified Palette");

		// creating a custom palette for the workbook
		HSSFPalette palette = wb.getCustomPalette();

		// replacing the standard red with freebsd.org red
		palette.setColorAtIndex(HSSFColor.RED.index, (byte) 153, // RGB red
																	// (0-255)
				(byte) 0, // RGB green
				(byte) 0 // RGB blue
		);
		// replacing lime with freebsd.org gold
		palette.setColorAtIndex(HSSFColor.LIME.index, (byte) 255, (byte) 204, (byte) 102);

		// save with the modified palette
		// note that wherever we have previously used RED or LIME, the
		// new colors magically appear
		out = new FileOutputStream("modified_palette.xls");
		wb.write(out);
		out.close();
		
		
		
		 //save with the default palette
	    FileOutputStream out1 = new FileOutputStream("Xdefault_palette.xlsx");
	    XSSFWorkbook wb1 = new XSSFWorkbook();
	    XSSFSheet sheet1 = wb1.createSheet();
	    XSSFRow row1 = sheet1.createRow(0);
	    XSSFCell cell1 = row1.createCell( 0);
	    cell1.setCellValue("custom XSSF colors");

	    XSSFCellStyle style1 = wb1.createCellStyle();
	    style1.setFillForegroundColor(new XSSFColor(new java.awt.Color(128, 0, 128)));
	    style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	    
	    
	    wb1.write(out1);
	    out1.close();
	}

}
