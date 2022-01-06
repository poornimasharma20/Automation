package com.zanui.testScripts;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.zanui.components.pages.ShopFrontCartPage;
import com.zanui.components.pages.ShopFrontHomePage;
import com.zanui.components.pages.ShopFrontOrderReviewPage;
import com.zanui.components.pages.ShopFrontProductDetailPage;
import com.zanui.lib.main.BaseTest;


import lombok.NoArgsConstructor;

/*************************************************************************
 * Objective: Create a test case to validate the checkout flow for Logged In user
 * Parameters:
 * Author: Pooja bagga
 * Updated by and when:
 * Checkout flow comment updated
 **************************************************************************/

/**
 * 
 * @author Pooja Bagga
 *
 */
@NoArgsConstructor
public class ShopFrontLoggedInCheckoutTest extends BaseTest {

	ShopFrontLogg edInCheckoutTest loggedInCheckout;

	@BeforeClass
	public void setUp() {
		loggedInCheckout = new ShopFrontLoggedInCheckoutTest();
	}

	@Test(testName = "TC001 - HomePageLaunched", description = "Home Page is opened", priority = 0)
	public void LaunchHomePage() {
		ShopFrontHomePage.navigateToHomePage("StagingStorefront");
	}

	@Test(testName = "TC002 - Login SF", description = "Login the Store Front", priority = 1)
	public void LoginStoreFront() {
		ShopFrontHomePage.LoginToStoreFront("StagingStorefront");
	}
	
	@Test(testName = "TC003 - Search Product", description = "Search product on Store Front", priority = 2)
	public void SearchProduct() {
		ShopFrontHomePage.SerachProduct("StagingStorefront");
	}
	
	@Test(testName = "TC004 - Add Product", description = "Add product on Store Front", priority = 3)
	public void AddProductToCart() {
		ShopFrontProductDetailPage.AddToCart();
	}
	
	@Test(testName="TC005 - Checkout", description="Checkout to Address Page", priority = 4 )
	public void Checkout()
	{
		ShopFrontCartPage.ProceedToCheckout();
	}
	
	@Test(testName="TC006 - Place Order", description="Place an order", priority = 5 )
	public void PlaceOrder()
	{
		ShopFrontOrderReviewPage.PlaceOrder();
	}
		 
	@Test(testName = "TC007 - Logout SF", description = "Logout the Store Front", priority = 6)
	public void LogoutToStoreFront() {
		ShopFrontHomePage.LogoutFromStoreFront();
	}
 
	
}
