package pageElements;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import genericLibrary.CommonUtils;
/**
 * This utility contains basic web elements like from date, to date<br>
 * search button and all
 * @author kanakam.kumar
 *
 */
public class HomePageElements {
	WebDriver driver;
	Actions actions;
	Logger log = Logger.getLogger(HomePageElements.class);
	CommonUtils commonUtils;

	public HomePageElements(WebDriver driver) {
		this.driver = driver;
		commonUtils = new CommonUtils(driver);
		PageFactory.initElements(driver, HomePageElements.class);
	}

	@FindBy(id = "RoundTrip")
	private WebElement roundTripRadioButton;

	@FindBy(id = "FromTag")
	private WebElement fromInput;

	@FindBy(id = "ToTag")
	private WebElement toInput;

	@FindBy(id = "DepartDate")
	private WebElement departureDatePicker;

	@FindBy(id = "ReturnDate")
	private WebElement returnDatePicker;

	@FindBy(id = "Adults")
	private WebElement adultsDropdown;

	@FindBy(id = "SearchBtn")
	private WebElement searchButton;
    
	/**
	 * this methos is used to click on round trip radio button
	 */
	public void clickRoundTripRadioBtn() {
		this.roundTripRadioButton.click();
	}
    /**
     * this utility is used to enter from station
     * @param fromStation
     */
	public void enterFromStation(String fromStation) {
		try {
			this.fromInput.sendKeys(fromStation);
			this.clickEnter();
		} catch (Exception e) {
			log.error("Unable to enter from station....", e);
		}
	}
	/**
	 * this method is used to enter to station
	 * @param toStation
	 */
	public void enterToStation(String toStation){
		try {
			this.toInput.sendKeys(toStation);
			this.clickEnter();
		} catch (Exception e) {
			log.error("Unable to enter to station....", e);
		}
	}
	/**
	 * to enter departure date 
	 * @param departOnDate
	 */
	public void enterDepartOnDate(String departOnDate){
		try{
			this.departureDatePicker.clear();
			this.departureDatePicker.sendKeys(departOnDate);
		}catch(Exception e){
			log.error("Unable to enter departure date in Depart on date..",e);
		}
	}
	
	/**
	 * this is to enter return date 
	 * @param returnDate
	 */
	public void enterReturnDate(String returnDate){
		try{
			this.returnDatePicker.clear();
			this.returnDatePicker.sendKeys(returnDate);
		}catch(Exception e){
			log.error("Unable to enter return date in Return on date..",e);
		}
	}
	/**
	 * to select no.of passengers(adults)
	 * @param adultCount
	 */
	public void enterNoOfAdultCount(int adultCount){
		try{
			if(adultCount > 1){
				commonUtils.selectByVisibleText(this.adultsDropdown, adultCount+"");
			}
		}catch(Exception e){
			log.error("Unable to perform action on Adult count dropdown...",e);
		}
	}
	/**
	 * to click on search button
	 */
	public void clickSearchButton(){
		try{
			this.searchButton.click();
		}catch (Exception e) {
			log.error("Not able to perform action on Search button\n",e);
		}
	}
	

	private void clickEnter() {
		actions = new Actions(driver);
		actions.sendKeys(Keys.ENTER).perform();
	}

}
