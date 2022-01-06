package com.zanui.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsReader {

	public static String filename = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Data"; 
	public String path;
	public static FileInputStream fis = null;
	public static FileOutputStream fileOut = null;
	private static XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	
	/*************************************************************************
	* Objective: Constructor for XlsReader
	* Parameters: path (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	
	public XlsReader(String path) {
		  this.path = path;
		  try {
			  fis = new FileInputStream(path);
			  workbook = new XSSFWorkbook(fis);
			  sheet = workbook.getSheetAt(0);
			  fis.close();
		  } catch (Exception e) {
			  e.printStackTrace();		  
		  }
	}
	
	/*************************************************************************
	* Objective: To get the count of rows in a sheet
	* Parameters: sheetName (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if(index == -1) {
			return 0;
		}
		else {
			sheet = workbook.getSheetAt(index);
			int rowCount = sheet.getLastRowNum() + 1;
			return rowCount;
		}
	}
	
	/*************************************************************************
	* Objective: To get the column count in a sheet
	* Parameters: sheetName (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public int getColCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		int noOfColumns = sheet.getRow(index).getPhysicalNumberOfCells() + 1;
		if (noOfColumns == -1) 
			return -1;
		else
			return noOfColumns;
	}
	
	/*************************************************************************
	* Objective: To get the value of a cell based on row number & column name
	* Parameters: sheetName (String), rowNum (int), colName (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public String getCellData(String sheetName, int rowNum, String colName) {
		try {
			if (rowNum <= 0)
				return "";
			
			int index = workbook.getSheetIndex(sheetName);
			int col_num = -1;
			if(index == -1)
				return "";
			
			sheet = workbook.getSheetAt(index);
			row =sheet.getRow(0);
			
			for(int i=0; i < row.getLastCellNum(); i++) {
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_num = i;
					break;
				}
			}
			if(col_num == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if(row == null)
				return "";
			cell = row.getCell(col_num);
			if(cell == null)
				return "";
			if(cell.getCellType() == CellType.STRING)
			return cell.getStringCellValue();
			else if(cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if(cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";	
		}
	}
	
	/*************************************************************************
	* Objective: To get the value of a cell based on row number & column number
	* Parameters: sheetName (String), rowNum (int), colNum (int)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			
			int index = workbook.getSheetIndex(sheetName);
			if(index == -1)
				return "";
			
			sheet = workbook.getSheetAt(index);
			row =sheet.getRow(rowNum - 1);
			if(row == null)
				return "";
			cell = row.getCell(colNum);
			if(cell == null)
				return "";
			
			if(cell.getCellType() == CellType.STRING)
			return cell.getStringCellValue();
			else if(cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if(cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist in xls";	
		}
	}
	
	/*************************************************************************
	* Objective: To get the row number of a specified cell
	* Parameters: sheetName (String), colName (String), cellValue (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		for(int i = 2; i<getRowCount(sheetName); i++) {
			if(getCellData(sheetName, i, colName).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}
	
	/*************************************************************************
	* Objective: To write the data to excel
	* Parameters: path (String), sheetName (String), scenarioName (String), colName (String), data (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	
	public static void writeToTestData(String path, String sheetName, String scenarioName, String colName, String data) {
		try {
			XlsReader excel = new XlsReader(path); 
			int rowCount = excel.getRowCount(sheetName); 
			int i=1; 
			int j=1;
			FileInputStream fis = new FileInputStream(path); 
			Workbook wb = WorkbookFactory.create(fis); 
			Sheet s = wb.getSheet(sheetName); 
			Row r = s.getRow(0); 
			int colNum = r.getLastCellNum();
			for(i = 1; i<=rowCount; i++) {
				String value = excel.getCellData(sheetName, 0, i); 
				if (value.equalsIgnoreCase(scenarioName)) {
					r = s.getRow(i); 
					break;
				} 
			}
			for (j = 1; j<=colNum; j++) {
				String valuel = excel.getCellData(sheetName, j,1); 
				if (valuel.equalsIgnoreCase(colName)) {
					r = s.getRow(j); 
					break;
				}
			}
			r = s.getRow(i-1); 
			Cell c = r.getCell(j);
			c.setCellValue(data); 
			FileOutputStream fos = new FileOutputStream(path); 
			wb.write(fos);
			fos.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
