package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontHomePageObjects;
import com.zanui.components.objects.ShopFrontProductDetailPageObjects;
import com.zanui.config.ZanuiConstants;
import com.zanui.lib.main.BrowserActions;
import com.zanui.lib.main.Multimaplibraries;
import com.zanui.lib.utils.Reports;

import lombok.NoArgsConstructor;


/*************************************************************************
 * Objective: This represents the home page entity
 * Parameters:
 * Author: Pooja bagga
 * Updated by and when:
 **************************************************************************/

/**
 * This class represents the home page
 * 
 * @author Pooja Bagga
 *
 */
@NoArgsConstructor
public class ShopFrontHomePage extends Multimaplibraries {

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
    	String titleofPage= BrowserActions.isGetTitle();
    	System.out.println(titleofPage);
    	
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
    	
    	/**
    	 * This method search product on SF
    	 * 
    	 * @param Scenario
    	 */
    	public static void SerachProduct(String Scenario)
    	{
    		
    		getTestData(ZanuiConstants.TestData, className);
			final String productName = getTestDataCellValue(Scenario, "product");
    		
    		BrowserActions.isSetValue(ShopFrontHomePageObjects.searchTextBox, productName);
    		BrowserActions.isSleep();
    		BrowserActions.isClick(ShopFrontHomePageObjects.searchSuggestion);
    		//BrowserActions.pressEnter(ShopFrontHomePageObjects.searchTextBox);
    		
    		BrowserActions.isSleep();
    		
    		String productNameLabel = BrowserActions.isGetText(ShopFrontProductDetailPageObjects.productNameLabel);
    		if (productName.contentEquals(productNameLabel)) {
    			Reports.ExtentReportLog("SearchProduct", Status.PASS, "Search successful", true);
    		} else {
    			Reports.ExtentReportLog("SearchProduct", Status.FAIL, "Search Failed", true);
    		}
    		
    	}
    	
    	/**
    	 * This method logout from SF
    	 * 
    	 * @param Scenario
    	 */
    	public static void LogoutFromStoreFront()
    	{
    		BrowserActions.isClick(ShopFrontHomePageObjects.myAccountLink);
    		BrowserActions.isClick(ShopFrontHomePageObjects.signoutButton);
    		BrowserActions.isSleep();
    		
    		
    		final boolean objVisible = BrowserActions.isVisible(ShopFrontHomePageObjects.myAccountLink);

    		if (objVisible) {
    			Reports.ExtentReportLog("StorefrontLogout", Status.PASS, "Logout successful", true);
    		} else {
    			Reports.ExtentReportLog("StorefrontLogout", Status.FAIL, "Logout Failed", true);
    		}
    		
    	}
    	
    	/**
    	 * This method test invalid login
    	 * 
    	 * @param Scenario
    	 */
    	
    	public static void InvalidLogin(String Scenario)
    	{
    		
        	getTestData(ZanuiConstants.TestData, className);
    		final String userName = getTestDataCellValue(Scenario, "userName");
    	    final String password = getTestDataCellValue(Scenario, "password");
    		BrowserActions.isClick(ShopFrontHomePageObjects.myAccountLink);
    		BrowserActions.isSetValue(ShopFrontHomePageObjects.emailAddressTextbox, userName);
    		BrowserActions.isSetValue(ShopFrontHomePageObjects.passwordTextBox, password);
    		BrowserActions.isClick(ShopFrontHomePageObjects.signInButton);
    		
    		BrowserActions.isSleep();
    		final boolean invalidLoginLabel = BrowserActions.isVisible(ShopFrontHomePageObjects.invalidLoginLabel);
    		String invalidLoginMsg = BrowserActions.isGetText(ShopFrontHomePageObjects.invalidLoginLabel);
    		
    		if (invalidLoginLabel && invalidLoginMsg.contains("Your email address or password is incorrect")) {
    			Reports.ExtentReportLog("StorefrontInvalidLogin", Status.PASS, "Invalid login unsuccessful", true);
    		} else {
    			Reports.ExtentReportLog("StorefrontInvalidLogin", Status.FAIL, "Invalid login successful", true);
    		}
    		
    	}
    	
    	
}
