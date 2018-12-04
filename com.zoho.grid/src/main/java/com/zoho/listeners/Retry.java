package com.zoho.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{
	//private Logger log=LoggerHelper.getLogger(WaitHelper.class);
	private int retryCount=0;
	private int maxRetryCount=1;

	
	public boolean retry(ITestResult result) {
	
		if(retryCount<maxRetryCount)
		{
			//log.info("Retrying test " + result.getName()+ "with status " + getResultStatusName(result.getStatus()) + "for the " + (retryCount+1) + "times");
			System.out.println("Inside retry listner for " + retryCount + "try");
			retryCount++;
			return true;
		}
		return false;
	}
	
	/*public String getResultStatusName(int status)
	{
		String resultName=null;
		if(status==1)
			resultName="SUCCESS";
		if(status==2)
			resultName="FAILURE";
		if(status==1)
			resultName="SKIP";
		return resultName;
	}
*/
}
