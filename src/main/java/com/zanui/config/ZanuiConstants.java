package com.zanui.config;

import java.io.File;

/**
 * @author Pooja Bagga
 *
 */
public abstract class ZanuiConstants {

	public static String ConfigPropertyFilePath = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "zanui"
			+ File.separator + "config" + File.separator
			+ "ConfigurationSettings.xlsx";
	
	public static String TestData = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "java" + File.separator + "com" + File.separator + "zanui" + File.separator
			+ "testData" + File.separator + "TestData.xlsx";
	
	public static String TestDataResultPath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "resources" + File.separator + "testData" + File.separator + "Resultsheet.xls";
	
	public static String TestDataOutput = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "java" + File.separator + "com" + File.separator + "zanui"
			+ File.separator + "testData" + File.separator + "testDataOutput"
			+ File.separator;
	
	public static String prodTestDataOutputPath = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator + "testData_Resultsheet.xls";
	
	public static String windowHandle;

}
