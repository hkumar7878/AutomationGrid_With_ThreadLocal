package com.zoho.listeners;




import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IInvokedMethodListener2;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;

import org.testng.ITestResult;

import com.zoho.baseSetUp.BaseSetUp_Grid;





public class CustomListeners extends BaseSetUp_Grid implements IInvokedMethodListener2,ITestListener, ISuiteListener, IInvokedMethodListener  {


	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {	
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		
	    try
	        {
	        	System.out.println("Inside After Method of test case");
	            //actionAfterTest.testCasesSheetUpd3(testResult);
	        }

	        catch (Exception e)
	        {
	            System.out.println("Excpetion is " + e.getMessage());
	        }
		}
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult,ITestContext context) {
		/*String m =testResult.getMethod().getMethodName();
		String m1=context.getName();
		System.out.println(m);*/
		
		
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult,ITestContext context) {
		
		
	}


	@Override
	public void onStart(ISuite suite) {
		System.out.println("At suite start");
		
	}

	@Override
	public void onFinish(ISuite suite){
		
	/*	try
		{
			MonitoringMail mail = new MonitoringMail();
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			String messageBody="http://" + InetAddress.getLocalHost().getHostAddress()
				+":8080/job/SeleniumDockerProject1/Extent_20Report/";
			System.out.println(messageBody);
		
			mail.sendMail(TestConfig.server, TestConfig.from,TestConfig.to, TestConfig.subject, messageBody);
			
			System.out.println("On finish of test suite");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		*/
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		
		//System.out.println("Inside onTestStart");
		/*test = rep.startTest(arg0.getName().toUpperCase());
		if(!TestUtil.isTestRunnable(arg0.getName(), excel)){
			throw new SkipException("Skipping the test " + arg0.getName().toUpperCase()+ "as run mode is NO");
		}
		*/
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		//test.log(LogStatus.PASS, arg0.getName().toUpperCase()+" PASS");
		//rep.endTest(test);
		//rep.flush();
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
	/*	System.setProperty("org.uncommons.reportng.escape-output","false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, arg0.getName().toUpperCase()+" Failed with exception : "+arg0.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		
		
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
		rep.endTest(test);
		rep.flush();*/
		
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		//test.log(LogStatus.SKIP, arg0.getName().toUpperCase()+" Skipped the test as the Run mode is NO");
		//rep.endTest(test);
		//rep.flush();
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	

}
