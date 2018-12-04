package com.backbase.utilLibrary;


import java.util.Hashtable;
import org.testng.annotations.DataProvider;

public class DataProviderRep_HashMap {
	public static ExcelReader excelReader = null;
	
	@DataProvider(name="newComputerData10")
	public static Object[][] getNewCompData10() {
    	String path= System.getProperty("user.dir")+"\\Test Excel Files\\" + "NewComputerTestData.xlsx";
    	excelReader=new ExcelReader(path);
		String sheetName="NewComputerAddition";
		int rows = excelReader.getRowCount(sheetName);
		int cols = excelReader.getColumnCount(sheetName);
		Object[][] data = new Object[rows - 1][1];
		Hashtable<String,String> table = null;
		for (int rowNum = 2; rowNum <= rows; rowNum++) 
		{ 
			table = new Hashtable<String,String>();			
			for (int colNum = 0; colNum < cols; colNum++) {
				String key=excelReader.getCellData1(sheetName, colNum, 0);
				String value=excelReader.getCellData1(sheetName, colNum, rowNum-1);
				table.put(key, value);
				data[rowNum - 2][0] = table;
			}
		}
		return data;
	}
}
