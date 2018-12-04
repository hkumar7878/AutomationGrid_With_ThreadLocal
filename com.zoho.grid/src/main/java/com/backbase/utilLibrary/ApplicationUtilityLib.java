package com.backbase.utilLibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.zoho.baseSetUp.BaseSetUp_Grid;

public class ApplicationUtilityLib extends BaseSetUp_Grid{
	
	
	public static int getTotalComputerValue(String s) {
		String[] array = s.split("\\s+");
		return Integer.parseInt(array[0]);

	}
	
	
	public static ArrayList<String> fetchGridData(WebElement table, String newcompName) throws Exception {

		ArrayList<String> rowsData = new ArrayList<String>();
		try {
			List<WebElement> rows_table = table.findElements(By.tagName("tr"));
			int rows_count = rows_table.size();

			for (int row = 1; row < rows_count; row++) {
				List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
				int columns_count = Columns_row.size();
				if (Columns_row.get(0).getText().equalsIgnoreCase(newcompName)) {
					for (int j = 0; j < columns_count; j++) {
						rowsData.add(Columns_row.get(j).getText());
					}
					break;
				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rowsData;

	}
	
public static String converstionDates(String inputPatteren,String outputPattern,String givenDate) throws ParseException

	{
		SimpleDateFormat inputFormat= new SimpleDateFormat(inputPatteren);
		SimpleDateFormat outputFormat= new SimpleDateFormat(outputPattern);
		Date date=null;
		String requireDate=null;
		date=inputFormat.parse(givenDate);
		requireDate=outputFormat.format(date);
		return requireDate;			
	}


 

public static String getElementBGColor(WebElement we)
{
	String buttonColor = we.getCssValue("background-color");
    System.out.println("Button color: " + buttonColor);
    String[] hexValue = buttonColor.replace("rgb(", "").replace(")", "").split(",");
    
    int hexValue1=Integer.parseInt(hexValue[0]);
    hexValue[1] = hexValue[1].trim();
    int hexValue2=Integer.parseInt(hexValue[1]);
    hexValue[2] = hexValue[2].trim();
    int hexValue3=Integer.parseInt(hexValue[2]);
     
    String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
    return actualColor;
}

public static List<String> fetchInputTextFieldsData(List<WebElement> input_TxtFields)
{
	List<String> inputFieldsData= new ArrayList<String>();
	
	try
	{
		for(WebElement we1:input_TxtFields)
		{
			inputFieldsData.add(we1.getAttribute("value"));
			System.out.println("");
		}
	}
	
	catch(Exception e)
	{
		System.out.println(e.getMessage());
	}
	return inputFieldsData;
	

}

public static boolean campareList(List<String> actual,List<String> exp)
{
    boolean flag=false;

    try
    {
        if (actual.size() != exp.size())
             return false;
     
        for(int i=0;i<actual.size();i++)
        {
            String actualItem=(String)actual.get(i);
            for (int j=0;j<exp.size();j++)
            {
                String expItem=(String)exp.get(j);
                int result = actualItem.compareTo(expItem);
                if(result==0)
                {
                    System.out.println(actualItem + " = " + expItem);
                    flag=true;
                }

                else
                {
                    flag=false;
                }
            }
        }

    }

    catch(Exception e)
    {
        System.out.println(e.getMessage());
    }
    return flag;
}

}
