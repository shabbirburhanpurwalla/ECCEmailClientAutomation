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
	
	lbl_DefaultLastUpdated("com.android.email:id/last_sync_time",ID,"Email Client"),
	lbl_AOL_InboxText("com.aol.mobile.aolapp:id/action_bar_spinner_item_text",ID,"Inbox Text"),
	lbl_Yahoo_InboxText("com.yahoo.mobile.client.android.mail:id/titleText",ID,"Inbox Text"),
	lbl_Gmail_PrimaryTitle("android:id/action_bar_title",ID,"Primary Text"),
	lbl_AquaMail_InboxTitle("org.kman.AquaMail:id/account_folder_name_folder",ID,"Inbox Text"),
	lbl_AquaMail_EmailSubject("org.kman.AquaMail:id/message_subject_short",ID,"Email Subject"),
	lbl_AOL_EmailSubject("com.aol.mobile.aolapp:id/message_subject",ID,"Email Subject");
	//
	
	
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
