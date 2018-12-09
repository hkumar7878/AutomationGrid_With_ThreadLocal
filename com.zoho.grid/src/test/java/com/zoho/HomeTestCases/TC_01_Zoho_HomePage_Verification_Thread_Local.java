package com.zoho.HomeTestCases;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.backbase.appPages.Zoho_Page_Home_ThreadLocal;
import com.backbase.utilLibrary.ActionAfterTest;
import com.backbase.utilLibrary.ActionBeforeTest;
import com.backbase.utilLibrary.DataProviderRep_HashMap;

import com.zoho.baseSetUp.BaseSetUp_Grid;
import com.zoho.utilities.TestUtil;

@Test
public class TC_01_Zoho_HomePage_Verification_Thread_Local extends BaseSetUp_Grid {
	String methodName=null;
	ActionAfterTest actionAfterTest=new ActionAfterTest();
	Zoho_Page_Home_ThreadLocal homePgObj_td;	
	WebDriver driver2;
	String bName=null;
	
	@BeforeMethod()
	@Parameters({"browserType"})
	public void getMethodName(Method method,String browserType)
	{
		bName=browserType;
		if(!TestUtil.isSuiteRunnable("LoginSuite", mstrSuite)){
			throw new SkipException("Skipping the suite as run mode is NO");
		}
	
		if(!TestUtil.isTestRunnable(method.getName(), excel)){
				throw new SkipException("Skipping the test " + method.getName().toUpperCase()+ "as run mode is NO");
		}
		 System.out.println("Starting " + method.getName() + "test case" );		
		 test=rep.startTest("Verify App Home Page" + "_" + bName);
		 setExtentTest(test);
		 initializeTestBaseSetup3(bName);
	}
	
	@Test(dataProviderClass=DataProviderRep_HashMap.class,dataProvider="newComputerData10",priority=1)
	@Parameters({"browserType"})
	public void TC_01_Verify_App_Home_Page_Thread_Local(Map <String,String> data) throws ParseException, InterruptedException, InvocationTargetException
	{	
		logInfo("Starting the test for ");
		homePgObj_td= new Zoho_Page_Home_ThreadLocal(getDriver());
		homePgObj_td.verifyHomePage(bName);
	}

	@AfterMethod
	   public void afterEachTest(ITestResult result) throws InterruptedException {
	        try
	        {
	        	System.out.println("Inside After Method of test case");
	            actionAfterTest.testCaseReportUp();
	        }

	        catch (Exception e)
	        {
	            System.out.println("Excpetion is " + e.getMessage());
	        }
		}

}
