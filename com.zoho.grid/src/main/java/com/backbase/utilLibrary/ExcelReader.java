package com.backbase.utilLibrary;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.Calendar;

public class ExcelReader {
    public  String path;
    public  FileInputStream fis = null;
    public  FileOutputStream fileOut =null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row   =null;
    private XSSFCell cell = null;


    public ExcelReader(String path)
    {
        this.path=path;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            //sheet = workbook.getSheetAt(0);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Another latest alternative method to fetch data from Excel
    
    public String [][] getDatafromExcel(String sheetName, String ExcelName)
    {
		
    	String dataSet[][]=null;
    	try
    	{
    		// get data from excel workbook
    		XSSFSheet sheet=workbook.getSheet(sheetName);
    		// count of active rows in sheet
    		int totalRow=sheet.getLastRowNum()+1;
    		// count number of active cell
    		
    		int totalColumn=sheet.getRow(0).getLastCellNum();
    		// Create array of row and column
    		dataSet = new String [totalRow-1][totalColumn];
    		
    		// Run for loop and store data in 2D array
    		
    		// This first loop will run on rows
    		
    		for(int i=1;i<totalRow;i++)
    		{
    			XSSFRow rows=sheet.getRow(i);
    			
    			// This loop will run the column of that row
    			for(int j=0;j<totalColumn;j++)
    			{
    					// get cellMethod will get cell data
    				
    				XSSFCell cell= rows.getCell(j);
    				// if Cell type is of String type then this If condition will work
    				
    				if(cell.getCellType()==Cell.CELL_TYPE_STRING)
    					dataSet[i-1][j]=cell.getStringCellValue();
    				else if (cell.getCellType()==Cell.CELL_TYPE_BLANK)
    					dataSet[i-1][j]=null;
    				else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
    				{
    					String cellText=String.valueOf(cell.getNumericCellValue());
    					dataSet[i-1][j]=cellText;
    				}
    				else
    					// if Cell type is of boolean type then this  condition will work
    					dataSet[i-1][j]=String.valueOf(cell.getBooleanCellValue());
    			}
    		}
    		return dataSet;
    	}
    	
    	catch(Exception e)
    	{
    		System.out.println("Exception in reading excel file " + e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return null;
    	
    }
    
    public String getCellData(String sheetName,String colName,int rowNum){
        try{
            if(rowNum <=0)
                return "";
            int index = workbook.getSheetIndex(sheetName);
            int col_Num=-1;
            if(index==-1)
                return "";
            sheet = workbook.getSheetAt(index);
            row=sheet.getRow(0);
            for(int i=0;i<row.getLastCellNum();i++){
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num=i;
            }
            if(col_Num==-1)
                return "";
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum-1);
            if(row==null)
                return "";
            cell = row.getCell(col_Num);
            if(cell==null)
                return "";
            if(cell.getCellType()==Cell.CELL_TYPE_STRING)
                return cell.getStringCellValue();
            else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
                String cellText  = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    double d = cell.getNumericCellValue();
                    Calendar cal =Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText =(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +cal.get(Calendar.MONTH)+1 + "/" + cellText;
                }
                return cellText;
            }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        }

        catch(Exception e){
            e.printStackTrace();
            return "row "+rowNum+" or column "+colName +" does not exist in xls";
        }
    }
    public boolean writeResult(String wsName, String colName, int rowNumber, String Result){
        try{
            int sheetIndex=workbook.getSheetIndex(wsName);
            if(sheetIndex==-1)
                return false;
            int colNum = getColumnCount(wsName);
            //int colNumber=-1;
            int colNumber=1;


            XSSFRow Suiterow = sheet.getRow(0);
            for(int i=0; i<colNum; i++){
                if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
                    colNumber=i;
                }
            }

            if(colNumber==-1){
                return false;
            }

            XSSFRow Row = sheet.getRow(rowNumber);
            XSSFCell cell = Row.getCell(colNumber);
            if (cell == null)
                cell = Row.createCell(colNumber);

            cell.setCellValue(Result);

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();


        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    // returns the row count in a sheet



    public int getRowCount(String sheetName){

        int index = workbook.getSheetIndex(sheetName);
        if(index==-1)
            return 0;
        else
        {
            sheet = workbook.getSheetAt(index);
            int number=sheet.getLastRowNum()+1;
            return number;
        }
    }

    public int getColumnCount(String sheetName){
        // check if sheet exists
        if(!isSheetExist(sheetName))
            return -1;
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);
        if(row==null)
            return -1;
        return row.getLastCellNum();

    }
    // returns the data from a cell
    //To retrieve SuiteToRun and CaseToRun flag of test suite and test case.
    public String retrieveToRunFlag(String wsName, String colName, String rowName){

        int sheetIndex=workbook.getSheetIndex(wsName);
        if(sheetIndex==-1)
            return null;
        else{
            int rowNum = getRowCount(wsName);
            int colNum = getColumnCount(wsName);
            int colNumber=-1;
            int rowNumber=-1;

            XSSFRow Suiterow = sheet.getRow(0);

            for(int i=0; i<colNum; i++){
                if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
                    colNumber=i;
                }
            }

            if(colNumber==-1){
                return "";
            }


            for(int j=0; j<rowNum; j++){
                XSSFRow Suitecol = sheet.getRow(j);
                if(Suitecol.getCell(0).getStringCellValue().equals(rowName.trim())){
                    rowNumber=j;
                }
            }

            if(rowNumber==-1){
                return "";
            }

            XSSFRow row = sheet.getRow(rowNumber);
            XSSFCell cell = row.getCell(colNumber);
            if(cell==null){
                return "";
            }
            String value = cellToString(cell);
            return value;
        }
    }
    
