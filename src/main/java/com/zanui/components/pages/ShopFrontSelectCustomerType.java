package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontAddressPageObjects;
import com.zanui.components.objects.ShopFrontSelectCustomerTypePageObjects;
import com.zanui.lib.main.BrowserActions;
import com.zanui.lib.main.Multimaplibraries;
import com.zanui.lib.utils.Reports;

import lombok.NoArgsConstructor;

/*************************************************************************
 * Objective: This represents the product detail page entity
 * Parameters:
 * Author: Pooja bagga
 **************************************************************************/

/**
 * This class represents the product detail page
 * 
 * @author Pooja Bagga
 *
 */
@NoArgsConstructor
public class ShopFrontSelectCustomerType extends Multimaplibraries {

	/**
	 * This method select continue to checkout as guest option
	 * 
	 */

	public static void checkoutAsGuest() {
		BrowserActions.isSleep();
		BrowserActions.isClick(ShopFrontSelectCustomerTypePageObjects.checkoutAsGuestButton);
		BrowserActions.isSleep();

		final boolean objVisible = BrowserActions.isVisible(ShopFrontAddressPageObjects.shippingInfoLabel);

		if (objVisible) {
			Reports.ExtentReportLog("CheckoutAsGuest", Status.PASS, "Checkout as guest is successful", true);
		} else {
			Reports.ExtentReportLog("CheckoutAsGuest", Status.FAIL, "Checkout as guest is Failed", true);
		}

	}
	
}
