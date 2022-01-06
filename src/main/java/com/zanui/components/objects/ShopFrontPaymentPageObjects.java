package com.zanui.components.objects;

/**
 * @author Pooja Bagga
 *
 */

public class ShopFrontPaymentPageObjects {

	public static String paymentInfoLabel = "xpath#//span[@class='checkout-step-text' and contains(text(),'Payment Info')]";
	public static String creditCardRadioButton = "xpath#//input[@id='option-credit-card']";
	public static String cardholderNameTextbox = "xpath#//input[@id='Registered-cardholder']";
	public static String cardNumberTextbox = "xpath#//input[@id='Registered-cardnumber']";
	public static String cardMonthDropdown = "xpath#//select[@data-behat='card-month']";
	public static String cardYearDropdown = "xpath#//select[@data-behat='card-year']";
	public static String CVCTextbox = "xpath#//input[@id='Registered-cvc']";
	public static String billingEmailTextbox = "xpath#//input[@id='payment-email']";
	public static String newsletterCheckbox = "xpath#//input[@id='newsletter-signup']";
	public static String continuePaymentButton = "xpath#//a[@data-behat='continue-payment']";	
}
