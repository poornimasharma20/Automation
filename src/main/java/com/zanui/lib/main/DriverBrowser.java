package com.zanui.lib.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;

import io.appium.java_client.android.AndroidDriver;

/*************************************************************************
 * Objective: Launch the specified browser
 * Parameters: browser (String), launchType (String)
 * Author: Pooja Bagga
 * Updated by and when:
 **************************************************************************/
public class DriverBrowser {

	public static WebDriver driver;

	public static WebDriver launchBrowser(String browser, String launchType) throws Exception {
		String downloadFilepath = Constants.testDataOutputPath; 
		String currWindw = null;  
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>(); 
		switch (browser.toLowerCase()) {
		
		case "firefox": 
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "zanui" + File.separator + "config" + File.separator + "resources" + File.separator + "geckodriver.exe");
			//String mimeTypes = "application/zip, application/octet-stream, image/jpeg, application/vnd.ms-outlook, text/html,application/pdf";
			FirefoxProfile profile = new FirefoxProfile(); 
			profile.setPreference("browser.download, folderList", 2); 
			profile.setPreference("browser.download.manager.showwhenStarting", false); 
			profile.setPreference("browser. download.dir", downloadFilepath); 
			profile.setPreference("browser.helperApps.neverAsk.openFile",
					"application/zip, application/octet-stream, image/jpeg, application/vnd.ms-outlook, text/html,application/pdf"); 
			profile.setPreference ("browser.helperApps. neverAsk.saveToDisk",
					"application/zip, application/octet-stream, image/jpeg, application/vnd.ms-outlook, text/html,application/pdf"); 
			profile.setPreference ("browser.helperApps.alwaysAsk.force", false); profile.setPreference("browser.download.manager.alertOnEXEOpen", false); profile.setPreference ("browser.download.manager.focusWhenStarting", false); profile.setPreference("browser.download.manager.useWindow", false); profile.setPreference("browser.download.manager.showAlertOnComplete", false); profile.setPreference ("browser.download.manager.closeWhenDone", false);
			FirefoxOptions ffoptions = new FirefoxOptions(); 
			ffoptions.setProfile (profile);
			if (launchType.equalsIgnoreCase ("Headless")) {
				ffoptions.addArguments ("-headless"); 
				driver = new FirefoxDriver (ffoptions);
			} else if (launchType.equalsIgnoreCase("Remote")) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setBrowserName("firefox");
				driver = new RemoteWebDriver(new URL("http://bme1-sgrid-1.intershop.com.au:4444/wd/hub"), capabilities);
			}
			
			else {
				driver = new FirefoxDriver(ffoptions);
			}
			currWindw = driver.getWindowHandle(); 
			Constants.CurrentWindow = currWindw; 
			Constants.driver = driver; 
			driver.manage().window().maximize(); 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
			driver.manage().deleteAllCookies(); 
			setSession(driver); 
			break;

