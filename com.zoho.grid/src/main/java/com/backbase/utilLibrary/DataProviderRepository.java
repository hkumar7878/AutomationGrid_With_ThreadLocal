package com.backbase.utilLibrary;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.zoho.baseSetUp.BaseSetUp_Grid;


public class DataProviderRepository extends BaseSetUp_Grid{
	

	
    static String SheetName = null;
    static String testDataFilePath=System.getProperty("user.dir" + "\\Test Excel Files\\NewComputerTestData.xlsx");
   //public static ExcelReader excel1=new ExcelReader(testDataFilePath);
    //public static ExcelReader excelReader = null;
    public static ExcelReader excelReader = null;
    
    @DataProvider(name="newComputerData11")
	public static Object[][] getNewCompData11() {
    	String path= System.getProperty("user.dir")+"\\Test Excel Files\\" + "NewComputerAddition";
    	excelReader=new ExcelReader(path);
		String sheetName="NewComputerTestData.xlsx";
		int rows = excelReader.getRowCount(sheetName);
		int cols = excelReader.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String,String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

			table = new Hashtable<String,String>();
			
			for (int colNum = 0; colNum < cols; colNum++) {

				table.put(excelReader.getCellData(sheetName, colNum, 1), excelReader.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}

		}

		return data;

	}
    
    @DataProvider(name="newComputerData")
    public static Object[][] getNewCompData(){
        System.out.println("Inside new computer data provider");
		String[][] data = getExcelData1("NewComputerTestData.xlsx", "NewComputerAddition");
    	return data;
    }
    
    
    
    
    @DataProvider(name="newComputerData5")
	public Object[][] getNewCompData5() {
    	ExcelReader excel1=new ExcelReader(testDataFilePath);
		String sheetName="NewComputerTestData.xlsx";
		int rows = excel1.getRowCount(sheetName);
		int cols = excel1.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String,String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

			table = new Hashtable<String,String>();
			
			for (int colNum = 0; colNum < cols; colNum++) {

				table.put(excel1.getCellData(sheetName, colNum, 1), excel1.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}

		}

		return data;

	}
    
 
    
   /* @DataProvider(name="newComputerData2")
	public Object[][] getNewCompData1() {

		String sheetName="NewComputerTestData.xlsx";
		int rows = excel1.getRowCount(sheetName);
		int cols = excel1.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		//Hashtable<String,String> table = null;

		for (int i = 0; i <= rows; i++) 
		{ 
			Map<Object, Object> datamap=new HashMap<Object,Object>();
			
			for (int j= 0; j < cols; j++) 
			{

				datamap.put(excel1.getCellData(sheetName, j, 1), excel1.getCellData(sheetName, j, i));
			
			}
			
			data[i][0]=datamap;

		}

		return data;

	}*/
    @DataProvider(name="existingCompData")
    public static Object[][] getExistingComputerData(){
        System.out.println("Inside data provider for exiting computer dataprovider");
		String[][] data = getExcelData1("SearchComputerTestData.xlsx", "ExistingComputers");
    	return data;
    }
    

    @DataProvider(name="nonExistingCompData")
    public static Object[][] getNonExistingComputerData(){
        System.out.println("Inside data provider for exiting computer dataprovider");
		String[][] data = getExcelData1("SearchComputerTestData.xlsx", "NonExistingComputers");
    	return data;
    }
 
    @DataProvider(name="deleteTestData")
    public static Object[][] getdeleteTestData(){
        System.out.println("Inside data provider for delete computer test data dataprovider");
		String[][] data = getExcelData1("DeleteTestData.xlsx", "DeleteData");
    	return data;
    }
 
    
    /*public Object [][] getExcelData(String fileName, String sheetName) {
        int rows=excel1.getRowCount(sheetName);
        int cols=excel1.getColumnCount(sheetName);
        Object data [][]=new Object[rows-1][cols];
        for (int rowNum=1;rowNum<rows;rowNum++)
        {

            for (int colNum=0;colNum<cols;colNum++)
            {
                data [rowNum-1][colNum]=excel1.getCellData(sheetName, colNum, rowNum);
            }
        }
        return data;
    }*/

    

}
