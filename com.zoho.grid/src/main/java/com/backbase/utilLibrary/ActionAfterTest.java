package com.backbase.utilLibrary;

import com.zoho.baseSetUp.BaseSetUp_Grid;

public class ActionAfterTest extends BaseSetUp_Grid {

	BaseSetUp_Grid baseSetUp;
	
	public void testCaseReportUp() throws InterruptedException
	{
		try
		{	
			if(rep!=null)
			{
				rep.endTest(getExtTest());
				rep.flush();
			}
			getDriver().quit();
    	      
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	
	}
}
