package pageobjects;

import static pageobjects.ObjectLocator.*;

/**
 * Class to store all the objects of Email
 * 
 * @author 387478
 * @lastmodified 15 October
 *
 */
public enum PageObjects {
	
	lbl_LastUpdated("com.android.email:id/last_sync_time",ID,"Email Client");
	
	
	String strProperty = "";
	ObjectLocator locatorType = null;
	String strObjName = "";
	
	public String getProperty(){
		return strProperty;
	}

	public ObjectLocator getLocatorType(){
		return locatorType;
	}
	public String getObjectName(){
		return strObjName;
	}

	private PageObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}
		
}
