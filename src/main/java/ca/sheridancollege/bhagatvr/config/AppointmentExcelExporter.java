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

import ca.sheridancollege.bhagatvr.beans.Appointment;

public class AppointmentExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private List<Appointment> listappointments;

	public AppointmentExcelExporter(List<Appointment> listappointments) {
		this.listappointments = listappointments;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Appointments");

	}

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		Cell cell = row.createCell(0);
		cell.setCellValue("Appointment ID");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("First Name");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("Last Name");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("Email Address");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("Appointment Date");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("Appointment Time");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("Animal Type");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("Services");
		cell.setCellStyle(style);
	}

	private void writeDataRows() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (Appointment appointment : listappointments) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(appointment.getId());
			sheet.autoSizeColumn(0);
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(appointment.getUser().getFirstname());
			sheet.autoSizeColumn(1);
			cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue(appointment.getUser().getLastname());
			sheet.autoSizeColumn(2);
			cell.setCellStyle(style);

			cell = row.createCell(3);
			cell.setCellValue(appointment.getUser().getEmail());
			sheet.autoSizeColumn(3);
			cell.setCellStyle(style);

			cell = row.createCell(4);
			cell.setCellValue(appointment.getAppointmentDate().toString());
			sheet.autoSizeColumn(4);
			cell.setCellStyle(style);

			cell = row.createCell(5);
			cell.setCellValue(appointment.getAppointmentTime());
			sheet.autoSizeColumn(5);
			cell.setCellStyle(style);

			cell = row.createCell(6);
			cell.setCellValue(appointment.getBreed());
			sheet.autoSizeColumn(6);
			cell.setCellStyle(style);

			cell = row.createCell(7);
			cell.setCellValue(appointment.getServices());
			sheet.autoSizeColumn(7);
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
