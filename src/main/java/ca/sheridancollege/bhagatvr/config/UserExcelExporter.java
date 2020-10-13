package ca.sheridancollege.bhagatvr.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ca.sheridancollege.bhagatvr.beans.User;

public class UserExcelExporter {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<User> listusers;
	
	public UserExcelExporter(List<User> listusers) {
		this.listusers = listusers;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Users");
		
	}
	
	private void writeHeaderRow() {
		Row row = sheet.createRow(0);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("User ID");
		cell.setCellStyle(style);
		
		cell = row.createCell(1);
		cell.setCellValue("First Name");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue("Last Name");
		cell.setCellStyle(style);
		
		cell = row.createCell(3);
		cell.setCellValue("Phone Number");
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("Email Address");
		cell.setCellStyle(style);
	}
	
	private void writeDataRows() {
		int rowCount = 1;
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for (User user: listusers) {
			Row row = sheet.createRow(rowCount++);
			
			Cell cell  = row.createCell(0);
			cell.setCellValue(user.getId());
			sheet.autoSizeColumn(0);
			cell.setCellStyle(style);
			
			cell  = row.createCell(1);
			cell.setCellValue(user.getFirstname());
			sheet.autoSizeColumn(1);
			cell.setCellStyle(style);
			
			cell  = row.createCell(2);
			cell.setCellValue(user.getLastname());
			sheet.autoSizeColumn(2);
			cell.setCellStyle(style);
			
			cell  = row.createCell(3);
			cell.setCellValue(user.getPhonenumber());
			sheet.autoSizeColumn(3);
			cell.setCellStyle(style);
			
			cell  = row.createCell(4);
			cell.setCellValue(user.getEmail());
			sheet.autoSizeColumn(4);
			cell.setCellStyle(style);
			
		}
	}
	
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRows();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}
	
}
