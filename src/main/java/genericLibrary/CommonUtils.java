package genericLibrary;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * CommonUtils is the class where common methods are all defined<br>
 * and reusable methods defined,this class allows to let user access<br>
 * reusable methods by creating object instance for this..<br>
 * 
 * @author praveen.k
 * @version 1.0 <br>
 */
public class CommonUtils {

	private static Logger log = Logger.getLogger(CommonUtils.class.getName());
	boolean flag = false;
	Select select;
	Actions action;
	Robot robot;
	WebDriver driver;

	public CommonUtils(WebDriver driver) {
		this.driver = driver;
	}

	// implicit wait
	/**
	 * This method tells that WebDriver to poll the DOM for a certain amount of
	 * time when trying to find an element or elements if they are not
	 * immediately available. The default setting is 0. Once set, the implicit
	 * wait is set for the life of the WebDriver object instance
	 * 
	 */
	public void waitForPageToLoad() {
		String TimeInSecondsString = null;
		int TimeInSeconds = 0;
		try {
			Thread.sleep(1000);
			TimeInSecondsString = PropertiesUtil.getPropValues("ImplicitWait_Time");
			TimeInSeconds = Integer.parseInt(TimeInSecondsString);
			driver.manage().timeouts().implicitlyWait(TimeInSeconds, TimeUnit.SECONDS);
			log.info("Wait For Page To Load Executed For :" + TimeInSeconds + " Seconds");
		} catch (Exception e) {
			log.error("Unable To Wait For Page To Load For " + TimeInSeconds + " Seconds\n", e);

		}
	}

