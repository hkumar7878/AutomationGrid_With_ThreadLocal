package com.backbase.utilLibrary;

public class SuiteUtility {
	
	public static boolean checkToRunUtility(ExcelReader xls, String sheetName, String ToRun, String testSuite){

        boolean flag = false;
        if(xls.retrieveToRunFlag(sheetName,ToRun,testSuite).equalsIgnoreCase("y"))  
        	flag = true; 
        //if (xls.retrieveToRunFlag(sheetName,ToRun,testSuite).equalsIgnoreCase("N") || (xls.retrieveToRunFlag(sheetName,ToRun,testSuite).equalsIgnoreCase(" ")))
        
        else  if (xls.retrieveToRunFlag(sheetName,ToRun,testSuite).equalsIgnoreCase("N"))           
        	flag = false;
        return flag;
    }
	
	public static String reteriveTestCaseID(ExcelReader xls, String sheetName, String testCaseID, String testSuite){

        String testCaseName=null; 
        testCaseName=xls.retrieveTestCaseName(sheetName, testCaseID, testSuite);
        return testCaseID;
    
    }
	
	public static boolean WriteResultUtility1(ExcelReader xls, String sheetName, String ColName, int rowNum, String Result){

        return xls.writeResult(sheetName, ColName, rowNum, Result);
    }
	
	 public static int readRowUtility(ExcelReader xls, String sheetName, String ColName,String tcName){

	        int i=0;
	        i=xls.getCellRowNum(sheetName, ColName, tcName);
	        return i;
	    }
	 public static boolean WriteResultUtility(ExcelReader xls, String sheetName, String ColName, int rowNum, String Result){
	        boolean flag=false;
	        
	            flag=xls.writeResult(sheetName, ColName, rowNum, Result);
	        

	        return flag;
	    }

}
