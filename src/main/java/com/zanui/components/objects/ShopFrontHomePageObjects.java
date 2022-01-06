package com.zanui.components.objects;

/**
 * @author Pooja Bagga
 *
 */
public class ShopFrontHomePageObjects {
	
	public static String myAccountLink = "xpath#//div[@class='w-dropdown-toggle dropdown-toggle']/div[text()='my account']";
	public static String emailAddressTextbox="xpath#//input[@id='account-login-flyout-email']";
	public static String passwordTextBox="xpath#//input[@id='account-login-flyout-password']";
	public static String signInButton = "xpath#//a[@class='button ga-track-link-click']";
	public static String signoutButton="xapth#//a[@class='btn-sign-out w-dropdown-link dropdown-link ga-track-link-click']";
	public static String searchTextBox="xpath#//input[@id='header-search']";
	public static String searchSuggestion="xpath#//span[@class='search-highlight']";
	public static String invalidLoginLabel = "xpath#//div[@class='show error-message-container']/div/div";
}
