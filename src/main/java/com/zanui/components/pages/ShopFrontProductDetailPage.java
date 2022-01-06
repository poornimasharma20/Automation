package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontHomePageObjects;
import com.zanui.components.objects.ShopFrontProductDetailPageObjects;
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
public class ShopFrontProductDetailPage extends Multimaplibraries {

	static String className = ShopFrontProductDetailPage.class.getSimpleName();

	/**
	 * This method add product into cart
	 * 
	 */

	public static void AddToCart() {
		BrowserActions.isClick(ShopFrontProductDetailPageObjects.increaseQtyLink);
		BrowserActions.isClick(ShopFrontProductDetailPageObjects.addToCartButton);
		BrowserActions.isSleep();

		BrowserActions.isClick(ShopFrontProductDetailPageObjects.proceedTocheckoutButton);
		final boolean objVisible = BrowserActions.isVisible(ShopFrontHomePageObjects.myAccountLink);

		if (objVisible) {
			Reports.ExtentReportLog("AddProductToCart", Status.PASS, "Product Added successfully", true);
		} else {
			Reports.ExtentReportLog("AddProductToCart", Status.FAIL, "Product Added Failed", true);
		}

	}
}
