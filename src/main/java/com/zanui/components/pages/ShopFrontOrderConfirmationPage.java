package com.zanui.components.pages;

import com.aventstack.extentreports.Status;
import com.zanui.components.objects.ShopFrontHomePageObjects;
import com.zanui.components.objects.ShopFrontOrderConfirmationPageObjects;
import com.zanui.lib.main.BrowserActions;
import com.zanui.lib.main.Multimaplibraries;
import com.zanui.lib.utils.Reports;

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

public class ShopFrontOrderConfirmationPage extends Multimaplibraries{

	public static void clickNoToFeedbackSurvey() {
			
    	BrowserActions.isSleep();
    	BrowserActions.isFrameSwitch(ShopFrontOrderConfirmationPageObjects.feedbackFrame);
    	BrowserActions.isClick(ShopFrontOrderConfirmationPageObjects.feedbackButtonNo); 	
			
		final boolean objVisible = BrowserActions.isVisible(ShopFrontHomePageObjects.myAccountLink);

		if (objVisible) {
			Reports.ExtentReportLog("Order Confirmed", Status.PASS, "Order Confirmation Successfull", true);
		} else {
			Reports.ExtentReportLog("Order Confirmed", Status.FAIL, "Order Confirmation falied", true);
		}	
		
	}
}
