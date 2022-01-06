package com.zanui.components.objects;

/**
 * @author Pooja Bagga
 *
 */

public class ShopFrontAddressPageObjects {
	public static String firstNameTextbox = "xpath#//input[@id='shipping-address-first-name']";
	public static String lastNameTextbox =	"xpath#//input[@id='shipping-address-last-name']";
	public static String address1Textbox = "xpath#//input[@id='shipping-address-address1']";
	public static String suburbTextbox = "xpath#//input[@id='shipping-address-suburb']";
	public static String postcodeTextbox = "xpath#//input[@id='shipping-address-postcode']";
	public static String phoneTextbox = "xpath#//input[@id='shipping-address-phone']";
	public static String stateDropDown = "xpath#//select[@id='shipping-address-state']";
	public static String continueButton = "xpath#//a[@data-behat='continue']";
	public static String shippingInfoLabel = "xpath#//span[@class='checkout-step-text' and contains(text(),'Shipping info')]";
}
