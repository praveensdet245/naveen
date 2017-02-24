package pageElements;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This utility contains methods which are used to book flights
 * @author kanakam.kumar
 *
 */
public class FlightsPageElements {
	
	WebDriver driver;
	WebDriverWait webDriverWait;
	Logger log = Logger.getLogger(FlightsPageElements.class);

	public FlightsPageElements(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, FlightsPageElements.class);
	}
	
	@FindBy(xpath="//div[@class='progress']")
	private WebElement progressBarFlightSearch;
	
	@FindBy(xpath="//button[text(),'Book']")
	private WebElement bookButtonForFlight;
	
	@FindBy(id="insurance_confirm")
	private WebElement insuranceConformCheckInBox;
	
	@FindBy(id="itineraryBtn")
	private WebElement continueBookingButton;
	
	
	
	private void waitTillProgressBarDisappear(){
		try{
			webDriverWait = new WebDriverWait(driver, 40);
			webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='progress'])")));
		}catch (Exception e) {
			log.info("Unable to wait till progress bar disappear...",e);
		}
	}
	
	public void clickBookButton(){
		try{
			this.waitTillProgressBarDisappear();
			this.bookButtonForFlight.click();
		}catch (Exception e) {
			log.info("Unable to click on Book button...",e);
		}
	}
	

}
