package com.zanui.lib.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.zanui.lib.main.Constants;
import com.zanui.lib.main.SeleniumApis;

/**
 * @author log
 *
 */
public class Reports {

	/*************************************************************************
	* Objective: Get the system current time
	* Parameters: None
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public static String currentTime() {
		String currTime;
		String timeNow = null;

		try {

			Calendar c1 = new GregorianCalendar();
			currTime = c1.getTime().toString();
			String curTime = currTime.replace(" ", "_");
			timeNow = Constants.suiteName + "_" + curTime.replace(":", ".");
		} catch (Exception e) {
			System.out.println(e);
		}
		return timeNow;
	}
	
	static String reportLocation = System.getProperty("user.dir") + File.separator + "ExtentReports" + File.separator;
	static String imageLocation = "images/";
	static String reportFolder = Reports.currentTime();
	
	/*************************************************************************
	* Objective: To Create the report with Title, Name, node and screenshot 
	* Parameters: None
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public static ExtentReports startReport() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
				Constants.ResultPath + File.separator + "Automation Report.html");
		ExtentReports extent = new ExtentReports();
		htmlReporter.config().setDocumentTitle("Automation Test Execution Report");
		htmlReporter.config().setReportName("Selenium Automation");
		htmlReporter.config().setAutoCreateRelativePathMedia(true);
		htmlReporter.config().setCSS(".r-img { width: 30%; }");
		htmlReporter.setAnalysisStrategy(AnalysisStrategy.SUITE);
		Constants.htmlReporter = htmlReporter;
		extent.attachReporter(htmlReporter);
		return extent;
	}
	
	/*************************************************************************
	* Objective: To take the screenshot
	* Parameters: driver (WebDriver)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/
	public static String createScreenShot(WebDriver driver) {
		UUID uuid = UUID.randomUUID();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// copy file object to destination location
			FileUtils.copyFile(scrFile,
					new File(reportLocation + reportFolder + File.separator + imageLocation + uuid + ".png"));

		} catch (Exception e) {
			System.out.println("Error while generating screenshot :\n" + e.toString());
		}
		return reportLocation + reportFolder + File.separator + imageLocation + uuid + ".png";
	}

	/*************************************************************************
	* Objective: To create the export report log along with node, status , message and screenshot
	* Parameters: Node (String), Status (Status), message (String), Screenshot (boolean)
	* Author: Pooja Bagga
	* Updated by and when:
	**************************************************************************/	
	public static void ExtentReportLog(String Node, Status Status, String message, boolean Screenshot) {
		if (Constants.driver != null) {
			if (Screenshot) {
				String imgPath = SeleniumApis.CaptureScreenShot(Constants.driver, Node);
				try {
					Constants.extentTest.createNode(Constants.testCaseName + "_" + Node).log(Status, message,
							MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
					Constants.logger.logp(Level.INFO, "", Constants.testCaseName, message);
				} catch (IOException e) {
					e.printStackTrace();

				}
			} else {
				Constants.extentTest.createNode(Constants.testCaseName + "_" + Node).log(Status, message);
				Constants.logger.logp(Level.INFO, "", Constants.testCaseName, message);

			}
		}
	}
	
}
