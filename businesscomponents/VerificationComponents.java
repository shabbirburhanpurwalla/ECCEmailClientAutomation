package businesscomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import supportlibraries.DriverScript;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.UIFields;

/**
 * Verification Components class
 * 
 * @author CTS
 */
public class VerificationComponents extends ReusableLibrary {
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} {@link DriverScript}
	 */
	UIFields uifields = new UIFields();
	
	public VerificationComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	private boolean isElementPresent(By by) 
	{
		try 
		{
			driver.findElement(by);
			return true;
		} 
		catch (NoSuchElementException e) 
		{
				return false;
		}
	}
	
	// Function to Verify that User has logged in Successfully into Application
	public void verifyLoginValidUser()
	{
		if(isElementPresent(By.linkText(uifields.lnk_logoutLink))) {
			report.updateTestLog("Verify Login", "Login succeeded for valid user", Status.PASS);
		} else {
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Verify Login", "Login failed for valid user");
		}
	}
	
	// Function to Verify that User has logged in Successfully into Web Mail
	public void verifyLoginIntoWebMail()
	{
		if(isElementPresent(By.linkText(uifields.lnk_Web_Logout))) {
			report.updateTestLog("Verify Login", "Login succeeded for valid user", Status.PASS);
		} else {
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Verify Login", "Login failed for valid user");
		}
	}



}