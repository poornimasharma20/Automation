package com.zanui.lib.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.zanui.lib.utils.Reports;

/**
 * @author Pooja Bagga
 *
 */
public class SeleniumApis {
	public static String element;
	public static WebElement webElement;
	public static List<WebElement> webElementsList;
	public static int length;

	/*************************************************************************
	 * Objective: Wrapper for the actions performed on the browser
	 * Parameters: action (String), locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void performAction(String action, String locator) {
		WebElement element = null;
		if(locator.indexOf("#//") >0 && !action.contains("assert")) {
			element = findElement(Constants.driver, locator);

		}
		switch(action) {
		case "click":
			SeleniumApis.clickWebElement(element);
			break;
		case "enter":
			SeleniumApis.enterWebElement(element);
		case "mouseHover":
			SeleniumApis.moveToElement(element);
			break;
		case "javaScriptExecutorClick":
			SeleniumApis.javascriptExecutorClick(Constants.driver, element);
			break;
		case "javaScriptExecutorMouseHover":
			SeleniumApis.javascriptExecutorMouseHover(Constants.driver, element);
			break;
		case "getWindowControl":
			SeleniumApis.getWindowControl(locator);
			break;
		case "switchFrame":
			SeleniumApis.switchFrame(locator);
			break;
		case "navigate":
			SeleniumApis.navigateTo(Constants.driver, locator);
			break;
		case "scrollToElement":
			SeleniumApis.javascriptExecutorScrollToElement(Constants.driver, element);
			break;
		case "setStepExecutionDelay":
			SeleniumApis.setStepExecutionDelay(locator);
			break;
		case "setGlobalTimeOut":
			SeleniumApis.setGlobalTimeOut(locator);
			break;
		case "clearText":
			SeleniumApis.clearText(element);
			break;
		case "assertVisible":
			SeleniumApis.assertVisible(locator);
			break;
		case "assertNotVisible":
			SeleniumApis.assertNotVisible(locator);
			break;
		default:
			break;
		}
	}

	/*************************************************************************
	 * Objective: Wrapper for the actions performed on the browser
	 * Parameters: action (String), locator (String), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void performAction(String action, String locator, String value) {
		WebElement element = findElement(Constants.driver, locator);
		switch (action) {

		case "setValue":
			SeleniumApis.sendKeys(element, value);
			break;
		case "javaScriptExecutorSendKeys":
			SeleniumApis.javascriptExecutorSendKeys(Constants.driver, element, value);
			break;
		case "SelectByValue":
			SeleniumApis.selectByValue(element, value);
			break;
		case "SelectByIndex":
			SeleniumApis.selectByIndex(element, value);
			break;
		case "SelectByVisibleText":
			SeleniumApis.selectByVisibleText(element, value);
			break;
		case "DragAndDrop":
			WebElement dragElement = findElement(Constants.driver, locator);
			SeleniumApis.dragAndDrop(element, dragElement);
			break;
		case "assertContainsText":
			SeleniumApis.assertContainsText(element, value);
			break;
		case "sendKeys":
			sendKeys(element, value);
		default:
			break;
		}
	}

	/*************************************************************************
	 * Objective: Wrapper for the get actions performed on the browser
	 * Parameters: action (String), locator (String), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/	
	public static String performGet(String action, String value, String locator) {
		WebElement element = findElement(Constants.driver, locator);
		String text = null;
		switch (action) {

		case "getAttribute":
			text = getAttribute(element, value);
			break;
		default:
			break;
		}
		return text;
	}

	/*************************************************************************
	 * Objective: Drag and drop the web element
	 * Parameters: dragElement (WebElement), dropElement (WebElement)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void dragAndDrop(WebElement dragElement, WebElement dropElement) {
		executionDelay();
		WebDriver driver = Constants.driver;
		Actions action = new Actions(driver);
		action.dragAndDrop(dragElement, dropElement).build().perform();
	}

	/*************************************************************************
	 * Objective: Halts or sleeps the execution thread by the time specified
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void executionDelay() {
		try {
			Thread.sleep(Constants.defaultGlobalTimeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*************************************************************************
	 * Objective: Assert if the element contains Text
	 * Parameters: element (WebElement), text (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void assertContainsText(WebElement element, String text) {
		try {
			String appText = "";
			if(element != null) {
				try {
					appText = element.getText();
					if(appText.length() <= 0) {
						appText = getAttribute(element, "value");
					}
				} catch (Exception e) {
					appText = getAttribute(element, "value");
				}
				if(appText.contains(text)) {
					Reports.ExtentReportLog("assert contains text", Status.PASS, text + " is present", true);
				}
				else {
					Reports.ExtentReportLog("assert contains text", Status.FAIL, text + " is not present in the element " + element.toString(), true);
				}
			} else {
				Reports.ExtentReportLog("assert contains text", Status.FAIL, "element does not exist", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Reports.ExtentReportLog("runtime error during assert contains text", Status.FAIL, e.getMessage(), true);
		}

	}
	
	/*************************************************************************
	 * Objective: Get the specified attribute from the webelement
	 * Parameters: element (WebElement), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static String getAttribute(WebElement element, String value) {
		String text = "";
		try {
			text = element.getAttribute(value);
		} catch (Exception e) {
			Constants.logger.logp(Level.WARNING, "", Constants.testCaseName, "Attribute " + value + " not found for element using default getAttribute" + element.toString());
			try {
				JavascriptExecutor js = (JavascriptExecutor) Constants.driver;
				text = (String) js.executeScript("return arguments[0].getAttribute('" + value + "');", element);
			} catch (Exception e1) {
				Constants.logger.logp(Level.WARNING, "", Constants.testCaseName, "Attribute " + value + " not found for element using default getAttribute" + element.toString());
			}
		}
		return text;

	}

	/*************************************************************************
	 * Objective: Assert if the locator is visible
	 * Parameters: OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void assertVisible(String OR) {
		try {
			if(isVisible(OR)) {
				Reports.ExtentReportLog("Assert Visible", Status.PASS, "Element is visible", true);
			} else {
				Reports.ExtentReportLog("Assert Visible", Status.FAIL, "Element " + element.toString() + " is not visible", true);
			}
		} catch(Exception e) {
			Reports.ExtentReportLog("Run time error during assert visible", Status.FAIL, e.getMessage(), true);
		}

	}

	/*************************************************************************
	 * Objective: To get the Text or the value of the locator
	 * Parameters: locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static String getValue(String locator) {
		WebElement element = findElement(Constants.driver, locator);
		try
		{
			String text = element.getText();
			return text;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/*************************************************************************
	 * Objective: To get the control of the Window specified
	 * Parameters: winTitle (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void getWindowControl(String winTitle) {
		WebDriver driver = Constants.driver;
		Set<String> allWindows = driver.getWindowHandles();
		try {
			for(String currentWindow : allWindows) {
				driver.switchTo().window(currentWindow);
				driver.manage().window().maximize();
				break;
			}
		} catch(NoSuchWindowException e) {
			System.out.println("Window with Title '" + winTitle + "' is not available");
			e.printStackTrace();
		}
	}

	/*************************************************************************
	 * Objective: To get the window handle of current window
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static String getWindowHandle() {
		WebDriver driver = Constants.driver;
		String windowHandle = driver.getWindowHandle();
		return windowHandle;
	}

	/*************************************************************************
	 * Objective: To switch the control to the specified window handle
	 * Parameters: windowHandle (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void switchWindowControl(String windowHandle) {		
		WebDriver driver = Constants.driver;
		try {
			driver.switchTo().window(windowHandle);
			driver.manage().window().maximize();
		} catch(NoSuchWindowException e) {
			System.out.println("Window with Handler '" + windowHandle + "' is not available");
			e.printStackTrace();
		}
	}


	/*************************************************************************
	 * Objective: Java script executor for click on web element
	 * Parameters: driver (WebDriver), element (WebElement)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void javascriptExecutorClick(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		} catch (ElementNotInteractableException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + element.toString() + "' is available in the DOM but not available for interaction", true);
		}

	}

	/*************************************************************************
	 * Objective: Java script executor to send keys
	 * Parameters: driver (WebDriver), element (WebElement), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void javascriptExecutorSendKeys(WebDriver driver, WebElement element, String value) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
		} catch (ElementNotInteractableException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + element.toString() + "' is available in the DOM but not available for interaction", true);
		}

	}

	/*************************************************************************
	 * Objective: Java script executor for mouse hover
	 * Parameters: driver (WebDriver), element (WebElement)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void javascriptExecutorMouseHover(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String strJavaScript = "var element = arguments[0];" + "var mouseEventObj = document.createEvent('MouseEvents');" + "mouseEventObj.initEvent('mouseOver', true, true);" + "element.dispatchEvent(mouseEventObj);";
			js.executeScript(strJavaScript, element);
		} catch(ElementNotInteractableException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + element.toString() + "is present in the DOM but not able to interact", true);
		}
	}
	
	/*************************************************************************
	 * Objective: Java script executor for scroll up
	 * Parameters: driver (WebDriver)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void javascriptExecutorScrollUp(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String strJavaScript = "scroll(0, -250);";
		js.executeScript(strJavaScript);
	}
	
	/*************************************************************************
	 * Objective: Java script executor for scroll down
	 * Parameters: driver (WebDriver)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void javascriptExecutorScrollDown(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String strJavaScript = "scroll(0, 250);";
		js.executeScript(strJavaScript);
	}
	
	/*************************************************************************
	 * Objective: Java script executor to scroll to a specified element
	 * Parameters: driver (WebDriver), element (WebElement)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void javascriptExecutorScrollToElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	/*************************************************************************
	 * Objective: Assert if locator is not visible
	 * Parameters: OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void assertNotVisible(String OR) {

		setGlobalTimeOut("3");
		try {
			if(!isVisible(OR)) {
				Reports.ExtentReportLog("assert not visible", Status.PASS, "Element " + element.toString() + "is not visible", false);
			}
			else {
				Reports.ExtentReportLog("assert not visible", Status.FAIL, "Element " + element.toString() + "is visible", true);
			}
		} catch (Exception e) {
			Reports.ExtentReportLog("Run time error during assert not visible", Status.FAIL, e.getMessage(), true);
		}
		setGlobalTimeOut("5");

	}

	/*************************************************************************
	 * Objective: To verify if the element is selected
	 * Parameters: OR (String)
	 * Author: Pooja Bagga
	 * Creation Date: 04-Apr-2020
	 * Updated by and when:
	 **************************************************************************/

