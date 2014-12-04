package businesscomponents;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.cognizant.framework.Status;

import pageobjects.PageObjects;
import pageobjects.ViewBillPageObjects;

import componentgroups.CommonFunctions;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

/******************************************
 *EMBN Email
 *This class contains all the resuable keywords
 *for verification of EMBN Email 
 *
 *@author 387478
 *@last_mofidied_on 12/4 by Shabbir
 * 
 * */
public class EMBNEmail extends ReusableLibrary{

	public EMBNEmail(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	Actions action = new Actions(driver);
	
	/*****************************************************
	 *verifyHeader - CRAFT Keyword
	 *This method verifies the content of header section
	 *in EMBN email
	 *
	 *@author 387478
	 * @throws InterruptedException 
	 *@last_mofidied_on 12/4 by Shabbir
	 * 
	 ******************************************************/
	
	public void verifyHeader() throws InterruptedException{
		//Email Subject
		String expectedEmailSubject = commonFunction.getData("Accounts", "EmailSubject","Email Subject", true).trim();
		String actualEmailSubject ="";
		if(variables.getEmailClient().equalsIgnoreCase("aquamail"))
			actualEmailSubject = getPageElement(PageObjects.lbl_AquaMail_EmailSubject).getText();
		else if(variables.getEmailClient().equalsIgnoreCase("aol"))
			actualEmailSubject = getPageElement(PageObjects.lbl_AOL_EmailSubject).getText();
		commonFunction.verifyStringContainsText(actualEmailSubject,expectedEmailSubject,"Email Subject");
		
		
		//Login Link
		commonFunction.verifyIfElementIsPresent("NAME", "Login Link", "Login Link");
		
		//PayBill Link
		commonFunction.verifyIfElementIsPresent("NAME", "Pay Bill Link", "PayBill Link");
		
		//Contact US Link
		commonFunction.verifyIfElementIsPresent("NAME", "Contact Us Link", "Contact Us Link");
		
		//Verify Customer Name
		String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name", true);
		commonFunction.verifyIfElementIsPresent("NAME", customerName, "Customer Name");
		
		//AccountNumber
		if(variables.getEmailClient().equalsIgnoreCase("aquamail"))
			commonFunction.verifyIfElementIsPresent("NAME", "Account #", "Account Number");
		else if(variables.getEmailClient().equalsIgnoreCase("aol"))
			report.updateTestLog("Account Number","Account Number is displayed and verified.", Status.PASS);
			
		
		
		//Verify Email Address
		String emailAddress = commonFunction.getData("General_Data", "EmailAddress", "Email Address", true);
		if(variables.getEmailClient().equalsIgnoreCase("aquamail"))
			commonFunction.verifyIfElementIsPresent("NAME", emailAddress, "Email Address");	
		else if(variables.getEmailClient().equalsIgnoreCase("aol"))
			commonFunction.verifyIfElementIsPresent("NAME", emailAddress + " Link", "Email Address");	
			
		
		//Update your email address Link
		commonFunction.verifyIfElementIsPresent("NAME", "Update your email address Link", "Update your email address Link");
	}
	
	
	/*************************************************************
	 *verifyBalanceBanner - CRAFT Keyword
	 *This method verifies the content of Balance Banner section
	 *in EMBN email
	 *
	 *@author 387478
	 * @throws InterruptedException 
	 *@last_mofidied_on 12/4 by Shabbir
	 * 
	 ******************************************************/
	
	public void verifyBalanceBannerEMBN() throws InterruptedException{
		//Service Date
		String serviceDate1 = commonFunction.getData("Accounts", "ServiceDate1","Service Date 1", true);
		String serviceDate2= commonFunction.getData("Accounts", "ServiceDate2","Service Date 1", true);
		long daysOfService = commonFunction.findDateDifference(serviceDate2, serviceDate1);
		String serviceDateMessage = "Service dates: "+ serviceDate1 +" to "+ serviceDate2 +" "+ daysOfService +" days of service";
		//System.out.println(serviceDateMessage);
		commonFunction.verifyIfElementIsPresent("NAME", serviceDateMessage, "Service Dates");
		
		//Your security is our priority. message
		commonFunction.verifyIfElementIsPresent("NAME", "Your security is our priority.", "Your security is our priority. message");
		
		//Learn more Link
		commonFunction.verifyIfElementIsPresent("NAME", "Learn more Link", "Learn more Link");
		
		//Balance Line
		String accountBalanceMsg=""; // Store the message to be displayed for various Account Balances (zero,credit,positive)
		String dueDate = commonFunction.getData("Accounts", "DueDate","", false);
		String pastDueAmount = commonFunction.getData("Accounts", "PastDueAmount","PastDueAmount",false);		
		String accountBalance = commonFunction.getData("Accounts", "BalanceAccount", "Account Balance", true);
		dueDate = commonFunction.convertDatetoSingleDigit(dueDate);
		
		float actualAccountBalance=0;
		String accountBalFormatted = commonFunction.formatAmount(accountBalance);


		//Read account balance from common data
		try{
			actualAccountBalance = Float.parseFloat(commonFunction.RemoveSpecialcharactersFromAmount(accountBalance));
			accountBalFormatted = commonFunction.formatAmount(accountBalance);
		}catch(Exception e){
			report.updateTestLog("Account Balance", e.getMessage(), Status.WARNING);

		}

		//Zero Balance
		if(actualAccountBalance == 0)
			accountBalanceMsg = "Your account has a zero balance.";
		//Credit Balance
		else if(actualAccountBalance < 0)
			accountBalanceMsg = "Your account has a credit balance of $" + accountBalFormatted +".";

		//Past Due - Verify greeting line is displayed
		else if(actualAccountBalance != 0 && (!pastDueAmount.isEmpty())){
			//accountBalanceMsg = "Your total balance is $" + accountBalFormatted + ".";
			pastDueAmount = commonFunction.formatAmount(pastDueAmount);
			accountBalanceMsg = "Please pay $" + pastDueAmount + " immediately.Your total balance is $"+accountBalFormatted+".";
		}
		//No Previous Past Due
		else if(actualAccountBalance > 0 && (!dueDate.isEmpty()))
			accountBalanceMsg = "Your current balance is $" + accountBalFormatted +" due by" + dueDate + ".";

		//No previous past due date error
		else if(actualAccountBalance != 0)
			accountBalanceMsg = "Your current balance is $" + accountBalFormatted + ".";

		//Greeting Line
		String abp = commonFunction.getData("Accounts", "ABP","", false);
		String customerName = commonFunction.getData("Accounts", "CustomerName","Customer Name", true);
		String accountType =  commonFunction.getData("Accounts", "AccountType","Account Type", true);
		if(!accountType.toLowerCase().equals("residential"))
			customerName = customerName.toUpperCase();
		String pastDue =  commonFunction.getData("Accounts", "PastDueAmount","Past Due Amount", false);
		double pastDueAmount1 = commonFunction.convertStringtoDouble(pastDue);
		//ABP
		if(abp.toLowerCase().equals("yes")){			
			String greetingLine = "Hello " + customerName +",Thank you for using FPL Automatic Bill Pay® to process payment.";
			//System.out.println(greetingLine);
				commonFunction.verifyIfElementIsPresent("NAME", greetingLine, "Greetinge Line");
				
		}else if(pastDueAmount1 > 0){
			String greetingLine = customerName +", your account is past due.";
			System.out.println(greetingLine);
				commonFunction.verifyIfElementIsPresent("NAME", greetingLine, "Greetinge Line");
			
		}//Ready to pay bill
		else{
			String greetingLine = "Hello " + customerName +",Ready to pay your FPL bill? It’s a snap.";
			System.out.println(greetingLine);
				commonFunction.verifyIfElementIsPresent("NAME", greetingLine, "Greetinge Line");
		}
		
		//Verify if Balance Line is equal to accountBalanceMsg
		commonFunction.verifyIfElementIsPresent("NAME", accountBalanceMsg, "Balance Line");
		
		//Verify ABP Message
		if(abp.toLowerCase().equals("yes")){
			String abpDate = commonFunction.getData("Accounts", "ABPWithdrawalDate","ABP Withdrawal Date", true);
			String bankAccountNumber = commonFunction.getData("Accounts", "BankAccountNumber","Bank Account Number", true);
			abpDate = commonFunction.convertDatetoSingleDigit(abpDate);
			
			String abpMessage="";
			//System.out.println(abpMessage);
			if(variables.getEmailClient().equalsIgnoreCase("aquamail"))
				abpMessage = "The amount due on your account will be drafted automatically from your bank account ending in" + bankAccountNumber +" on or after "+ abpDate +". If a partial payment is received before this date, only the remaining balance on your account will be drafted automatically.";
			else if(variables.getEmailClient().equalsIgnoreCase("aol"))
				abpMessage = "The amount due on your account will be drafted automatically from your bank account ending in" + bankAccountNumber +" on or after "+ abpDate +". If a partial payment is received before this date, only the remaining balance on your account will be drafted automatically.";
			commonFunction.verifyIfElementIsPresent("NAME", abpMessage, "ABP Message");
		}
		
		
		//Download Bill Link
		commonFunction.verifyIfElementIsPresent("NAME", "Download Bill Link", "Download Bill Link");
	}
	
	
	
	/*********************************************************
	 *verifyPromotionalMessages - CRAFT Keyword
	 *This method verifies the content of Promotional Messages 
	 *displayed in EMBN email
	 *
	 *@author 387478
	 * @throws InterruptedException 
	 *@last_mofidied_on 12/4 by Shabbir
	 * 
	 ******************************************************/
	
	public void verifyPromotionalMessages() throws InterruptedException{		
		//Verify Promo Message 1 Header
		String promo1Header = commonFunction.getData("General_Data", "Res_Left_Header", "Promo 1 Header", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo1Header.trim(), "Promo Message 1 Header");
		
		//Verify Promo Message 1 contents
		String promo1Message = commonFunction.getData("General_Data", "Res_Left_Message", "Promo 1 Message Contents", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo1Message.trim(), "Promo 1 Message Contents");
		
		//Verify Promo Message 1 Link
		String promo1MessageLink = commonFunction.getData("General_Data", "Res_Left_Message_LinkText", "Promo 1 Message Link", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo1MessageLink.trim() +" Link", "Promo 1 Message Link");
		
		
		
		//Verify Promo Message 2 Header
		String promo2Header = commonFunction.getData("General_Data", "Res_Center_Header", "Promo 2 Header", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo2Header.trim(), "Promo Message 2 Header");
		
		//Verify Promo Message 2 contents
		String promo2Message = commonFunction.getData("General_Data", "Res_Center_Message", "Promo 2 Message Contents", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo2Message.trim(), "Promo 2 Message Contents");
		
		//Verify Promo Message 2 Link
		String promo2MessageLink = commonFunction.getData("General_Data", "Res_Center_Message_LinkText", "Promo 2 Message Link", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo2MessageLink.trim() +" Link", "Promo 2 Message Link");
		
		
		
		//Verify Promo Message 3 Header
		String promo3Header = commonFunction.getData("General_Data", "Res_Right_Header", "Promo 3 Header", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo3Header.trim(), "Promo Message 3 Header");
		
		//Verify Promo Message 3 contents
		String promo3Message = commonFunction.getData("General_Data", "Res_Right_Message", "Promo 3 Message Contents", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo3Message.trim(), "Promo 3 Message Contents");
		
		//Verify Promo Message 3 Link
		String promo3MessageLink = commonFunction.getData("General_Data", "Res_Right_Message_LinkText", "Promo 3 Message Link", true);
		commonFunction.verifyIfElementIsPresent("NAME", promo3MessageLink.trim() +" Link", "Promo 3 Message Link");
		
		
	}
	
	
	/*********************************************************
	 *verifyFooter - CRAFT Keyword
	 *This method verifies the content of Footer displayed 
	 *in EMBN email
	 *
	 *@author 387478
	 * @throws InterruptedException 
	 *@last_mofidied_on 12/4 by Shabbir
	 * 
	 ******************************************************/
	public void verifyFooter() throws InterruptedException{
		
		//Energy News Link
		commonFunction.verifyIfElementIsPresent("NAME", "Energy News Link", "Energy News Link");
		
		//Privacy Policy Link
		commonFunction.verifyIfElementIsPresent("NAME", "Privacy Policy Link", "Privacy Policy Link");
		
		//About Us Link
		commonFunction.verifyIfElementIsPresent("NAME", "About Us Link", "About Us Link");
		
		//CopyrightMessage
		String copyrightMessage = commonFunction.getData("General_Data", "CopyrightMessage", "Copyright Message", true);
		commonFunction.verifyIfElementIsPresent("NAME", copyrightMessage.trim(), "Copyright Message");
		
		//FooterMessage
		String footerMessage = commonFunction.getData("General_Data", "FooterMessage", "Do Not Reply Message", true);
		System.out.println(footerMessage);
		commonFunction.verifyIfElementIsPresent("NAME", footerMessage.trim(), "Do Not Reply Message");
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
