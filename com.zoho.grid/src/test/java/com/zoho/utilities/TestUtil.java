package com.zoho.utilities;


import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.backbase.utilLibrary.ExcelReader;
import com.relevantcodes.extentreports.LogStatus;
import com.zoho.baseSetUp.BaseSetUp_Grid;
import com.backbase.utilLibrary.Constants;

public class TestUtil extends BaseSetUp_Grid{
	
	public static boolean isSuiteRunnable(String suiteName, ExcelReader mstrSuite)
	{		
		int rowCount=mstrSuite.getRowCount(Constants.SUITE_SHEET);
		for(int rNum=2;rNum<=rowCount;rNum++)
		{
			String suiteColName=mstrSuite.getCellData(Constants.SUITE_SHEET, Constants.SUITE_COL, rNum);
			if(suiteColName.equals(suiteName))
			{
				String runMode=mstrSuite.getCellData(Constants.SUITE_SHEET, Constants.SUITE_RUNMODE, rNum);
				if(runMode.equals(Constants.RUNMODE_Y))
					return true;
				else 
					return false;
			}
		}
		return false;
	}
	
	public static boolean isTestRunnable(String testName, ExcelReader excel)
	{
		boolean flag=false;
		int rows=excel.getRowCount(Constants.TESTCASES_SHEET);
		for(int rNum=2;rNum<=rows;rNum++)
		{
			String testCase=excel.getCellData(Constants.TESTCASES_SHEET, "TCID", rNum);
			if(testCase.equalsIgnoreCase(testName))
			{
				String runMode=excel.getCellData(Constants.TESTCASES_SHEET, "Runmode", rNum);
					{
						if(runMode.equalsIgnoreCase("Y"))
						//return true;
							flag=true;
						else
							flag=false;					
				}
			}		
		}
		
		return flag;
	}	
}