	public static boolean isSelected (String OR) {
		try {
			boolean isSelected; 
			WebDriver driver = Constants.driver; 
			WebElement element = SeleniumApis.findElement(driver, OR); 
			if (element != null) {
				isSelected = element.isSelected(); } 
			else {
				isSelected = false;
				return isSelected; 
			} 
		} catch(Exception e) {
			Reports.ExtentReportLog("Run time error", Status. FAIL, e.getMessage(), true); 
		}
		return false;
	}

	/*************************************************************************
	 * Objective: To verify if the text of the locator matches with the specified value
	 * Parameters: PropertyValue (String), OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static boolean CheckPropertyText(String PropertyValue, String OR) {
		String provalue = null; 
		WebDriver driver = Constants.driver; 
		provalue = SeleniumApis.findElement(driver, OR).getText().trim(); 
		System.out.println("provalue: " + provalue); 
		if (provalue.equals(PropertyValue)) { 
			Reports.ExtentReportLog(Constants.testCaseName + "_CheckProperty", Status.PASS, " : Expected Value: " + PropertyValue + " - Actual Value: " + provalue, false); 
			return true; 
		} else { 
			Reports.ExtentReportLog(Constants.testCaseName + "_CheckProperty", Status.FAIL, " : Expected Value:" + PropertyValue + " - Actual Value: " + provalue, true); 
			return false;
		}
	}

	/*************************************************************************
	 * Objective: To verify if the value of the locator matches with the specified value for a given attribute
	 * Parameters: PropertyName (String), PropertyValue (String), OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static boolean CheckPropertyValue(String PropertyName, String PropertyValue, String OR) {
		String provalue = null; 
		WebDriver driver = Constants.driver; 
		provalue = SeleniumApis.findElement(driver, OR).getAttribute(PropertyName).trim(); 
		System.out.println("provalue: " + provalue); 
		if (provalue.equals(PropertyValue)) { 
			Reports.ExtentReportLog(Constants.testCaseName + "_CheckProperty", Status.PASS, " : Expected Value: " + PropertyValue + " - Actual Value: " + provalue, false); 
			return true; 
		} else { 
			Reports.ExtentReportLog(Constants.testCaseName + "_CheckProperty", Status.FAIL, " : Expected Value:" + PropertyValue + " - Actual Value: " + provalue, true); 
			return false;
		}
	}


	/*************************************************************************
	 * Objective: To set the execution delay for a particular step
	 * Parameters: delayInMilliSeconds (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void setStepExecutionDelay(String delayInMilliSeconds) {
		Constants.globalStepExecutionDelay = Long.parseLong(delayInMilliSeconds);

	}

	/*************************************************************************
	 * Objective: To set the global time out parameter for specified seconds
	 * Parameters: delayInSeconds (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void setGlobalTimeOut(String delayInSeconds) {
		Constants.globalTimeOut = Long.parseLong(delayInSeconds);
	}

	/*************************************************************************
	 * Objective: To get the text of specified element for all the locator types
	 * Parameters: driver (WebDriver), type (String), OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	// Get text 
	public static String getText(WebDriver driver, String type, String OR) {
		String text = "";
		String[] locatorType = OR.split("#");
		String path = locatorType[1];
		//String type = locatorType[0];
		switch (type) { 
		case "id":
			text = driver.findElement(By.id(path)).getText(); 
			break;
		case "xpath":
			text = driver.findElement(By.xpath(path)).getText(); 
			break;
		case "className":
			text = driver.findElement(By.className(path)).getText(); 
			break;
		case "tagName":
			text = driver.findElement(By.tagName(path)).getText(); 
			break;
		case "linktext":
			text = driver.findElement(By.linkText(path)).getText(); 
			break;
		case "name" :
			text = driver. findElement(By.name(path)).getText(); 
			break;
		}
		return text;
	}

	
	
	/*************************************************************************
	 * Objective: To get the control of the current window
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void getCurrentWindowControl() {

		WebDriver driver = Constants.driver;
		Set<String> allWindows = driver.getWindowHandles();
		try {
			System.out.println("All Window handles : " + allWindows);

			for(String currentWindowHandle : allWindows) {
				if(!currentWindowHandle.equals(Constants.CurrentWindow)) {
					System.out.println("Current Window : " + currentWindowHandle);
					driver.switchTo().window(currentWindowHandle);
					driver.manage().window().maximize();
					break;
				}
			}
		}
		catch(NoSuchWindowException e) {
			System.out.println("Window is not available");
			e.printStackTrace();
		}
	}

	/*************************************************************************
	 * Objective: To verify if the specified window exists
	 * Parameters: winTitle (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static boolean checkWindowExists(String winTitle) {
		boolean winExists = false;
		try {
			getCurrentWindowControl();
			if(Constants.driver.getTitle().contains(winTitle)) {
				winExists = true;
			} else {
				winExists = false;
			}
			return winExists;
		} catch (NoSuchWindowException e) {
			e.printStackTrace();
			return winExists;
		}

	}

	/*************************************************************************
	 * Objective: Get the title of web page
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static String getTitleOfPage()
	{
		return Constants.driver.getTitle();
	}
	
	
	/*************************************************************************
	 * Objective: To switch to the specified frame
	 * Parameters: frameTitle (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void switchFrame(String frameTitle) {
		WebDriver driver = Constants.driver;
		try {
			driver.switchTo().frame(frameTitle);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
		}
	}	


	/*************************************************************************
	 * Objective: To switch to the default frame
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void switchToDefaultFrame() {
		WebDriver driver = Constants.driver;
		try {
			driver.switchTo().defaultContent();
		} catch (NoSuchFrameException e) {
			e.printStackTrace();;
		}
	}

	/*************************************************************************
	 * Objective: To set a web element with specified value for a specified attribute
	 * Parameters: element (WebElement), attributeName (String), attributeValue (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void setAttribute(WebElement element, String attributeName, String attributeValue) {
		JavascriptExecutor js = (JavascriptExecutor) Constants.driver;
		js.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue + "');", element);

	}

	/*************************************************************************
	 * Objective: To clear the text from a web element
	 * Parameters: element (WebElement)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void clearText(WebElement element) {
		element.sendKeys(Keys.CONTROL + "A");
		element.sendKeys(Keys.DELETE);
	}

	
	
	/*************************************************************************
	 * Objective: To navigate to the specified URL
	 * Parameters: driver (WebDriver), URL (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void navigateTo(WebDriver driver, String URL) {
		driver.navigate().to(URL);		
	}

	/*************************************************************************
	 * Objective: To wait until the element is visible
	 * Parameters: driver (WebDriver), xpath (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void waitUntilVisibilityOfElements(WebDriver driver, String xpath) {	
		WebDriverWait wait = new WebDriverWait(driver, Constants.globalTimeOut);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
	}

	/*************************************************************************
	 * Objective: To click the specified web element
	 * Parameters: element (WebElement)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void clickWebElement(WebElement element) {
		try {
			element.click();
		} catch (ElementNotInteractableException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + element.toString() + "' is present in the DOM, but not able to interact ", true);
		}
	}

	public static void enterWebElement(WebElement element) {
		try {
			element.sendKeys(Keys.ENTER);
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + element.toString() + "' is present in the DOM, but not able to interact ", true);
		}
	}
	
	/*************************************************************************
	 * Objective: Action performed to move to an element or mouse hover
	 * Parameters: element (WebElement)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void moveToElement(WebElement element) {
		try {
			WebDriver driver = Constants.driver;
			Actions act = new Actions(driver);
			Action mouseHover = act.moveToElement(element).build();
			mouseHover.perform();
		} catch (ElementNotInteractableException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + element.toString() + "' is present in the DOM, but not able to interact ", true);
		}
	}

	/*************************************************************************
	 * Objective: Wrapper for different checks performed on a locator
	 * Parameters: action (String), locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/	

	public static Boolean PerformCheck(String action, String locator) {
		Boolean value = null;
		switch(action) {
		case "isVisible":
			value = isVisible(locator);
			break;
		case "isSelected":
			value = isSelected(locator);
			break;
		case "checkWindowExists":
			value = checkWindowExists(locator);
			break;
		case "waitTillInvisibilityOfElement":
			value = waittillInvisibilityofElement(locator);
			break;
		case "isExists":
			value = isExists(locator);
			break;
		default:
			break;
		}
		return value;

	}

	/*************************************************************************
	 * Objective: To verify if an element exists
	 * Parameters: OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static boolean isExists(String OR) {
		WebDriver driver = Constants.driver;
		try {
			boolean isExists;
			executionDelay();
			String[] locatorType = OR.split("#");
			String xpath = locatorType[1];
			if(driver.findElements(By.xpath(xpath)).size() > 0) {
				isExists = true;
			} else {
				isExists = false;
			}
			return isExists;
		} catch (Exception e) {
			return false;
		}

	}
	
	/*************************************************************************
	 * Objective: To verify if an element is visible
	 * Parameters: OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static boolean isVisible(String OR) {
		try {
			boolean isVisible;
			Thread.sleep(Constants.globalStepExecutionDelay);
			WebDriver driver = Constants.driver;
			String[] locatorType = OR.split("#");
			String xpath = locatorType[1];
			WebElement element = driver.findElement(By.xpath(xpath));
			if(element != null) {
				if(element.isDisplayed()) {
					isVisible = true;
				} else {
					isVisible = false;
				}
			} else {
				isVisible = false;
			}
			return isVisible;
		} catch (Exception e) {
			return false;

		}

	}

	/*************************************************************************
	 * Objective: To find the element for a specified locator
	 * Parameters: driver (WebDriver), locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static WebElement findElement(WebDriver driver, String locator) {

		try {
			try {
				Thread.sleep(Constants.globalStepExecutionDelay);
			}
			catch (InterruptedException e) {
				e.printStackTrace();

			}
			String[] locatorType = locator.split("#");
			String type = locatorType[0];
			String OR = locatorType[1];
			WebDriverWait wait = new WebDriverWait(driver, Constants.globalTimeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR)));
			switch(type) {
			case "id" :
				webElement = driver.findElement(By.id(OR));
				break;
			case "xpath" :
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].removeAttribute('disabled','disabled')", driver.findElement(By.xpath(OR)));
				js.executeScript("arguments[0].setAttribute('style','border: 2px solid red;');", driver.findElement(By.xpath(OR)));
				webElement = driver.findElement(By.xpath(OR));
				break;
			case "className" :
				webElement = driver.findElement(By.className(OR));
				break;
			case "tagName" :
				webElement = driver.findElement(By.tagName(OR));
				break;
			case "linktext" :
				webElement = driver.findElement(By.linkText(OR));
				break;
			case "name" :
				webElement = driver.findElement(By.name(OR));
				break;
			}
			return webElement;
		} catch (NoSuchElementException e) {
			e.printStackTrace();

			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + locator + "' is not found in the application", true);
			try {
				BaseTest.afterTest();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		} catch (ElementNotVisibleException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + locator + "' is not visible in the application", true);
			try {
				BaseTest.afterTest();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		} catch (TimeoutException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + locator + "' is not found in the specified time", true);
			try {
				BaseTest.afterTest();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}

	/*************************************************************************
	 * Objective: To find the list of elements for specified locator 
	 * Parameters: driver (WebDriver), locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/


	public static List<WebElement> findElements(WebDriver driver, String locator) {

		try {
			try {
				Thread.sleep(Constants.globalStepExecutionDelay);
			}
			catch (InterruptedException e) {
				e.printStackTrace();

			}
			String[] locatorType = locator.split("#");
			String type = locatorType[0];
			String OR = locatorType[1];
			WebDriverWait wait = new WebDriverWait(driver, Constants.globalTimeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR)));
			switch(type) {
			case "id" :
				webElementsList = driver.findElements(By.id(OR));
				break;
			case "xpath" :
				//JavascriptExecutor js = (JavascriptExecutor) driver;
				//js.executeScript("arguments[0].removeAttribute('disabled','disabled')", driver.findElements(By.xpath(OR)));
				//js.executeScript("arguments[0].setAttribute('style','border: 2px solid red;');", driver.findElements(By.xpath(OR)));
				webElementsList = driver.findElements(By.xpath(OR));
				break;
			case "className" :
				webElementsList = driver.findElements(By.className(OR));
				break;
			case "tagName" :
				webElementsList = driver.findElements(By.tagName(OR));
				break;
			case "linktext" :
				webElementsList = driver.findElements(By.linkText(OR));
				break;
			case "name" :
				webElementsList = driver.findElements(By.name(OR));
				break;
			}
			return webElementsList;
		} catch (NoSuchElementException e) {
			e.printStackTrace();

			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + locator + "' is not found in the application", true);
			try {
				BaseTest.afterTest();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		} catch (ElementNotVisibleException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + locator + "' is not visible in the application", true);
			try {
				BaseTest.afterTest();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		} catch (TimeoutException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + locator + "' is not found in the specified time", true);
			try {
				BaseTest.afterTest();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}

	/*************************************************************************
	 * Objective: To get the size of the list of web elements
	 * Parameters: webElementsList (List<WebElement>)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/


	public static int webElementsListSize(List<WebElement> webElementsList) {
		return webElementsList.size();

	}

	/*************************************************************************
	 * Objective: To pass the value to a specified Web Element
	 * Parameters: element (WebElement), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/


	public static void sendKeys(WebElement element, String value) {
		try {
			element.clear();
			element.sendKeys(value);
		} catch (ElementNotInteractableException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element '" + element.toString() + "' is present in the DOM, but not able to interact ", true);

		}		
	}

	/*************************************************************************
	 * Objective: To select an item from drop down based on the item text
	 * Parameters: element (WebElement), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/


	public static void selectByName(WebElement element, String value) {
		try {
			Select sel = new Select(element);
			sel.selectByVisibleText(value);			
		} catch (ElementNotSelectableException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element is disabled. Hence, Not able to select", true);
		}

	}

	/*************************************************************************
	 * Objective: To select an item from drop down based on the item index
	 * Parameters: element (WebElement), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/


	public static void selectByIndex(WebElement element, String value) {
		try {
			Select sel = new Select(element);
			int index = Integer.parseInt(value);
			sel.selectByIndex(index);			
		} catch (ElementNotSelectableException e) {
			e.printStackTrace();
			Reports.ExtentReportLog(Constants.testCaseName, Status.FAIL, "Element is disabled. Hence, Not able to select", true);
		}

	}

	/*************************************************************************
	 * Objective: To select an item from drop down based on the item value 
	 * Parameters: element (WebElement), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/


	public static void selectByValue(WebElement element, String value) {
		Select sel = new Select(element);
		sel.selectByValue(value);
	}

	/*************************************************************************
	 * Objective: To capture the screenshot and place it in specified location appending timestamp to it
	 * Parameters: driver (WebDriver), Node (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static String CaptureScreenShot(WebDriver driver, String Node) {
		String newPath = null;
		try {
			String location = Node;
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = Constants.ResultPath;
			newPath = path + File.separator + location + "_" + timeStamp() + ".png";
			FileUtils.copyFile(src, new File(newPath));			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return newPath;

	}

	/*************************************************************************
	 * Objective: To get the current timestamp
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static String timeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());

	}

	/*************************************************************************
	 * Objective: To wait until the element gets invisible
	 * Parameters: locator (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static boolean waittillInvisibilityofElement(String Locator) {
		executionDelay(); 
		WebDriver driver = Constants.driver; 
		try {
			String[] locatorType = Locator.split("#"); 
			String OR = locatorType[1]; 
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, Constants.globalTimeOut);
			boolean present = wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(OR))); 
			return present; 
		} catch (Exception e) {
			return false; 
		} finally {
			driver.manage(). timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}

	
	/*************************************************************************
	 * Objective: To find element not waiting till the visibility of the element
	 * Parameters: driver (WebDriver), type (String), OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static WebElement findElementDevoidVisibilityWait(WebDriver driver, String type, String OR) {
		switch (type) { 
		case "id":
			webElement = driver.findElement(By.id(OR));
			break; 
		case "xpath":
			webElement = driver.findElement(By.xpath(OR));
			break; 
		case "className" :
			webElement = driver.findElement(By.className(OR));
			break; 
		case "tagName":
			webElement = driver.findElement(By.tagName(OR));
			break; 
		case "linktext":
			webElement = driver.findElement(By.linkText(OR));
			break; 
		case "name":
			webElement = driver.findElement(By.name(OR)); 
			break;
		}
		return webElement;
	}

	/*************************************************************************
	 * Objective: To take the screenshot and place it in specified location appending timestamp to it
	 * Parameters: driver (WebDriver), location (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void TakingScreenShots(WebDriver driver, String location) {
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String path = Constants.ResultPath + File.separator + Constants.TCID + "-Screenshots";
			FileUtils.copyFile(src, new File(path + File.separator + location + File.separator + timeStamp() + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*	public static void UploadDocuments(String vbBatchFilePath, String DocumentPath) {
		try {
			Process p = Runtime.getRuntime().exec(vbBatchFilePath + " " + DocumentPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	} */

