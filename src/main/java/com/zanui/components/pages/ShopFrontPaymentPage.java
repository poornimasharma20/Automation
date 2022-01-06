package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontOrderReviewPageObjects;
import com.zanui.components.objects.ShopFrontPaymentPageObjects;
import com.zanui.config.ZanuiConstants;
import com.zanui.lib.main.BrowserActions;
import com.zanui.lib.main.Multimaplibraries;
import com.zanui.lib.utils.Reports;

import lombok.NoArgsConstructor;

/*************************************************************************
 * Objective: This represents the payment Page Entity
 * Parameters:
 * Author: Pooja bagga
 * Updated by and when:
 **************************************************************************/

/**
 * This class represents the payment page
 * 
 * @author Pooja Bagga
 *
 */

@NoArgsConstructor
public class ShopFrontPaymentPage extends Multimaplibraries {

	static String className = ShopFrontPaymentPage.class.getSimpleName();
	
	/**
	 * This method select CC and enter CC details
	 * 
	 * @param Scenario
	 */

	public static void EnterCreditCardDetails(String Scenario) {
		getTestData(ZanuiConstants.TestData, className);
		
		BrowserActions.isSleep();
		
		
		BrowserActions.isClick(ShopFrontPaymentPageObjects.creditCardRadioButton);
		
		final String CardholderName = getTestDataCellValue(Scenario, "CardholderName");
		//BrowserActions.isClearText(ShopFrontPaymentPageObjects.cardholderNameTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontPaymentPageObjects.cardholderNameTextbox, CardholderName);
		
		final String CardNumber = getTestDataCellValue(Scenario, "CardNumber");
		//BrowserActions.isClearText(ShopFrontPaymentPageObjects.cardNumberTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontPaymentPageObjects.cardNumberTextbox, CardNumber);
		
		final String CardMonth = getTestDataCellValue(Scenario, "CardMonth");
		BrowserActions.isSetSelectByValue(ShopFrontPaymentPageObjects.cardMonthDropdown, CardMonth);
		
		final String CardYear = getTestDataCellValue(Scenario, "CardYear");
		BrowserActions.isSetSelectByValue(ShopFrontPaymentPageObjects.cardYearDropdown, CardYear);
		
		final String CVC = getTestDataCellValue(Scenario, "CVC");
		//BrowserActions.isClearText(ShopFrontPaymentPageObjects.CVCTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontPaymentPageObjects.CVCTextbox, CVC);
		
		final String BillingEmail = getTestDataCellValue(Scenario, "BillingEmail");
		//BrowserActions.isClearText(ShopFrontPaymentPageObjects.billingEmailTextbox);
		BrowserActions.isSendKeyStroke(ShopFrontPaymentPageObjects.billingEmailTextbox, BillingEmail);
		
		BrowserActions.isSleep();

		BrowserActions.isClick(ShopFrontPaymentPageObjects.continuePaymentButton);
		
		BrowserActions.isSleep();
		final boolean objVisible = BrowserActions.isVisible(ShopFrontOrderReviewPageObjects.reviewInfoLabel);

		if (objVisible) {
			Reports.ExtentReportLog("PaymentCheckout", Status.PASS, "Payment successful", true);
		} else {
			Reports.ExtentReportLog("PaymentCheckout", Status.FAIL, "Payment Failed", true);
		}

	}
}
