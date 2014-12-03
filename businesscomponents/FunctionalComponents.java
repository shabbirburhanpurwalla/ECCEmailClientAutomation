package businesscomponents;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import pageobjects.*;

/**
 * Functional Components class
 * @author Cognizant
 */


/*
 * Last modified by: Ravi
 * Last modified on: 10/13/2014
 * Area of modification: Updated Comments
 * */

public class FunctionalComponents extends ReusableLibrary
{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	
	
	/*******************************************************
	 * Constructor to initialize the component library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	public FunctionalComponents(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	

	
	/*******************************************************
	 * Verify Email App is invoked successfully
	 * 
	 * @author 387478
	 * @lastmodifiedon 2 Dec 2014
	 * *****************************************************
	 */
	public void invokeApplication() throws InterruptedException{		
		//Thread.sleep(2000);
		int timeout = 10;
		
		if(commonFunction.waitForElementVisibility(PageObjects.lbl_LastUpdated.getProperty(),timeout))
			report.updateTestLog("Invoke Email Application","Email application invoked successfully.", Status.PASS);
		else{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Email App", "Email app not loaded");
		}
		
		
	}
	
	/*******************************************************
	 * Search email based on Account Number
	 * 
	 * @author 387478
	 * @lastmodifiedon 2 Dec 2014
	 * *****************************************************
	 */
	public void searchEmail(){
			String ba = commonFunction.getData("Accounts", "AccountNumber", "Account Number", true);
			Boolean emailFound = false;
			
			List <WebElement> emailList = driver.findElements(By.className("android.view.View"));	       
			System.out.println(emailList.size());
	       
			for(int i=0; i<emailList.size(); i++){
				
			}
			if(!emailFound){
				report.updateTestLog("Search Email","Email for Account Number '"+ ba +"' not found", Status.FAIL);
			}else{
				report.updateTestLog("Search Email","Email for Account Number '"+ ba +"' found", Status.PASS);
				frameworkParameters.setStopExecution(true);
				throw new FrameworkException("Search Email","Email for Account Number '"+ ba +"' found");
			}
	        //emailList.get(4).click();
	        
	}
}