	/*************************************************************************
	 * Objective: To upload a document from specified path
	 * Parameters: DocumentPath (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void UploadDocuments(String DocumentPath) throws InterruptedException {
		StringSelection stringSelection = new StringSelection(DocumentPath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		Robot robot = null;
		Robot robot1 = null;
		Robot robot2 = null;
		try {
			robot = new Robot();
			robot1 = new Robot();
			robot2 = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Thread.sleep(3000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot1.keyPress(KeyEvent.VK_V);
		robot1.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(3000);
		robot2.keyPress(KeyEvent.VK_ENTER);
		robot2.keyRelease(KeyEvent.VK_ENTER);
	}

	/*************************************************************************
	 * Objective: To find the length of specified web element
	 * Parameters: driver (WebDriver), type (String), OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static int findElementsLength(String type, String OR) {
		WebDriver driver = Constants.driver;
		WebDriverWait wait = new WebDriverWait(driver, Constants.globalTimeOut); 
		int size = 0; 
		switch (type) { 
		case "id":
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(OR)))); 
			size = driver.findElements(By.id(OR)).size(); 
			break;
		case "xpath":
			//wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(OR)))); 
			size = SeleniumApis.findElements(driver, OR).size();
			break;
		case "className" :
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.className(OR)))); 
			size = driver.findElements(By.className(OR)).size(); 
			break;
		case "tagName":
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.tagName(OR)))); 
			size = driver.findElements(By.tagName(OR)).size(); 
			break;
		case "linktext":
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText(OR)))); 
			size = driver.findElements(By.linkText(OR)).size(); 
			break;
		case "name":
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.name(OR)))); 
			size = driver.findElements(By.name(OR)).size(); 
			break;
		}
		return size;
	}
	
	/*************************************************************************
	 * Objective: To select a value from the drop down
	 * Parameters: element (WebElement), value (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void selectByVisibleText(WebElement element, String value) {
		Select sel = new Select(element); 
		sel.selectByVisibleText(value); 
	}

	/*************************************************************************
	 * Objective: To click the specified radio button
	 * Parameters: driver (WebDriver), type (String), OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	// click on radio button 
	public static String radioButton(WebDriver driver, String type, String OR) {
		element = isElementPresent(driver, type, OR); 
		if (element.equals(Constants.KEYWORD_OBJECTPRESENT)) {
			WebDriverWait wait = new WebDriverWait(driver, Constants.globalTimeOut); 
			switch (type) { 
			case "id":
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(OR)))); 
				driver.findElement(By.id(OR)).click(); 
				break;
			case "xpath":
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(OR)))); 
				driver.findElement(By.xpath(OR)).click(); 
				break;
			case "className":
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.className(OR)))); 
				driver.findElement(By.className(OR)).click(); 
				break;
			case "tagName":
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.tagName(OR)))); 
				driver.findElement(By. tagName(OR)).click(); 
				break;
			case "linktext":
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText(OR)))); 
				driver.findElement(By.linkText(OR)).click(); 
				break;
			case "name":
				wait.until(ExpectedConditions.visibilityOfElementLocated((By.name(OR)))); 
				driver.findElement(By.name(OR)).click(); 
				break;
			}
		} else {
			return Constants.KEYWORD_OBJECTABSENT;
		}
		return Constants.KEYWORD_ACTIONPERFORMED;
	}

	/*************************************************************************
	 * Objective: To verify if the specified element is present 
	 * Parameters: driver (WebDriver), type (String), OR (String)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static String isElementPresent(WebDriver driver, String type, String OR) {
		switch(type) {
		case "id":
			if(driver.findElements(By.id(OR)).size()!= 0) {
				return Constants.KEYWORD_OBJECTPRESENT;
			}
			break;
		case "name":
			if(driver.findElements(By.name(OR)).size()!= 0) {
				return Constants.KEYWORD_OBJECTPRESENT;
			}
			break;
		case "className":
			if(driver.findElements(By.className(OR)).size()!= 0) {
				return Constants.KEYWORD_OBJECTPRESENT;
			}
			break;
		case "tagName":
			if(driver.findElements(By.tagName(OR)).size()!= 0) {
				return Constants.KEYWORD_OBJECTPRESENT;
			}
			break;
		case "linkText":
			if(driver.findElements(By.linkText(OR)).size()!= 0) {
				return Constants.KEYWORD_OBJECTPRESENT;
			}
			break;
		case "xpath":
			if(driver.findElements(By.xpath(OR)).size()!= 0) {
				return Constants.KEYWORD_OBJECTPRESENT;
			}
			break;	
		}
		return Constants.KEYWORD_OBJECTABSENT;
	}

	/*************************************************************************
	 * Objective: To close the current window
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void closeCurrentWindow() {
		Constants.driver.close();
	}

}