    public String retrieveTestCaseName(String wsName, String colName, String rowName){

        int sheetIndex=workbook.getSheetIndex(wsName);
        if(sheetIndex==-1)
            return null;
        else{
            int rowNum = getRowCount(wsName);
            int colNum = getColumnCount(wsName);
            int colNumber=-1;
            int rowNumber=-1;

            XSSFRow Suiterow = sheet.getRow(0);

            for(int i=0; i<colNum; i++){
                if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
                    colNumber=i;
                }
            }

            if(colNumber==-1){
                return "";
            }


            for(int j=0; j<rowNum; j++){
                XSSFRow Suitecol = sheet.getRow(j);
                if(Suitecol.getCell(0).getStringCellValue().equals(rowName.trim())){
                    rowNumber=j;
                }
            }

            if(rowNumber==-1){
                return "";
            }

            XSSFRow row = sheet.getRow(rowNumber);
            XSSFCell cell = row.getCell(colNumber);
            if(cell==null){
                return "";
            }
            String value = cellToString(cell);
            return value;
        }
    }




    public boolean isRowEmpty(Row row){
        if (row == null){
            return true;
        }
        else {
            return false;
        }
    }

    //To write result In test suite list sheet.
    public boolean writeResult(String wsName, String colName, String rowName, String Result){
        try{
            int rowNum = getRowCount(wsName);
            int rowNumber=-1;
            int sheetIndex=workbook.getSheetIndex(wsName);
            if(sheetIndex==-1)
                return false;
            int colNum = getColumnCount(wsName);
            int colNumber=-1;


            XSSFRow Suiterow = sheet.getRow(0);
            for(int i=0; i<colNum; i++){
                if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
                    colNumber=i;
                }
            }

            if(colNumber==-1){
                return false;
            }

            for (int i=0; i<rowNum-1; i++){
                XSSFRow row = sheet.getRow(i+1);
                XSSFCell cell = row.getCell(0);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String value = cellToString(cell);
                if(value.equals(rowName)){
                    rowNumber=i+1;
                    break;
                }
            }

            XSSFRow Row = sheet.getRow(rowNumber);
            XSSFCell cell = Row.getCell(colNumber);
            if (cell == null)
                cell = Row.createCell(colNumber);
            if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
                cell = Row.createCell(colNumber);


            cell = Row.createCell(colNumber);
            cell.setCellValue(Result);

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();


        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static String cellToString(XSSFCell cell){
        int type;
        Object result;
        type = cell.getCellType();
        switch (type){
            case 0 :
                result = cell.getNumericCellValue();
                break;

            case 1 :
                result = cell.getStringCellValue();
                break;

            default :
                throw new RuntimeException("Unsupportd cell.");
        }
        return result.toString();
    }



    //To retrieve test data from test case data sheets.
    public Object[][] retrieveTestData(String wsName){
        int sheetIndex=workbook.getSheetIndex(wsName);
        if(sheetIndex==-1)
            return null;
        else{
            int rowNum = getRowCount(wsName);
            int colNum = getColumnCount(wsName);

            Object data[][] = new Object[rowNum-1][colNum-2];

            for (int i=0; i<rowNum-1; i++){
                XSSFRow row = sheet.getRow(i+1);
                for(int j=0; j< colNum-2; j++){
                    if(row==null){
                        data[i][j] = "";
                    }
                    else{
                        XSSFCell cell = row.getCell(j);

                        if(cell==null){
                            data[i][j] = "";
                        }
                        else{
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            String value = cellToString(cell);
                            data[i][j] = value;
                        }
                    }
                }
            }
            return data;
        }

    }




    // returns the data from a cell

    public String getCellData(String sheetName,int colNum,int rowNum){

        try{
            if(rowNum <=0)
                return "";
            int index = workbook.getSheetIndex(sheetName);
            if(index==-1)
                return "";
            sheet = workbook.getSheetAt(index);
            //row = sheet.getRow(rowNum-1);
            //XSSFRow row = sheet.getRow(rowNum-1);
            XSSFRow row = sheet.getRow(rowNum);
            if(row==null)
            {

                System.out.println("Row is null");
                return "";
            }
            cell = row.getCell(colNum);
            if(cell==null)
                return "";
            if(cell.getCellType()==Cell.CELL_TYPE_STRING)
                return cell.getStringCellValue();
            else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
                String cellText  = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();
                    Calendar cal =Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText =
                            (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.MONTH)+1 + "/" +
                            cal.get(Calendar.DAY_OF_MONTH) + "/" +
                            cellText;

                }
                return cellText;
            }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        }
        catch(Exception e){
            e.printStackTrace();
            return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
        }
    }
    
    public String getCellData1(String sheetName,int colNum,int rowNum){

        try{
            /*if(rowNum <=0)
                return "";*/
            int index = workbook.getSheetIndex(sheetName);
            if(index==-1)
                return "";
            sheet = workbook.getSheetAt(index);
            //row = sheet.getRow(rowNum-1);
            //XSSFRow row = sheet.getRow(rowNum-1);
            XSSFRow row = sheet.getRow(rowNum);
            if(row==null)
            {

                System.out.println("Row is null");
                return "";
            }
            cell = row.getCell(colNum);
            if(cell==null)
                return "";
            if(cell.getCellType()==Cell.CELL_TYPE_STRING)
                return cell.getStringCellValue();
            else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
                String cellText  = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();
                    Calendar cal =Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText =
                            (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.MONTH)+1 + "/" +
                            cal.get(Calendar.DAY_OF_MONTH) + "/" +
                            cellText;

                }
                return cellText;
            }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        }
        catch(Exception e){
            e.printStackTrace();
            return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
        }
    }
    // returns true if data is set successfully else false

    public boolean setCellData(String sheetName,String colName,int rowNum, String data){
        try{
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            if(rowNum<=0)
                return false;
            int index = workbook.getSheetIndex(sheetName);
            int colNum=-1;
            if(index==-1)
                return false;
            sheet = workbook.getSheetAt(index);
            row=sheet.getRow(0);
            for(int i=0;i<row.getLastCellNum();i++){
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if(row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum=i;
            }
            if(colNum==-1)
                return false;
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum-1);
            if (row == null)
                row = sheet.createRow(rowNum-1);
            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);
            cell.setCellValue(data);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }







    // returns true if data is set successfully else false

    public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){

        try{
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            if(rowNum<=0)
                return false;
            int index = workbook.getSheetIndex(sheetName);
            int colNum=-1;
            if(index==-1)
                return false;
            sheet = workbook.getSheetAt(index);
            row=sheet.getRow(0);
            for(int i=0;i<row.getLastCellNum();i++){
                if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
                    colNum=i;
            }
            if(colNum==-1)
                return false;
            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum-1);
            if (row == null)
                row = sheet.createRow(rowNum-1);
            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);
            cell.setCellValue(data);
            XSSFCreationHelper createHelper = workbook.getCreationHelper();
            //cell style for hyperlinks
            CellStyle hlink_style = workbook.createCellStyle();
            XSSFFont hlink_font = workbook.createFont();
            hlink_font.setUnderline(XSSFFont.U_SINGLE);
            hlink_font.setColor(IndexedColors.BLUE.getIndex());
            hlink_style.setFont(hlink_font);
            //hlink_style.setWrapText(true);
            XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
            link.setAddress(url);
            cell.setHyperlink(link);
            cell.setCellStyle(hlink_style);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }







    // returns true if sheet is created successfully else false

	/*public boolean addSheet(String  sheetname){
		FileOutputStream fileOut;
		try {
			 workbook.createSheet(sheetname);
			 fileOut = new FileOutputStream(path);
			 workbook.write(fileOut);
		     fileOut.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}*/



    // returns true if sheet is removed successfully else false if sheet does not exist

	/*public boolean removeSheet(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return false;
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();

		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
*/
    // returns true if column is created successfully

	/*public boolean addColumn(String sheetName,String colName){

		try{
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		sheet=workbook.getSheetAt(index);
		row = sheet.getRow(0);
		if (row == null)
			row = sheet.createRow(0);
		if(row.getLastCellNum() == -1)
			cell = row.createCell(0);
		else
			cell = row.createCell(row.getLastCellNum());
	        cell.setCellValue(colName);
	        cell.setCellStyle(style);
	        fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
*/






    // removes a column and all the contents

	/*public boolean removeColumn(String sheetName, int colNum)
	{

		try{

		if(!isSheetExist(sheetName))
			return false;
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetName);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		XSSFCreationHelper createHelper = workbook.getCreationHelper();
		style.setFillPattern(HSSFCellStyle.NO_FILL);
		for(int i =0;i<getRowCount(sheetName);i++){
			row=sheet.getRow(i);
			if(row!=null){
				cell=row.getCell(colNum);
				if(cell!=null){
					cell.setCellStyle(style);
					row.removeCell(cell);
				}
			}
		}

		fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
	    fileOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
*/




    // find whether sheets exists

    public boolean isSheetExist(String sheetName){

        int index = workbook.getSheetIndex(sheetName);

        if(index==-1){

            index=workbook.getSheetIndex(sheetName.toUpperCase());

            if(index==-1)

                return false;

            else

                return true;

        }

        else

            return true;

    }





    // returns number of columns in a sheet







    //String sheetName, String testCaseName,String keyword ,String URL,String message

    public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){





        url=url.replace('\\', '/');

        if(!isSheetExist(sheetName))

            return false;



        sheet = workbook.getSheet(sheetName);



        for(int i=2;i<=getRowCount(sheetName);i++){

            if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){



                setCellData(sheetName, screenShotColName, i+index, message,url);

                break;

            }

        }





        return true;

    }

    public int getCellRowNum(String sheetName,String colName,String cellValue){

        int getRowCount=getRowCount(sheetName);

        for(int i=1;i<=getRowCount;i++){
            String cellData=getCellData(sheetName,colName , i);
            if(cellData.equals(cellValue))
            {
                return i-1;
            }
        }
        return -1;
    }

    // to run this on stand alone

	/*public static void main(String arg[]) throws IOException{





		ExcelReader datatable = null;





			 datatable = new ExcelReader("C:\\CM3.0\\app\\test\\Framework\\AutomationBvt\\src\\config\\xlfiles\\Controller.xlsx");

				for(int col=0 ;col< datatable.getColumnCount("TC5"); col++){

					System.out.println(datatable.getCellData("TC5", col, 1));

				}*/

}

