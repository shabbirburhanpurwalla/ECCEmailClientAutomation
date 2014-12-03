package businesscomponents;

import org.openqa.selenium.interactions.Actions;

import componentgroups.CommonFunctions;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class EMBNEmail extends ReusableLibrary{

	public EMBNEmail(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	Actions action = new Actions(driver);
	
	
}
