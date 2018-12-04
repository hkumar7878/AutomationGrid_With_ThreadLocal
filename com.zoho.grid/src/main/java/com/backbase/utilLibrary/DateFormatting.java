package com.backbase.utilLibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatting {

	public static void main(String[] args) throws ParseException {
		//String date=converstionDates("dd MMM yyyy","d/M/yyyy","22 Nov 2018");
		String date=converstionDates("dd MMM yyyy","yyyy-MM-dd","22 Nov 2018");
		System.out.println("Require date is " + date);
	
		//   Require date is 22/11/2018
		// Require date is 2018-11-22
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

}
