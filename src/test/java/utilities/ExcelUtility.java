package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	private String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	public Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;
	private String value;

	public ExcelUtility(String path) throws IOException {

		this.path = path;
		fis = new FileInputStream(new File(path));
		workbook = WorkbookFactory.create(fis);
	}
	public Map<String, String> getMandatoryFieldData(String filePath, String sheetName) throws IOException {
		Map<String, String> data = new HashMap<>();

		/*
		 * FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new
		 * XSSFWorkbook(fis);
		 */
		Sheet sheet = workbook.getSheet(sheetName);
		Row headerRow = sheet.getRow(0); // First row with field names
		Row valueRow = sheet.getRow(1);  // Second row with values

		int totalColumns = headerRow.getLastCellNum();

		for (int i = 0; i < totalColumns; i++) {
			Cell headerCell = headerRow.getCell(i);
			Cell valueCell = valueRow.getCell(i);

			DataFormatter formatter = new DataFormatter();
			if (headerCell != null && valueCell != null) {
				String fieldName = headerCell.getStringCellValue().trim();
				String fieldValue = formatter.formatCellValue(valueCell).trim();

				if (!fieldName.isEmpty() && !fieldValue.isEmpty()) {
					data.put(fieldName, fieldValue);
				}
			}
		}
		return data;
	}

	public String getCellValue(String sheetName, int rowNum, int cellNum)
	{

		
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(cellNum);
		return value = cell.getStringCellValue(); 
		/*
		try {
			sheet = workbook.getSheet(sheetName);
			if (sheet == null) return null;

			row = sheet.getRow(rowNum);
			if (row == null) return null;

			cell = row.getCell(cellNum);
			if (cell == null) return null;

			return cell.getStringCellValue().trim();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} */
	}

}
