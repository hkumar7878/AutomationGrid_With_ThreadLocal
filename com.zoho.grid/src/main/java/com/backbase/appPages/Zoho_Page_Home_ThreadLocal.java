package com.backbase.appPages;



import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.backbase.errorCollectors.ErrorCollector;
import com.backbase.utilLibrary.GenericUtilLib;
import com.zoho.baseSetUp.BaseSetUp_Grid;


public class Zoho_Page_Home_ThreadLocal extends BaseSetUp_Grid{
	
	boolean isEventSuccessful;
	String totalCompFoundData;
	String successMsg;
	ArrayList<String> rowsData=new ArrayList<String>();
	GenericUtilLib genericUtil_Obj= new GenericUtilLib();
	//RemoteWebDriver driver;
	
	public Zoho_Page_Home_ThreadLocal(WebDriver driver)
    {
    	this.driver = driver;
    	PageFactory.initElements(driver,this);
    }
	@FindBy(xpath = "//a[@class='zh-login']")
	public WebElement lnk_login;
	
	@FindBy(xpath = "//a[contains(text(),'Play sample application')]")
	public static WebElement lnk_sampleApp;
	
	//@FindBy(xpath = "//a[contains(text(),'XYZ')]")
	//public static WebElement lnk_sampleApp;


	
	public void verifyHomePage(String bName1)
    {
        try
        {          
        	isEventSuccessful=genericUtil_Obj.verifyLinkDisplay(lnk_sampleApp, "Play");
        	Assert.assertTrue(isEventSuccessful);        	
            System.out.println("Home Page is displayed successfully");
            reportPass("Home Page is displayed successfully");
            addLog("Home Page is displayed successfully" + bName1,bName1);
           passInfo("Home Page is displayed successfully" + bName1);
                    
        }
        catch(AssertionError | Exception e)
        {
        		ErrorCollector.addVerificationFailure(e);
        		//logger.info("--------Home Page is not displayed-----------------");
        		//logger.info(e.getMessage());
        		 addLog("Home Page is NOT displayed successfully" + bName1,bName1);
        		System.out.println("Home Page is NOT displayed successfully");
            	reportFailure("Home Page is not displayed" + bName1);
            	failInfo("Home Page is NOT displayed successfully" + bName1);
            	Assert.fail();
        }
    }
				
}
	
	


