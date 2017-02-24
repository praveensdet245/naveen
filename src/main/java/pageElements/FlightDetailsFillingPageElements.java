package pageElements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import genericLibrary.CommonUtils;

/**
 * This utility contains filling data of travellers methods
 * @author kanakam.kumar
 *
 */
public class FlightDetailsFillingPageElements {

	WebDriver driver;
	WebDriverWait webDriverWait;
	CommonUtils commonUtils;
	Logger log = Logger.getLogger(FlightDetailsFillingPageElements.class);

	public FlightDetailsFillingPageElements(WebDriver driver) {
		this.driver = driver;
		commonUtils = new CommonUtils(driver);
		PageFactory.initElements(driver, FlightDetailsFillingPageElements.class);
	}
	
	@FindBy(id="insurance_confirm")
	private WebElement insuranceConformCheckInBox;
	
	@FindBy(id="itineraryBtn")
	private WebElement continueBookingButton;
	
	@FindBy(id="username")
	private WebElement emailAddressInput;
	
	@FindBy(id="LoginContinueBtn_1")
	private WebElement emailAddressContinueButton;
	
	@FindBy(id="AdultTitle1")
	private WebElement travellers_name_title_dropdown;
	
	@FindBy(id="AdultFname1")
	private WebElement travellers_firstNameInput;
	
	@FindBy(id="AdultLname1")
	private WebElement travellers_lastNameInput;
	
	@FindBy(id="mobileNumber")
	private WebElement travellers_mobileNumber;
	
	@FindBy(id="travellerBtn")
	private WebElement travellers_continueButton;
	
	/**
	 * 
	 */
	public void clickOnContinueBookingButton(){
		try{
			this.insuranceConformCheckInBox.click();
			this.continueBookingButton.click();
		}catch(Exception e){
			log.error("Unable to continue booking because ",e);
		}
	}
	/**
	 * 
	 * @param emailId
	 */
	public void emailAddress_enterEmailToContinue(String emailId){
		try{
			commonUtils.waitForElement(this.emailAddressInput);
			this.emailAddressInput.sendKeys(emailId);
			this.emailAddressContinueButton.click();
			log.info("Email entered successfully to continue flight booking... "+emailId);
		}catch(Exception e){
			log.error("Unable to continue from email address block..",e);
		}
	}
	
	/**
	 * 
	 * @param suffix
	 * @param firstName
	 * @param lastName
	 * @param mobileNumberWithCountryCode
	 */
	public void travellers_EnterTravellersDetailsAndContinue(String suffix,String firstName,String lastName,String mobileNumberWithCountryCode){
		try{
			commonUtils.waitForElement(this.travellers_name_title_dropdown);
			if(suffix.equalsIgnoreCase("Mr")){
				commonUtils.selectByVisibleText(this.travellers_name_title_dropdown, "Mr");
			}else if(suffix.equalsIgnoreCase("Ms")){
				commonUtils.selectByVisibleText(this.travellers_name_title_dropdown, "Ms");
			}else if(suffix.equalsIgnoreCase("Mrs")){
				commonUtils.selectByVisibleText(this.travellers_name_title_dropdown, "Mrs");
			}else{
				log.warn("Invalid title entered : "+suffix);
			}
			
			this.travellers_firstNameInput.sendKeys(firstName);
			this.travellers_lastNameInput.sendKeys(lastName);
			this.travellers_mobileNumber.sendKeys(mobileNumberWithCountryCode);
			this.travellers_continueButton.click();
		}catch(Exception e){
			log.error("Unable to continue in travellers block with entered details {"+suffix+" "+"firstName"+" "+"lastName"+" "+"mobileNumberWithCountryCode"+"}/n",e);
		}
	}
}
