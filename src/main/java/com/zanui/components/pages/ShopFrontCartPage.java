package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontCartPageObjects;
import com.zanui.components.objects.ShopFrontHomePageObjects;
import com.zanui.lib.main.BrowserActions;
import com.zanui.lib.main.Multimaplibraries;
import com.zanui.lib.utils.Reports;

import lombok.NoArgsConstructor;

/*************************************************************************
 * Objective: This represents the shopping cart page entity
 * Parameters:
 * Author: Pooja bagga
 **************************************************************************/


/**
* This class represents the cart page
* 
* @author Pooja Bagga
*
*/
@NoArgsConstructor
public class ShopFrontCartPage extends Multimaplibraries{
	static String className = ShopFrontProductDetailPage.class.getSimpleName();

	/**
	 * This method add product into cart
	 * 
	 */

	public static void ProceedToCheckout() {
		BrowserActions.isSleep();
		BrowserActions.isClick(ShopFrontCartPageObjects.checkoutButton);
		BrowserActions.isSleep();

		final boolean objVisible = BrowserActions.isVisible(ShopFrontHomePageObjects.myAccountLink);

		if (objVisible) {
			Reports.ExtentReportLog("ProceedToCheckout", Status.PASS, "Proceed to Checkout Successful", true);
		} else {
			Reports.ExtentReportLog("ProceedToCheckout", Status.FAIL, "Proceed to Checkout failed", true);
		}

	}
	
}
