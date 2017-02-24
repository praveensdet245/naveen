package genericLibrary;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


/**
 * This utility is <strong>Listener</strong>,this class name user either mention in <br>
 * individual class or testng.xml
 * @author praveen.k
 * @version 1.0
 */
public class TestngListener implements ITestListener,ISuiteListener{
	private static Logger log = Logger.getLogger(TestngListener.class.getName());
	
    CommonUtils commonUtils;
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		log.info("PASS Test "+result.getName()+" Passed In "+result.getTestClass()+" class");
	}

	public void onTestFailure(ITestResult result) {
		commonUtils = new CommonUtils(Driver.getDriverInstance());
		log.error("FAIL Test "+result.getName().toString().trim()+" was failed In "+result.getTestClass()+" class");
		log.error(result.getThrowable());
		String screenShotName = "FAIL_"+result.getTestClass().getName()+"__"+result.getName().toString().trim();
		String fullyQualifiedScreenshotName = System.getProperty("user.dir")+"\\ScreenShots\\"+screenShotName+".png";
		commonUtils.takeScreenShot(screenShotName);
		MonitorMail.onErrorSendScreenshot(fullyQualifiedScreenshotName, result.getName().toString().trim());
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		log.warn("SKIP Test "+result.getName().toString().trim()+" was Skipped In "+result.getTestClass()+" class");
		log.warn(result.getThrowable().getStackTrace());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		log.info("SUITE started at { "+TestngListener.getTime()+" } with the name of { "+suite.getName()+" }...");
	}

	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		log.info("SUITE Ended at { "+TestngListener.getTime()+" } with trhe name of { "+suite.getName()+" }...");
	}
    
	private static Date getTime() {
	        Calendar calendar = Calendar.getInstance();
	        return calendar.getTime();        
	    }

}
