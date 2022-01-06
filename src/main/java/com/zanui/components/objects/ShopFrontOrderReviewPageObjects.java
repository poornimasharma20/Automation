package com.zanui.components.objects;

/**
 * @author Pooja Bagga
 *
 */
public class ShopFrontOrderReviewPageObjects {

	public static String completePurchaseButton = "xpath#//div[@class='w--tab-active w-tab-pane tab-pane checkout']/div/div/div/a[@data-behat='complete-purchase']";
	public static String reviewInfoLabel = "xpath#//span[@class='checkout-step-text' and contains(text(),'Review & Place order')]";
}
