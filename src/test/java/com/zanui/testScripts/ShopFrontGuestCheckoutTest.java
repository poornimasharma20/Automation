package com.zanui.testScripts;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.zanui.components.pages.ShopFrontHomePage;
import com.zanui.components.pages.ShopFrontProductDetailPage;
import com.zanui.components.pages.ShopFrontSelectCustomerType;
import com.zanui.components.pages.ShopFrontOrderReviewPage;
import com.zanui.components.pages.ShopFrontPaymentPage;
import com.zanui.components.pages.ShopFrontAddressPage;
import com.zanui.components.pages.ShopFrontCartPage;
import com.zanui.lib.main.BaseTest;

public class ShopFrontGuestCheckoutTest extends BaseTest {

	ShopFrontGuestCheckoutTest guestCheckout;

	@BeforeClass
	public void setUp() {
		guestCheckout = new ShopFrontGuestCheckoutTest();
	}

	@Test(testName = "TC001 - HomePageLaunched", description = "Home Page is opened", priority = 0)
	public void LaunchHomePage() {
		ShopFrontHomePage.navigateToHomePage("StagingStorefront");
	}
	
	@Test(testName = "TC002 - Search Product", description = "Search product on Store Front", priority = 1)
	public void SearchProduct() {
		ShopFrontHomePage.SerachProduct("StagingStorefront");
	}
	
	@Test(testName = "TC003 - Add Product", description = "Add product on Store Front", priority = 2)
	public void AddProductToCart() {
		ShopFrontProductDetailPage.AddToCart();
	}
	
	@Test(testName="TC004 - Checkout", description="Checkout to Address Page", priority = 3)
	public void Checkout()
	{
		ShopFrontCartPage.ProceedToCheckout();
	}
	
	@Test(testName="TC005 - Checkout as guest", description="Select checkout as guest", priority = 4)
	public void SelectCustomerType()
	{
		ShopFrontSelectCustomerType.checkoutAsGuest();
	}
	
	@Test(testName="TC006 - Shiiping Address", description="Fill the shipping address", priority = 5 )
	public void FillAddress()
	{
		ShopFrontAddressPage.FillAddressData("StagingStorefront");
	}
	
	@Test(testName="TC007 - Payment", description="Fill card details and do payment", priority = 6 )
	public void FillCardDetails()
	{
		ShopFrontPaymentPage.EnterCreditCardDetails("StagingStorefront");
	}
		 
	@Test(testName = "TC008 - Place Order", description = "Confirm and place order", priority = 7)
	public void PlaceOrder() {
		ShopFrontOrderReviewPage.PlaceOrder();
	}
	
} 
