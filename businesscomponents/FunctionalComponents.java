package businesscomponents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
	 * @throws InterruptedException 
	 * @lastmodifiedon 2 Dec 2014
	 * *****************************************************
	 */
	public void searchEmail() throws InterruptedException{
		if(variables.getEmailClient().toLowerCase().equals("aol"))
			searchEmailAol();
		if(variables.getEmailClient().toLowerCase().equals("default"))
			searchEmailDefault();
	}
	
	/*******************************************************
	 * Search email on Default Email Application
	 * 
	 * @author 387478
	 * @throws InterruptedException 
	 * @lastmodifiedon 5 Dec 2014
	 * *****************************************************
	 */
	public void searchEmailDefault(){
		List<WebElement> emailList = driver.findElementsById("com.android.email:id/item_container");
		int emailCount = emailList.size();
		System.out.print(emailCount-1 +" emails found");
		
		int i;
		if(emailCount > 1){
			String accountNumber = commonFunction.getData("Accounts", "AccountNumber", "Account Number", true);
			for(i=1;i<emailCount;i++){
				WebElement currentEmail = emailList.get(i);
				try{
					currentEmail.click();
					Thread.sleep(1000);
					driver.navigate().back();
					Thread.sleep(1000);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
			}
			if(i==emailCount){
				frameworkParameters.setStopExecution(true);
				throw new FrameworkException("Search Email","Email not found for '"+accountNumber+"' in last "+ emailCount +" emails.");
			}
		}else{
			report.updateTestLog("Search Email","No emails found.", Status.FAIL);
		}
	}
	
	public void searchEmailAol(){
		List<WebElement> emailList = driver.findElementsById("com.android.email:id/item_container");
		int emailCount = emailList.size();
		System.out.print(emailCount-1 +" emails found");
	}
}