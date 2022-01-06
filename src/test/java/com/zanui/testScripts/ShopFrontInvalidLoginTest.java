package com.zanui.testScripts;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.Test;
	import com.zanui.components.pages.ShopFrontHomePage;
	
	import com.zanui.lib.main.BaseTest;


	import lombok.NoArgsConstructor;

	/*************************************************************************
	 * Objective: Create a test case to validate the checkout flow for Logged In user
	 * Parameters:
	 * Author: Pooja bagga
	 * Updated by and when:
	 **************************************************************************/

	/**
	 * 
	 * @author Pooja Bagga
	 *
	 */
	@NoArgsConstructor
	public class ShopFrontInvalidLoginTest extends BaseTest {

		ShopFrontInvalidLoginTest invalidLogin;

		@BeforeClass
		public void setUp() {
			invalidLogin = new ShopFrontInvalidLoginTest();
		}

		@Test(testName = "TC001 - HomePageLaunched", description = "Home Page is opened", priority = 0)
		public void LaunchHomePage() {
			ShopFrontHomePage.navigateToHomePage("StagingStorefrontInvalidLogin");
		}

		@Test(testName = "TC002 - Invalid Login SF", description = "Try Invalid Login into the Store Front", priority = 1)
		public void LoginStoreFront() {
			ShopFrontHomePage.InvalidLogin("StagingStorefrontInvalidLogin");
		}
}
