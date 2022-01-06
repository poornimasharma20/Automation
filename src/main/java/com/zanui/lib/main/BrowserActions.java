package com.zanui.lib.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Pooja Bagga
 *
 */
public class BrowserActions extends SeleniumApis{

	public static WebDriver driver = Constants.driver;

	/*************************************************************************
	 * Objective: To navigate to specified URL
	 * Parameters: URL (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void isNavigate(String URL) {
		System.out.print(URL); 
		performAction("navigate", URL);
	}

	/*************************************************************************
	 * Objective: To Click on the specified Object
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void isClick(String locator) {
		performAction("click", locator);
	}

	
	/*************************************************************************
	 * Objective: To enter on specified Object
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void pressEnter(String locator)
	{
		performAction("enter",locator);
	}
	
	
	/*************************************************************************
	 * Objective: Clears the input text from the object
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void isClearText(String locator) {
		performAction("clearText", locator);
	}

	/*************************************************************************
	 * Objective: Sets the object with specified value
	 * Parameters: Locator (String), Value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void isSetValue(String locator, String value) { 
		if (value.length() > 0) {
			performAction("setValue", locator, value);
		}
	}
	
	/*************************************************************************
	 * Objective: Sets the drop down object with specified value
	 * Parameters: Locator (String), Value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void isSetSelectByValue(String locator, String value) { 
		if (value.length() > 0) {
			performAction("SelectByValue", locator, value);
		}
	}
	
	/*************************************************************************
	 * Objective: Mouse Hovers on specified object
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void isMouseHover(String locator) { 
			performAction("mouseHover", locator);
	}

	public static void isFrameSwitch(String locator)
	{
		performAction("switchFrame",locator);
	}
	/*************************************************************************
	 * Objective: Enter the keys or the String value into the object
	 * Parameters: Locator (String), Value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void isSendKeyStroke(String locator, String value) {
		performAction("sendKeys", locator, value);

	}

	/*************************************************************************
	 * Objective: Assert the visibility of the object
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void isAssertVisible(String locator) {
		performAction("assertvisible", locator);
	}

	/*************************************************************************
	 * Objective: Assert if the object exists
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void isAssertExists(String locator) {
		performAction("assertExists", locator);
	}

	/*************************************************************************
	 * Objective: Assert if the object doesn't exist
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void isAssertNotExists(String locator) {
		performAction("assertNotExists", locator);
	}

	/*************************************************************************
	 * Objective: Assert if object contains specified text 
	 * Parameters: Locator (String), Text (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void isAssertContainsText(String locator, String text) {
		performAction("assertContainsText", locator, text);
	}
	
	/*************************************************************************
	 * Objective: To return the text present in the object
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	
	public static String isGetText(String locator) {
		return getText(driver, "xpath", locator);
		
	}
	
	/*************************************************************************
	 * Objective: Get the title of web page
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static String isGetTitle()
	{
		return getTitleOfPage();
		
	}
	
	
	/*************************************************************************
	 * Objective: Delay the execution of the thread by predefined time
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	
	public static void isSleep() {
		executionDelay();
		
	}
	
	/*************************************************************************
	 * Objective: Sets the attribute of the element with the specified value
	 * Parameters: element (WebElement), attributeName (String), attributeValue (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void isSetAttribute(WebElement element, String attributeName, String attributeValue) {
		if (attributeValue != "" || attributeValue != null) {
			setAttribute(element, attributeName, attributeValue);
		}
	}
	
	/*************************************************************************
	 * Objective: Returns the required attribute name of the specified locator
	 * Parameters: attributeName (String), Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/	
	public static String isGetAttribute(String attributeName, String locator) {
		return performGet("getAttribute", attributeName, locator);
		
	}
	
	/*************************************************************************
	 * Objective: Returns the value present in the locator
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/	
	public static String isGetValue(String locator) {
		return SeleniumApis.getValue(locator);
		
	}
	
	/*************************************************************************
	 * Objective: Verify if the object is visible
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static boolean isVisible(String locator) {
		return PerformCheck("isVisible", locator);
	}
	
	/*************************************************************************
	 * Objective: Verify if the object exists
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static boolean isExists(String locator) {
		return PerformCheck("isExists", locator);
	}
	
	/*************************************************************************
	 * Objective: Get the current window control
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void getCurrentWindowControl() {
		SeleniumApis.getCurrentWindowControl();
	}
	
	/*************************************************************************
	 * Objective: Close the current window
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void closeWindow() {
		SeleniumApis.closeCurrentWindow();
	}
	
	/*************************************************************************
	 * Objective: Get the window handle of the current window
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	
	public static String getWindowhandle() {
		return SeleniumApis.getWindowHandle();
	}
	
	/*************************************************************************
	 * Objective: Switch to the specified window handle
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	
	public static void switchWindowControl(String windowHandle) {
		SeleniumApis.switchWindowControl(windowHandle);
	}
	
	/*************************************************************************
	 * Objective: Returns the size of the element
	 * Parameters: Locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	
	public static int getElementsLength(String locator) { 
		return SeleniumApis.findElementsLength("xpath", locator);
		
	}

}