		case "chrome":
			ChromeOptions choptions = new ChromeOptions();
			DesiredCapabilities cap = new DesiredCapabilities();
			System.out.println(System.getProperty("user.dir"));
			choptions = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "zanui" + File.separator + "config" + File.separator + "resources" + File.separator + "chromedriver.exe");
			chromePrefs = new HashMap<String, Object>(); 
			chromePrefs.put("profile.default_content_settings.popups", 0); 
			chromePrefs.put("download. default directory", downloadFilepath); 
			chromePrefs.put("credentials enable service", false); 
			chromePrefs.put("profile.password manager enabled", false); 
			chromePrefs.put("safebrowsing.enabled", true); 
			choptions.setExperimentalOption("prefs", chromePrefs);
			choptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			choptions.setExperimentalOption("useAutomationExtension", false); 
			// choptions.addArguments ("--disable-infobars"); 
			choptions.addArguments("--test-type"); 
			choptions.addArguments("--disable-gpu"); 
			choptions.addArguments("--no-first-run");
			choptions.addArguments("--no-default-browser-check"); 
			choptions.addArguments("--ignore-certificate-errors"); 
			cap = DesiredCapabilities.chrome();
			if (launchType.equalsIgnoreCase("Headless")) {
				choptions.addArguments("--headless"); 
				choptions.addArguments("window-size=1920, 1080"); 
				cap.setCapability(ChromeOptions.CAPABILITY, choptions); 
				driver = new ChromeDriver(choptions);
			} else if (launchType.equalsIgnoreCase("Remote")) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setBrowserName("chrome");
				driver = new RemoteWebDriver(new URL("http://bme1-sgrid-1.intershop.com.au:4444/wd/hub"), capabilities);
			}
			
			else if (launchType.equalsIgnoreCase("Mobile")) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
		        capabilities.setCapability("platformName", "Android");
		        capabilities.setCapability("platformVersion", "7.0");
		        capabilities.setCapability("deviceName", "ASUS_Z017DA");
		        capabilities.setCapability("uiautomator2ServerInstallTimeout", 50000);
		        capabilities.setCapability("browserName", "Chrome");	        
		        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		        break;
			}
			
			else {
				cap.setCapability(ChromeOptions.CAPABILITY, choptions);
				driver = new ChromeDriver(choptions);
			}
			currWindw = driver.getWindowHandle(); 
			Constants.CurrentWindow = currWindw; 
			driver.manage().window().maximize(); 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
			driver.manage().deleteAllCookies(); 
			setSession(driver); 
			break;

		case "ie":
			// DesiredCapabilities cap = new DesiredCapabilities(); 
			// cap.setCapability (InternetExplorerDriver.IE_ENSURE CLEAN_SESSION, true); 
			// closeAllBrowserSession(); 
			System.out.println(System.getProperty ("user.dir")); 
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "zanui" + File.separator + "config" + File.separator + "resources" + File.separator + "IEDriverServer.exe");
			try {
				if (launchType.equalsIgnoreCase ("Headless")) {
					// InternetExplorerOptions options = new InternetExplorerOptions(); 
					// options.addArguments("-headless""); 
					//options.setHeadless(true); 
					// driver = new InternetExplorerDriver (options);
				} else {
					driver = new InternetExplorerDriver(); 
					//driver = new htmlun();
				}
			} catch (Exception e) {
				while (driver == null) {
					System.out.println("Outer driver Exception :" + driver);

					try {
						driver = new InternetExplorerDriver();
					} catch (Exception inn) {
						// inn.printStackTrace(); 
						System.out.println("Inner driver exception ");
					}
					if (driver == null) {
						// closeAllBrowserSession();
					}
				}
			}
			System.out.println("driver exception " + driver); 
			try {
				driver.manage().window().maximize(); 
			} catch (WebDriverException e) { 
				try {
					driver = new InternetExplorerDriver(); 
				} catch (Exception el) {
					// e.printStackTrace(); 
					while (driver == null) {
						System.out.println("Outer driver Exception " + driver); 
						try {
							driver = new InternetExplorerDriver(); 
						} catch (Exception inn) {
							// inn.printStackTrace(); 
							System.out.println("Inner driver exception ");
						}
						if (driver == null) {
							// closeAllBrowserSession();
						}
					}
				}
			}
			currWindw = driver.getWindowHandle(); 
			Constants.CurrentWindow = currWindw; 
			driver.manage().window().maximize(); 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			break;
		default: 
			System.out.println(System.getProperty("user.dir"));
			choptions = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "zanui" + File.separator + "config" + File.separator + "resources" + File.separator + "chromedriver.exe");
			chromePrefs = new HashMap<String, Object>(); 
			chromePrefs.put("profile.default_content_settings.popups", 0); 
			chromePrefs.put("download. default directory", downloadFilepath); 
			chromePrefs.put("credentials enable service", false); 
			chromePrefs.put("profile.password manager enabled", false); 
			chromePrefs.put("safebrowsing.enabled", true); 
			choptions.setExperimentalOption("prefs", chromePrefs);
			choptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			choptions.setExperimentalOption("useAutomationExtension", false); 
			// choptions.addArguments ("--disable-infobars"); 
			choptions.addArguments("--test-type"); 
			choptions.addArguments("--disable-gpu"); 
			choptions.addArguments("--no-first-run");
			choptions.addArguments("--no-default-browser-check"); 
			choptions.addArguments("--ignore-certificate-errors"); 
			cap = DesiredCapabilities.chrome();
			if (launchType.equalsIgnoreCase("Headless")) {
				choptions.addArguments("--headless"); 
				choptions.addArguments("window-size=1920, 1080"); 
				cap.setCapability(ChromeOptions. CAPABILITY, choptions); 
				driver = new ChromeDriver(choptions);
			} else if (launchType.equalsIgnoreCase("Remote")) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setPlatform(Platform.WINDOWS);
				driver = new RemoteWebDriver(new URL("http://bme1-sgrid-1.intershop.com.au:4444/wd/hub"), capabilities);
			}
			
			else {
				cap.setCapability(ChromeOptions.CAPABILITY, choptions);
				driver = new ChromeDriver(choptions);
			}
			currWindw = driver.getWindowHandle(); 
			Constants.CurrentWindow = currWindw; 
			driver.manage().window().maximize(); 
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
			driver.manage().deleteAllCookies(); 
			setSession(driver); 
			break;
		}

		return driver;
	}

	/*************************************************************************
	 * Objective: Set the session details into the session.properties file
	 * Parameters: driver (WebDriver)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/
	public static void setSession (WebDriver driver) throws IOException { 
		try {
			HttpCommandExecutor executor = (HttpCommandExecutor) ((RemoteWebDriver) driver).getCommandExecutor(); 
			URL url = executor.getAddressOfRemoteServer(); 
			SessionId session_id = ((RemoteWebDriver) driver).getSessionId(); 
			String sessionID = session_id.toString(); 
			String URL = url.toString();
			OutputStream output = new FileOutputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "zanui" + File.separator + "config" + File.separator + "session.properties"); 
			Properties prop = new Properties(); 
			prop.setProperty("sessionID", sessionID); 
			prop.setProperty("URL", URL); 
			prop.store(output, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*************************************************************************
	 * Objective: Get the session details from the session.properties file
	 * Parameters: None
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static void getCurrentSession() { 
		try {
			InputStream input = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "intershop" + File.separator + "config" + File.separator + "session.properties"); 
			Properties prop = new Properties();

			// load a properties file 
			prop.load(input);
			// get the property value and print it out 
			String sessionID = prop.getProperty("sessionID"); 
			String URL = prop.getProperty("URL");
			URL url = new URL (URL); 
			RemoteWebDriver driver = createDriverFromSession(sessionID, url); 
			Constants.driver = driver;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*************************************************************************
	 * Objective: To create driver from session
	 * Parameters: sessionId (String), command_executor (URL)
	 * Author: Pooja Bagga
	 * Updated by and when:
	 **************************************************************************/

	public static RemoteWebDriver createDriverFromSession(final String sessionId, URL command_executor) {
		CommandExecutor executor = new HttpCommandExecutor(command_executor) {
			@Override 
			public Response execute(Command command) throws IOException {
				Response response = null; 
				if (command.getName() == "newSession") {
					response = new Response(); 
					response.setSessionId(sessionId); 
					response.setStatus(0); 
					response.setValue(Collections.<String, String>emptyMap());
					try {
						Field commandCodec = null; 
						commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
						commandCodec.setAccessible(true); 
						commandCodec.set(this, new W3CHttpCommandCodec());
						Field responseCodec = null; 
						responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec"); 
						responseCodec.setAccessible(true);
						responseCodec.set(this, new W3CHttpResponseCodec()); 
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} 
				}
				else {
					response = super.execute(command);
				}
				return response;
			}
		};
		return new RemoteWebDriver(executor, new DesiredCapabilities());
	}


}


