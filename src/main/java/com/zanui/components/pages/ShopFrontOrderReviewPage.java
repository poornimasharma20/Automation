package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontOrderReviewPageObjects;
import com.zanui.lib.main.BrowserActions;
import com.zanui.lib.main.Multimaplibraries;
import com.zanui.lib.utils.Reports;

import lombok.NoArgsConstructor;

/*************************************************************************
 * Objective: This represents the order review page entity
 * Parameters:
 * Author: Pooja bagga
 * Updated by and when:
 **************************************************************************/

/**
 * This class represents the order review page
 * 
 * @author Pooja Bagga
 *
 */

@NoArgsConstructor
public class ShopFrontOrderReviewPage extends Multimaplibraries {
	static String className = ShopFrontOrderReviewPage.class.getSimpleName();

	/**
	 * This method add product into cart
	 * 
	 * @param Scenario
	 */
	public static void PlaceOrder() {
		BrowserActions.isSleep();
		BrowserActions.isClick(ShopFrontOrderReviewPageObjects.completePurchaseButton);
		BrowserActions.isSleep();

		String pageActualTitle = BrowserActions.isGetTitle();
		System.out.println(pageActualTitle);
		String pageExpectedTitle = "Thank you for shopping at Zanui!";
		

		if (pageActualTitle.equals(pageExpectedTitle)) {
			Reports.ExtentReportLog("ReviewOrder", Status.PASS, "Review Order successful", true);
		} else {
			Reports.ExtentReportLog("ReviewOrder", Status.FAIL, "Review Order Failed", true);
		}

	}
}
