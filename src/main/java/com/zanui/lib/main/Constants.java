package com.zanui.lib.main;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

/**
 * @author log
 *
 */
@SuppressWarnings("deprecation")
public abstract class Constants {
	public static final String Logger = null;
	public static String toolType;
	public static String timeStamp;
	public static String ResultPath;
	public static String testCaseName;
	public static String testName;
	public static String suiteName;
	public static String TCID;
	public static String KEYWORD_PASS = "PASS";
	public static String KEYWORD_FAIL = "FAIL";
	public static String KEYWORD_NA = "NOT APPLICABLE FOR THIS CASE";
	public static String KEYWORD_OBJECTPRESENT = "OBJECT PRESENT";
	public static String KEYWORD_OBJECTABSENT = "OBJECT ABSENT";
	public static String KEYWORD_ACTIONPERFORMED = "ACTION PERFORMED";
	public static String KEYWORD_ACTIONNOTPERFORMED = "ACTION NOT PERFORMED";
	public static String OR_AttributeValue = "AttributeValue";
	public static String locator = "Locator";
	public static String locatorTYPE = "LocatorType";
	public static String locatorName = "LocatorName";
	public static String GlobalConfigPropertyFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "intershop" + File.separator + "config" + File.separator + "ConfigurationSettings.xlsx";
	public static String CurrentWindow;
	public static String testDataOutputPath = "";
	public static String testDataPath = "";
	public static String prodConfigPropertyFilePath = "";
	public static String testDataFile = "";
	public static String recordingPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "intershop" + File.separator + "config" + File.separator + "resources";
	public static String ffmpegPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "intershop" + File.separator + "config" + File.separator + "resources";
	
	//WebDriver
	public static WebDriver driver;
	
	//Extent Report
	public static ExtentTest extentTest;
	public static ExtentReporter report;
	public static ExtentReports extentReport;
	public static ExtentHtmlReporter htmlReporter;

	//TimeToWait
	public static long globalTimeOut;
	public static long defaultGlobalTimeout;
	public static long defaultGlobalStepExecutionDelay;
	public static long globalStepExecutionDelay;
	
	//Logger
	public static Logger logger;
	public static FileHandler FH = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static MultiMap<String, ?> testData = new MultiValueMap();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static MultiMap<String, ?> objectRepository = new MultiValueMap();
}
