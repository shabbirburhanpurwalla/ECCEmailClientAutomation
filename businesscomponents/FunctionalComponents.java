package businesscomponents;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
		int timeout = 5;
		
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		
		if(variables.getEmailClient().equalsIgnoreCase("aol"))
			invokeAOL(timeout);
		else if(variables.getEmailClient().equalsIgnoreCase("yahoo"))
			invokeYahoo(timeout);
		else if(variables.getEmailClient().equalsIgnoreCase("outlook"))
			invokeOutlook(timeout);
		else if(variables.getEmailClient().equalsIgnoreCase("aquamail"))
			invokeAquaMail(timeout);
		else if(variables.getEmailClient().equalsIgnoreCase("gmail"))
			invokeGmail(timeout);
		else if(variables.getEmailClient().equalsIgnoreCase("default"))
			invokeDefault(timeout);		
	}
	
	public void invokeDefault(int timeout){
		if(commonFunction.waitForElementVisibility(PageObjects.lbl_DefaultLastUpdated.getProperty(),timeout))
			report.updateTestLog("Invoke Email Application","Email application invoked successfully.", Status.PASS);
		else{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Email App", "Email app not loaded");
		}	
	}
	public void invokeAOL(int timeout){
		driver.findElement(By.id("com.aol.mobile.aolapp:id/menubar_mail")).click();
		if(commonFunction.waitForElementVisibility(PageObjects.lbl_AOL_InboxText.getProperty(),timeout))
			report.updateTestLog("Invoke Email Application","Email application invoked successfully.", Status.PASS);
		else{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Email App", "Email app not loaded");
		}	
	}
	
	public void invokeYahoo(int timeout){
		if(commonFunction.waitForElementVisibility(PageObjects.lbl_Yahoo_InboxText.getProperty(),timeout))
			report.updateTestLog("Invoke Email Application","Email application invoked successfully.", Status.PASS);
		else{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Email App", "Email app not loaded");
		}	
	}
	public void invokeOutlook(int timeout){

	}
	
	public void invokeGmail(int timeout){
		if(commonFunction.waitForElementVisibility(PageObjects.lbl_Gmail_PrimaryTitle.getProperty(),timeout)){
			String title = driver.findElementById(PageObjects.lbl_Gmail_PrimaryTitle.getProperty()).getText();
			if(title.toLowerCase().equals("primary"))
				report.updateTestLog("Invoke Email Application","Email application(Primary Tab) invoked successfully.", Status.PASS);
			else{
				frameworkParameters.setStopExecution(true);
			    throw new FrameworkException("Email App", "Email app(Primary tab) not loaded");
			}
		}
			
		else{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Email App", "Email app not loaded");
		}
	}
	
	public void invokeAquaMail(int timeout){
		try{
			List <WebElement> folders = driver.findElementsByName("Folder: Inbox");
			if(folders.size()>0){
				folders.get(0).click();
				Thread.sleep(2000);
			}else{
				frameworkParameters.setStopExecution(true);
				throw new FrameworkException("Email App", "Email app not loaded");
			}
		}catch(Exception e){
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
		else if(variables.getEmailClient().toLowerCase().equals("aquamail"))
			searchAquaMail();
		
		else
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
	
	public void searchEmailAol() throws InterruptedException{
		List<WebElement> emailList = driver.findElementsByClassName("android.view.View");
		int emailCount = emailList.size();
		System.out.println(emailCount+" emails found");
		
		int i;
		if(emailCount > 0){
			
			//Retrieve Account Number
			String accountNumber = commonFunction.getData("Accounts", "AccountNumber", "Account Number", true);
			if(accountNumber.length()> 0 && accountNumber.length()<10)
				accountNumber = String.format("%010d", Integer.parseInt(accountNumber));
			accountNumber = commonFunction.insertCharAt(accountNumber, '-', 5);
			String accountNumberObj = "Account #" +accountNumber;
			
			//System.out.println(accountNumberObj);
			for(i=0;i<emailCount - 2;i++){
				WebElement currentEmail = emailList.get(i);
				try{
					currentEmail.click();
					Thread.sleep(2000);
					//com.aol.mobile.aolapp:id/message_header_show_hide_details
					//driver.findElementById("com.aol.mobile.aolapp:id/message_header_show_hide_details").click();
					driver.findElementById(PageObjects.lbl_AOL_EmailSubject.getProperty()).click();
					if(commonFunction.isElementPresent("NAME", accountNumberObj, "Email", false))
						break;
					
					driver.navigate().back();
					Thread.sleep(1000);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
			}
			if(i==emailCount - 2){
				frameworkParameters.setStopExecution(true);
				throw new FrameworkException("Search Email","Email not found for '"+accountNumber+"' in last "+ emailCount +" emails.");
			}else{
				System.out.println("Email Found....Performing validations");
				report.updateTestLog("Search Email","Email found for '"+accountNumber+"'.", Status.PASS);
			}
		}else{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Search Email","No emails found");
		
		}
	}
	
	public void searchAquaMail() throws InterruptedException{
		List<WebElement> emailList = driver.findElementsById("org.kman.AquaMail:id/message_item_root");
		int emailCount = emailList.size();
		System.out.println(emailCount +" emails found");
		
		
		int i;
		if(emailCount > 0){
			
			//Retrieve Account Number
			String accountNumber = commonFunction.getData("Accounts", "AccountNumber", "Account Number", true);
			if(accountNumber.length()> 0 && accountNumber.length()<10)
				accountNumber = String.format("%010d", Integer.parseInt(accountNumber));
			accountNumber = commonFunction.insertCharAt(accountNumber, '-', 5);
			String accountNumberObj = accountNumber + " Link";
			
			for(i=0;i<emailCount;i++){
				WebElement currentEmail = emailList.get(i);
				try{
					currentEmail.click();
					Thread.sleep(1000);
					
					driver.findElementById("org.kman.AquaMail:id/message_subject_short").click();
					if(commonFunction.isElementPresent("NAME", accountNumberObj, "Email", false))
						break;
					
					driver.navigate().back();
					Thread.sleep(1000);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
			}
			if(i==emailCount){
				frameworkParameters.setStopExecution(true);
				throw new FrameworkException("Search Email","Email not found for '"+accountNumber+"' in last "+ emailCount +" emails.");
			}else{
				System.out.println("Email Found....Performing validations");
				report.updateTestLog("Search Email","Email found for '"+accountNumber+"'.", Status.PASS);
			}
		}else{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Search Email","No emails found");
		
		}
	}
	
	
	/*********************************************************
	 * Function to return WebElement for PageObjects
	 * 
	 * This function takes ViewBillPageObjects object as an
	 * input and returns the WebElement object for the input 
	 * object 
	 * 
	 * @param viewBillPageEnum 
	 * 		  The enum element for which WebElement object needs to be 
	 *        created
	 * @return WebElement
	 * 		   WebElement object corresponding to the input enum is 
	 * 		   returned 
	 * ********************************************************
	 */

	private  WebElement getPageElement(PageObjects PageEnum)
	{
		try{

			return commonFunction.getElementByProperty(PageEnum.getProperty(), PageEnum
					.getLocatorType().toString());
		} catch(Exception e){
			report.updateTestLog("Get page element", PageEnum.toString()
					+ " object is not defined or found.", Status.FAIL);
			return null;
		}
	}
	
	
	
	
}