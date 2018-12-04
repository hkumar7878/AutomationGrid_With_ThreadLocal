package com.backbase.utilLibrary;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	static ExtentReports extent;
	final static String filePath = "./Report_AddNewComputer/TestResults_Report.html";
  
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath, true);
            
        }
        
        return extent;
    }

}
