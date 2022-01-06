package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontAddressPageObjects;
import com.zanui.components.objects.ShopFrontPaymentPageObjects;
import com.zanui.config.ZanuiConstants;
import com.zanui.lib.main.BrowserActions;
import com.zanui.lib.main.Multimaplibraries;
import com.zanui.lib.utils.Reports;

import lombok.NoArgsConstructor;

/*************************************************************************
 * Objective: This represents the Address Page Entity
 * Parameters:
 * Author: Pooja bagga
 * Updated by and when:
 **************************************************************************/

/**
 * This class represents the address page
 * 
 * @author Pooja Bagga
 *
 */

@NoArgsConstructor
public class ShopFrontAddressPage extends Multimaplibraries {
	static String className = ShopFrontAddressPage.class.getSimpleName();
	
	/**
	 * This method fill data on address form
	 * 
	 * @param Scenario
	 */
	public static void FillAddressData(String Scenario) {
		getTestData(ZanuiConstants.TestData, className);
		
		BrowserActions.isSleep();
		
		final String FirstName = getTestDataCellValue(Scenario, "FirstName");
		//BrowserActions.isClearText(ShopFrontAddressPageObjects.firstNameTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontAddressPageObjects.firstNameTextbox, FirstName);
		
		final String LastName = getTestDataCellValue(Scenario, "LastName");
		//BrowserActions.isClearText(ShopFrontAddressPageObjects.lastNameTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontAddressPageObjects.lastNameTextbox, LastName);
		
		final String Address1 = getTestDataCellValue(Scenario, "Address1");
		//BrowserActions.isClearText(ShopFrontAddressPageObjects.address1Textbox);
		BrowserActions.isSendKeyStroke(ShopFrontAddressPageObjects.address1Textbox, Address1);
		
		final String Suburb = getTestDataCellValue(Scenario, "Suburb");
		//BrowserActions.isClearText(ShopFrontAddressPageObjects.suburbTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontAddressPageObjects.suburbTextbox, Suburb);
		
		final String Postcode = getTestDataCellValue(Scenario, "Postcode");
		//BrowserActions.isClearText(ShopFrontAddressPageObjects.postcodeTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontAddressPageObjects.postcodeTextbox, Postcode);
		
		final String Phonenumber = getTestDataCellValue(Scenario, "Phonenumber");
		//BrowserActions.isClearText(ShopFrontAddressPageObjects.phoneTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontAddressPageObjects.phoneTextbox, Phonenumber);
		
		final String State = getTestDataCellValue(Scenario, "State");
		//BrowserActions.isClearText(ShopFrontAddressPageObjects.stateDropDown);
		BrowserActions.isSetSelectByValue(ShopFrontAddressPageObjects.stateDropDown, State);
		
		BrowserActions.isSleep();
		BrowserActions.isClick(ShopFrontAddressPageObjects.continueButton);

		final boolean objVisible = BrowserActions.isVisible(ShopFrontPaymentPageObjects.paymentInfoLabel);

		if (objVisible) {
			Reports.ExtentReportLog("AddressCheckout", Status.PASS, "Address checkout is successful", true);
		} else {
			Reports.ExtentReportLog("AddressCheckout", Status.FAIL, "Address checkout is Failed", true);
		}

	}
}
  