	/**
	 * An explicit waits is code you define to wait for a certain condition to
	 * occur before proceeding further in the code
	 * 
	 * @param ElementToBePresent
	 *            till this element presents wait
	 */
	public void waitForElement(WebElement ElementToBePresent) {
		String TimeInSecondsString = null;
		int timeToWaitInSeconds = 0;
		try {
			Thread.sleep(1000);
			TimeInSecondsString = PropertiesUtil.getPropValues("ExplicitWait_Time");
			timeToWaitInSeconds = Integer.parseInt(TimeInSecondsString);
			WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSeconds);
			wait.until(ExpectedConditions.visibilityOf(ElementToBePresent));
			log.info("Wait For Element Successfully Executed With In time :" + timeToWaitInSeconds);
		} catch (Exception e) {
			log.error("Unable To Execute waitForElement Method  ", e);
		}
	}

	// verify text is there or not in particular webelement
	/**
	 * Get the visible (i.e. not hidden by CSS) innerText of this element
	 * including sub-elements, without any leading or trailing whitespace
	 * 
	 * @param ElementToBeLocated
	 * @param Text
	 * @return boolean (if given text exist in given element)
	 */
	public boolean verifyText(WebElement ElementToBeLocated, String Text) {

		log.info("Entered Webelement is  :" + ElementToBeLocated.toString());
		log.info("Entered Text is  :" + Text);
		try {
			if (ElementToBeLocated.isDisplayed()) {

				log.info("Entered Element Is Displayed  :" + ElementToBeLocated);

				String responseText = ElementToBeLocated.getText();

				log.info("Resultent Text For verifyText Method is :" + responseText);

				if (Text.equals(responseText)) {
					flag = true;
					log.info("Entered Text Is Present,Returned Boolean Value is TRUE..... ");
				} else {
					log.error("Text Is Not Present In Webelement  :" + ElementToBeLocated);
				}
			} else {
				log.warn("Entered Webelement is Not Displayed :" + ElementToBeLocated);
			}
		} catch (Exception e) {
			log.error("Unable To Execute VerifyText Method for Entered Arguments { (Webelement = " + ElementToBeLocated
					+ ") , (String Text = " + Text + ") }", e);
		}
		return flag;
	}

	// get page title
	/**
	 * This utility is to get current page title
	 * 
	 * @return pageTitle
	 */
	public String getPageTitle() {
		String pageTitle = null;
		try {
			pageTitle = driver.getTitle();
			log.info("Page title is : " + pageTitle);
		} catch (Exception e) {
			log.error("Unable to get current page title.. " + e);
		}
		return pageTitle;
	}

	// verify text is present or not in entire page
	/**
	 * This method verify wether given text exist in page or not
	 * 
	 * @param TextToBePresent
	 * @return boolean if given text exist in page
	 */
	public boolean verifyTextPresent(String TextToBePresent) {
		log.info("Entered Text To Be Present for verifyTextPresent Method is :" + TextToBePresent);
		try {
			String pageSource = driver.getPageSource();
			if (pageSource.contains(TextToBePresent)) {
				flag = true;
				log.info("Entered Text is Present In Current Page,,Returned Value from verifyTextPresent is TRUE.....");
			} else {
				log.error("Text Is Not Present in HTML Page ");
			}
		} catch (Exception e) {
			log.error("Unable To Execute verifyTextPresent Method \n", e);
		}

		return flag;
	}

	// drop down value select by index
	/**
	 * Select the option at the given index. This is done by examing the "index"
	 * attribute of an element, and not merely by counting.
	 * 
	 * @param SelectBoxWebelement
	 * @param Index
	 * @return boolean
	 */
	public boolean selectByIndex(WebElement SelectBoxWebelement, int Index) {
		try {
			select = new Select(SelectBoxWebelement);
			select.selectByIndex(Index);
			flag = true;
			log.info(Index + " Index Value Is Selected From Select Box Webelement...");
		} catch (Exception e) {
			log.error("Unable to Select By Index :" + Index + " ..  \n", e);
		}
		return flag;
	}

	// drop down value select by visible text
	/**
	 * Select all options that display text matching the argument. That is, when
	 * given "Bar" this would select an option like: value=foo
	 * 
	 * @param SelectBoxWebelement
	 * @param VisibleText
	 * @return boolean
	 */
	public boolean selectByVisibleText(WebElement SelectBoxWebelement, String VisibleText) {
		try {
			select = new Select(SelectBoxWebelement);
			select.selectByVisibleText(VisibleText);
			flag = true;
			log.info("selectByVisibleText Executed Successfully For the Arguments { (String : " + VisibleText + ") }");
		} catch (Exception e) {
			log.error("Unable To Execute selectByVisibleText Method For the Arguments { (String : " + VisibleText
					+ ") }\n", e);
		}
		return flag;
	}

	// drop down value select by value
	/**
	 * Select option that have a value matching the argument.
	 * 
	 * @param SelectBoxWebelement
	 * @param Value
	 * @return boolean
	 */
	public boolean selectByValue(WebElement SelectBoxWebelement, String Value) {
		try {
			select = new Select(SelectBoxWebelement);
			select.selectByValue(Value);
			flag = true;
			log.info("selectByValue Executed Successfully For the Arguments { (String : " + Value + ") }");

		} catch (Exception e) {
			log.error("Unable To Execute selectByValue Method For the Arguments { ,(String : " + Value + ") }", e);
		}
		return flag;
	}

	// drop down select all options
	/**
	 * This method is used to select all options from the dropdown element if
	 * that dropdown is multi select dropdown
	 * 
	 * @param SelectBoxWebelement
	 * @return boolean
	 */
	public boolean selectAll(WebElement SelectBoxWebelement) {
		try {
			select = new Select(SelectBoxWebelement);
			if (select.isMultiple()) {
				List<WebElement> alloptions = select.getOptions();
				for (int i = 0; i < alloptions.size(); i++) {
					alloptions.get(i).click();
				}
				flag = true;
				log.info("All Options Are Selected For Select Box Webelement :" + SelectBoxWebelement);
			} else {
				log.error("Entered Select Box Webelement is not MultiSelectBox");
			}

		} catch (Exception e) {
			log.error("Unable To Execute selectAll Method For the Arguments { (WebElement : " + SelectBoxWebelement
					+ ")}", e);
		}
		return flag;
	}

	// dropdown box returning all options
	/**
	 * This method is used to get all options from the dropdown element
	 * 
	 * @param SelectBoxWebelement
	 * @return List of Strings
	 */
	public List<String> selectReturnAllOptions(WebElement SelectBoxWebelement) {

		List<String> allOptionsTexts = new ArrayList<String>();

		@SuppressWarnings("unused")
		String[] options = null;
		try {
			select = new Select(SelectBoxWebelement);
			List<WebElement> allOptionsWebelements = select.getOptions();
			for (int i = 0; i < allOptionsWebelements.size(); i++) {
				String webelementText = allOptionsWebelements.get(i).getText();
				allOptionsTexts.add(webelementText);
			}
			log.info("All Options From Select Box are  :" + allOptionsTexts);
		} catch (Exception e) {
			log.error("Unable To Return All Values...  ", e);
		}
		return allOptionsTexts;
	}

	// drop down deselect all
	/**
	 * Clear all selected entries. This is only valid when the SELECT supports
	 * multiple selections.
	 * 
	 * @param SelectBoxWebelement
	 * @return boolean
	 * @throws java.lang.UnsupportedOperationException
	 *             - If the SELECT does not support multiple selections
	 */
	public boolean deselectAll(WebElement SelectBoxWebelement) {
		try {
			select = new Select(SelectBoxWebelement);
			select.deselectAll();

			flag = true;
			log.info("Deselected All Options From Select Box Webelement  :" + SelectBoxWebelement);
		} catch (Exception e) {
			log.error("Unable To Execute deselectAll Method For the Arguments { (WebElement : " + SelectBoxWebelement
					+ ")}", e);
		}
		return flag;
	}

	// drop down deselect by index
	/**
	 * Deselect the option at the given index. This is done by examing the
	 * "index" attribute of an element, and not merely by counting.
	 * 
	 * @param SelectBoxWebelement
	 * @param Index
	 * @return boolean if option deselected by index properly
	 */
	public boolean deselectByIndex(WebElement SelectBoxWebelement, int Index) {
		try {
			select = new Select(SelectBoxWebelement);
			select.deselectByIndex(Index);
			flag = true;
			log.info("deselectByIndex Method is Executed Successfully For Arguments { (Int :" + Index + ") }");
		} catch (Exception e) {
			log.error("Unable To Execute selectByVisibleText Method For the Arguments { (Int : " + Index + ") }", e);
		}
		return flag;
	}

	// drop down deselect by visible text
	/**
	 * Deselect option that display text matching the argument.
	 * 
	 * @param SelectBoxWebelement
	 * @param VisibleText
	 * @return if method executed successfully
	 */
	public boolean deselectByVisibleText(WebElement SelectBoxWebelement, String VisibleText) {
		try {
			select = new Select(SelectBoxWebelement);
			select.deselectByVisibleText(VisibleText);
			flag = true;
			log.info("deselectByVisibleText Method is Executed Successfully For Arguments { (String :" + VisibleText
					+ ") }");
		} catch (Exception e) {
			log.error("Unable To Execute deselectByVisibleText Method For the Arguments { (String : " + VisibleText
					+ ") }", e);
		}
		return flag;
	}

	// drop down deselect by value
	/**
	 * Deselect option that have a value matching the argument.
	 * 
	 * @param SelectBoxWebelement
	 * @param Value
	 * @return boolean if method executed successfully
	 */
	public boolean deselectByValue(WebElement SelectBoxWebelement, String Value) {
		try {
			select = new Select(SelectBoxWebelement);
			select.deselectByValue(Value);

			log.info("deselectByValue Method is Executed Successfully For Arguments { (String :" + Value + ") }");
			flag = true;
		} catch (Exception e) {
			log.error("Unable To Execute deselectByValue Method For the Arguments { (String : " + Value + ") }", e);
		}
		return flag;
	}

	// drop down all values
	/**
	 * This method is used to get all options from dropdown element
	 * 
	 * @param SelectBoxWebelement
	 * @return List of Webelements
	 */
	public List<WebElement> selectBoxAllValues(WebElement SelectBoxWebelement) {
		List<WebElement> allOptions = null;
		try {
			select = new Select(SelectBoxWebelement);
			allOptions = select.getOptions();
		} catch (Exception e) {
			log.error("Unable To Execute selectBoxAllValues...", e);
		}
		return allOptions;
	}

	// drop down selected options
	/**
	 * This method is used to get all selected options from dropdown element
	 * 
	 * @param SelectBoxWebelement
	 * @return List of webelements
	 */
	public List<WebElement> selectBoxSelectedOptions(WebElement SelectBoxWebelement) {
		List<WebElement> SelectedOptions = null;
		try {
			select = new Select(SelectBoxWebelement);
			SelectedOptions = select.getAllSelectedOptions();

		} catch (Exception e) {
			log.error("Unable To Execute selectBoxSelectedOptions...", e);
		}
		return SelectedOptions;
	}

	// drop down first selected option
	/**
	 * The first selected option in this select tag (or the currently selected
	 * option in a normal select)
	 * 
	 * @param SelectBoxWebelement
	 * @return String
	 */
	public String selectBoxFirstedSelectedOption(WebElement SelectBoxWebelement) {
		String returnedValue = null;
		try {
			select = new Select(SelectBoxWebelement);
			returnedValue = select.getFirstSelectedOption().toString();

		} catch (Exception e) {
			log.error("Unable To Execute selectBoxFirstedSelectedOption...", e);
		}
		return returnedValue;
	}

	// Actions drag and drop functionality using drag and drop
	/**
	 * <strong>This method is used to perform Drag and Drop action on
	 * element</strong> The user-facing API for emulating complex user gestures.
	 * Use this class rather than using the Keyboard or Mouse directly.
	 * Implements the builder pattern: Builds a CompositeAction containing all
	 * actions specified by the method calls.
	 * 
	 * @param Source
	 *            which element needs to be dragged
	 * @param Destination
	 *            to where it has to be dropped
	 * @return boolean
	 */
	public boolean actionDragAndDrop(WebElement Source, WebElement Destination) {
		try {
			action = new Actions(driver);
			action.dragAndDrop(Source, Destination).perform();

			log.info("actionDragAndDrop Method is Successfully Executed.......");
			flag = true;
		} catch (Exception e) {
			log.error("Unable To Execute actionDragAndDrop Method ...", e);
		}
		return flag;
	}

	public void actionOpenNewTab() {
		try {
			Toolkit.getDefaultToolkit().getSystemClipboard();
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			log.info("Browser opened new tab....");
		} catch (Exception e) {
			log.error("Unable To Execute actionOpenNewTab Method to open new tab...", e);
		}
	}

	// Actions drag and drop functionality using click and hold and move to
	// element method
	/**
	 * <strong>This method is used to perform Drag and Drop action on
	 * element</strong> The user-facing API for emulating complex user gestures.
	 * Use this class rather than using the Keyboard or Mouse directly.
	 * Implements the builder pattern: Builds a CompositeAction containing all
	 * actions specified by the method calls.
	 * 
	 * @param Source
	 *            which element needs to be dragged
	 * @param Destination
	 *            to where it has to be dropped
	 * @return boolean
	 */
	public boolean actionDragAndDropUsingClickAndHold(WebElement Source, WebElement Destination) {
		try {
			action = new Actions(driver);
			action.clickAndHold(Source).perform();
			log.info("Source Element Found Successfull for The Method actionDragAndDropUsingClickAndHold");
			action.moveToElement(Destination).perform();
			log.info("Destination Element Found Successfull for The Method actionDragAndDropUsingClickAndHold");
			action.release().build();

			log.info("actionDragAndDropUsingClickAndHold Method is Successfully Executed.......");
			flag = true;
		} catch (Exception e) {
			log.error("Unable To Execute actionDragAndDropUsingClickAndHold Method ...", e);
		}
		return flag;
	}

	public boolean actionDragAndDropBasedOnCoordinates(WebElement source, int x_coordinate, int y_coordinate) {
		try {
			action = new Actions(driver);
			action.dragAndDropBy(source, x_coordinate, y_coordinate).perform();
			action.release().build();
			log.info("actionDragAndDropBasedOnCoordinates Method is Successfully Executed.......");
			flag = true;
		} catch (Exception e) {
			log.error("Unable To Execute actionDragAndDropBasedOnCoordinates Method ...", e);
		}
		return flag;
	}

	// Actions double click
	/**
	 * <strong>This method is used to perform double clik on element</strong>
	 * The user-facing API for emulating complex user gestures. Use this class
	 * rather than using the Keyboard or Mouse directly. Implements the builder
	 * pattern: Builds a CompositeAction containing all actions specified by the
	 * method calls.
	 * 
	 * @param onElement
	 * @return boolean if method executed successfully
	 */
	public boolean actionDoubleClick(WebElement onElement) {
		try {
			action = new Actions(driver);
			action.doubleClick(onElement);

			log.info("actionDoubleClick Method Successfully Executed...");
			flag = true;
		} catch (Exception e) {
			log.error("Unable To Execute actionDoubleClick Method...", e);
		}
		return flag;
	}

	// Multiple Window ID's
	public Set<String> MultipleWindowIDs() {
		Set<String> windowids = null;
		try {
			windowids = driver.getWindowHandles();
			log.info("MultipleWindowIDs Method Successfully Executed...");
		} catch (Exception e) {
			log.error("Unable To Execute MultipleWindowIDs Method...", e);
		}
		return windowids;
	}

	// javascript Executor
	/**
	 * Executes JavaScript in the context of the currently selected frame or
	 * window. The script fragment provided will be executed as the body of an
	 * anonymous function Within the script, use document to refer to the
	 * current document. Note that local variables will not be available once
	 * the script has finished executing, though global variables will persist.
	 * 
	 * @param JavaScript
	 *            The JavaScript to execute
	 * @return boolean if javascript executed successfully
	 */
	public boolean javaScriptExecutor(String JavaScript) {
		log.info("javaScriptExecutor Method Argument is  :" + JavaScript);
		try {
			((JavascriptExecutor) driver).executeScript(JavaScript);
			log.info("javaScriptExecutor Method Executed Successfully For Argument  :" + JavaScript);
			flag = true;
		} catch (Exception e) {
			log.error("Unable To Execute javaScriptExecutor Method For Argument  :" + JavaScript, e);
		}
		return flag;
	}

	/*
	 * //file upload using send keys
	 *//**
		 * This method is used to upload file by which element has input as a
		 * html tag
		 * 
		 * @param UploadButton
		 * @param filePath
		 * @return boolean if file uploaded successfully
		 *//*
		 * public boolean fileUploadSendKeys(WebElement UploadButton,String
		 * filePath){ try{ UploadButton.sendKeys(filePath); log.info(
		 * "File Uploaded Successfully using fileUploadSendKeys Method...");
		 * flag = true; }catch(Exception e){ log.error(
		 * "Unable To Execute fileUploadSendKeys Method...", e); } return flag;
		 * }
		 */

	// file uploadng using robot class
	/**
	 * This utility is to upload file from particular location
	 * 
	 * @param filePath
	 */
	public void uploadFile(String filePath) {
		File file;
		StringSelection stringSelection;

		try {
			file = new File(filePath);
			if (file.exists()) {
				stringSelection = new StringSelection(filePath);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				log.info("File uploaded sucessfully from the path { " + filePath + " }");
			}
		} catch (Exception e) {
			log.error("Unable to upload file from { " + filePath + " } ", e);
		}
	}
	/*
	 * //file upoload //file upload using autoit exe file
	 *//**
		 * This method is used to upload file using autoIT 3rd party tool
		 * 
		 * @param AutoITexeFilePath
		 * @return boolean if file uploaded successfully
		 *//*
		 * public boolean fileUploadAutoIT(String AutoITexeFilePath){ File file;
		 * try{ file = new File(AutoITexeFilePath); if(file.exists()){ log.info(
		 * "Given File Exist in "+AutoITexeFilePath);
		 * Runtime.getRuntime().exec(AutoITexeFilePath); log.info(
		 * "File Uploaded Successfully using fileUploadAutoIT Method..."); flag
		 * = true; }else{ log.warn("AutoIT exe File Doesn't Exist :"
		 * +AutoITexeFilePath); } }catch(Exception e){ log.error(
		 * "Unable To Execute fileUploadAutoIT Method.." , e); } return flag; }
		 */

    /**
     * This utililty is used to take screenshot
     * @param screenshotName
     * @return String 
     */
	public String takeScreenShot(String screenshotName) {
		String filepath = "ScreenShots";
		screenshotName = screenshotName.replace(":", "_");
		if (new File(filepath).isDirectory()) {
			log.info(filepath + " Directory Exist");
		} else {
			log.warn(filepath + " doesn't exist..");
			new File(filepath).mkdir();
			log.info(filepath + " Directory Created Successfully...");
		}
		String fullyQualifiedScreenshot = filepath + "\\" + screenshotName + ".png";
		log.info("Full screen shot name along with path is : " + fullyQualifiedScreenshot);
		try {
			File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			log.info("Screenshot Name : " + fullyQualifiedScreenshot);
			FileUtils.copyFile(srcfile, new File(fullyQualifiedScreenshot));
			log.info(fullyQualifiedScreenshot);
			log.info("Screenshot Saved in :" + filepath + " location ,with " + screenshotName + " name...");
		} catch (Exception e) {
			log.error("Unable to Execute Screenshot method...", e);
		}
		return fullyQualifiedScreenshot;

	}

	/**
	 * This method is used to maximize the window
	 */
	public void maximizeWindow() {
		try {
			driver.manage().window().maximize();
			log.info("Window Maximized Successfully.....");
		} catch (Exception e) {
			log.warn("Unable To Maximize Window...");

		}
	}

	/**
	 * This method is used to delete all cookies
	 */
	public void deleteAllCookies() {
		try {
			driver.manage().deleteAllCookies();
		} catch (Exception e) {
			log.warn("Unable To Delete All Cookies...");
		}
	}

	/**
	 * This method is used to change the driver control to parent window
	 */
	@SuppressWarnings("unused")
	public boolean switchToParentWindow() {
		try {
			Set<String> windowIds = driver.getWindowHandles();
			Iterator<String> iterator = windowIds.iterator();
			String parentWindowId = iterator.next();
			String childWindowId = iterator.next();
			driver.switchTo().window(parentWindowId);
			log.info("Driver control switched to Parent Window sucessfully...");
			flag = true;
		} catch (Exception e) {
			log.error("Unable to switch to Parent window...", e);
		}
		return flag;
	}

	/**
	 * This method is used to change the driver control to child window
	 * 
	 * @return
	 */
	public boolean switchToChildWindow() {
		try {
			Set<String> windowIds = driver.getWindowHandles();
			Iterator<String> iterator = windowIds.iterator();
			@SuppressWarnings("unused")
			String parentWindowId = iterator.next();
			String childWindowId = iterator.next();
			driver.switchTo().window(childWindowId);
			log.info("Driver control switched to Child Window sucessfully...");
			flag = true;
		} catch (Exception e) {
			log.error("Unable to switch to Child window...", e);
		}
		return flag;
	}

}
