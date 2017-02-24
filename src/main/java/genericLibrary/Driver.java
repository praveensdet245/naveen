package genericLibrary;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * This is the class where driver defined as static
 * 
 * @author praveen.k
 * @version 1.0
 */

public class Driver {
	private static Logger log = Logger.getLogger(Driver.class.getName());
	private static WebDriver driver;

	/**
	 * This is the main utility which can return browser instance based on name which is mentioned in properties file
	 * @return
	 */

	public static WebDriver getDriverInstance() {
		
		try {
			driver = Driver.getWebdriverInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Unable to get driver instance ....",e);
		}
		
		return driver;
	}

	private static WebDriver getWebdriverInstance() throws IOException {
		String browserInstance = PropertiesUtil.getPropValues("Browser");
		log.info("Browser Instance : " + browserInstance);

		if (browserInstance.equalsIgnoreCase("firefox") || (browserInstance.contains("firefox"))) {
			System.setProperty("webdriver.gecko.driver","DriverFiles/geckodriver");
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.dir", "DownloadFiles");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/pdf,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			driver = new FirefoxDriver(profile);
			log.info("Firefox browser invoked successfully.......");
		} else if (browserInstance.equalsIgnoreCase("chrome") || (browserInstance.contains("chrome"))) {
			System.setProperty("webdriver.chrome.driver", "DriverFiles/chromedriver");
			String downloadFilepath = "DownloadFiles";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--test-type");
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(cap);
			log.info("Chrome browser invoked successfully.......");
		} else {
			log.error("Invali dbrowser name ....");
		}
		return driver;
	}

}
