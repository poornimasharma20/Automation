package com.zanui.lib.main;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aventstack.extentreports.Status;
import com.zanui.lib.utils.Reports;
import com.zanui.lib.utils.XlsReader;

/**
 * @author Pooja Bagga
 *
 */
public class Multimaplibraries {
	
	/*************************************************************************
	 * Objective: To read the data present in a excel sheet
	 * Parameters: testDataPath (String), sheetName (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	@SuppressWarnings("deprecation")
	public static void getTestData(String testDataPath, String sheetName) {
		try {
			Constants.testData.clear();
			XlsReader excel = new XlsReader(testDataPath); 
			FileInputStream fis = new FileInputStream(testDataPath); 
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(fis); 
			XSSFSheet sheet = workbook.getSheet(sheetName); 
			XSSFRow row = sheet.getRow(0); 
			int colNum = row.getLastCellNum(); 
			System.out.println("Total Number of Columns: " + colNum);
			int TD_Rowcount = excel.getRowCount(sheetName); 
			for (int i = 1; i <= TD_Rowcount; i++) {
				for (int j = 1; j < colNum; j++) {
					String columnName = excel.getCellData(sheetName, j, 1); 
					String testDataName = excel.getCellData(sheetName, i, "Scenario"); 
					String testDataValue = excel.getCellData(sheetName, i, columnName); 
					Constants.testData.put(testDataName, testDataValue);
				}
			}
			System.out.println("Adding TestData to multimap completed"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*************************************************************************
	* Objective: To get the data of a complete row based on scenario name
	* Parameters: scenarioName (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static ArrayList<String> getTestDataRowValue(String scenarioName) {
		ArrayList<String> object = null; 
		try {
			object = (ArrayList<String>) Constants.testData.get(scenarioName); 
		} catch (Exception e) {
			System.out.println("There is no object by the name: " + scenarioName);
		}
		return object;
	}

	/*************************************************************************
	* Objective: To get value of each cell based on scenario & column name
	* Parameters: scenarioName (String), colName (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public static String getTestDataCellValue(String scenarioName, String colName) {
		String value = null; 
		ArrayList<String> object = Multimaplibraries.getTestDataRowValue(scenarioName); 
		ArrayList<String> scenario = Multimaplibraries.getTestDataRowValue("Scenario"); 
		int index = -1; 
		for (int i = 0; i < scenario.size(); i++) { 
			if (scenario.get(i).equals(colName)) {
				index = i; 
				break;
			}
		}
		try {
			value = object.get(index);
		} catch (Exception e) {
			e.printStackTrace(); 
			value = null; 
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Column '" + colName + "' does not exist in test data file", true);
		}
		return value;
	}

	/*************************************************************************
	* Objective: To get names of all the scenarios present in a sheet
	* Parameters: testDataPath (String), sheetName (String)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public static ArrayList<String> getScenarioList(String testDataPath, String sheetName) {
		ArrayList<String> list = new ArrayList<String>();
		String value = null;
		XlsReader excel = new XlsReader(testDataPath);
		int rowCount = excel.getRowCount(sheetName);

		for(int j=2; j<rowCount+1; j++) {
			value = excel.getCellData(sheetName, j, "Scenario");
			list.add(value);
		}
		return list;
	}



}
