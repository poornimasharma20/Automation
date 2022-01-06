package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontHomePageObjects;
import com.zanui.config.ZanuiConstants;
import com.zanui.lib.main.BrowserActions;
import com.zanui.lib.main.Multimaplibraries;
import com.zanui.lib.utils.Reports;

import lombok.NoArgsConstructor;

/*************************************************************************
 * Objective: This represents the Invalid Login entity
 * Parameters:
 * Author: Pooja bagga
 * Updated by and when:
 **************************************************************************/

/**
 * This class represents the invalid login
 * 
 * @author Pooja Bagga
 *
 */
@NoArgsConstructor

public class ShopFrontInvalidLogin extends Multimaplibraries {

static String className = ShopFrontHomePage.class.getSimpleName();
	
	/**
	 * This method navigates the control to {@link HomePage}}
	 * 
	 * @param Scenario
	 */
	public static void navigateToHomePage(String Scenario) {
		getTestData(ZanuiConstants.TestData, className);
		
		final String URL = getTestDataCellValue(Scenario, "URL");
    	BrowserActions.isNavigate(URL);  	
    	BrowserActions.isSleep();
    	
    	final boolean objVisible = BrowserActions.isVisible(ShopFrontHomePageObjects.myAccountLink);
    	if (objVisible) {
			Reports.ExtentReportLog("StorefrontLaunch", Status.PASS, "Application Launched successful", true);
		} else {
			Reports.ExtentReportLog("StorefrontLauncht", Status.FAIL, "Application Launched Failed", true);
		}
		
	}
    	
	/**
	 * This method login into SF
	 * 
	 * @param Scenario
	 */
    	public static void LoginToStoreFront(String Scenario)
    	{
    		getTestData(ZanuiConstants.TestData, className);
			final String userName = getTestDataCellValue(Scenario, "userName");
	    	final String password = getTestDataCellValue(Scenario, "password");
		BrowserActions.isClick(ShopFrontHomePageObjects.myAccountLink);
		BrowserActions.isSetValue(ShopFrontHomePageObjects.emailAddressTextbox, userName);
		BrowserActions.isSetValue(ShopFrontHomePageObjects.passwordTextBox, password);
		BrowserActions.isClick(ShopFrontHomePageObjects.signInButton);
		
		BrowserActions.isSleep();
		final boolean objVisible = BrowserActions.isVisible(ShopFrontHomePageObjects.myAccountLink);

		if (objVisible) {
			Reports.ExtentReportLog("StorefrontLogin", Status.PASS, "Logout successful", true);
		} else {
			Reports.ExtentReportLog("StorefrontLogint", Status.FAIL, "Logout Failed", true);
		}
		
	}
}
