package com.backbase.utilLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.backbase.errorCollectors.ErrorCollector;
import com.zoho.baseSetUp.BaseSetUp_Grid;


public class GenericUtilLib extends BaseSetUp_Grid {
	 

	public static boolean clickLink_BasedOn_LinkName(WebElement table, String newcompName) throws Exception {

		boolean flag = false;

		List<WebElement> linkElements = new ArrayList<WebElement>();
		try {
			List<WebElement> rows_table = table.findElements(By.tagName("tr"));
			int rows_count = rows_table.size();
			for (int row = 1; row < rows_count; row++) {
				linkElements = rows_table.get(row).findElements(By.tagName("td"));
				for (int i = 0; i < linkElements.size(); i++) {
					if (linkElements.get(i).getText().equalsIgnoreCase(newcompName)) {
						linkElements.get(i).findElement(By.tagName("a")).click();
						flag = true;
						break;
					}

				}
				break;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			flag = false;

		}
		return flag;

	}
	
	public void checkElementVisiblity(WebElement we) throws InterruptedException {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.textToBePresentInElement(we, "Play sample application — Computer database"));
		}

		catch (StaleElementReferenceException se) {
			Thread.sleep(4000);
			System.out.println("Exception occured " + se.getMessage());
		}
		// wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(""));
		// wait.until(ExpectedConditions.;
	}

	public static boolean verifyText(WebElement we, String expVerifyText) {
		boolean flag = false;
		try {
			if (we.isDisplayed()) {
				String actual_hd_txt = we.getText();
				if (actual_hd_txt.contains(expVerifyText))
					flag = true;
				else
					flag = false;
			}
			System.out.println(expVerifyText + " is displayed successfully");

		}

		catch (Throwable t) {
			System.out.println(expVerifyText + "header text is not displayed");
			flag = false;
		}
		return flag;

	}

	public boolean verifyLinkDisplay(WebElement we, String expVerifyText) {
		boolean flag = false;
		try {

			//Thread.sleep(3000);
			String actual_hd_txt = we.getText();
			if (actual_hd_txt.contains(expVerifyText))
				return true;
			else 
				return false;
			
		}

		catch (Exception t) {
			System.out.println(expVerifyText + "text is not displayed");
			return false;
			
		}
		
	}
	
	 public static String getSelectVisibleVal(WebElement we)
	   {
		   String elementTxt=null;
		   try
		   {
			   Select select= new Select(we);
			   WebElement option=select.getFirstSelectedOption();
			   elementTxt =option.getText();
			   System.out.println("Selected value is" + elementTxt);
		   }
		   
		   catch(Exception e)
		   {
			   System.out.println(e.getMessage());
		   }
		return elementTxt;
	   }

	public static boolean verifyBtnDisplayed(WebElement we) {

		try {
			boolean flag = false;
			// checkPageIsReady();
			// GenericUtilLib.checkElementVisiblity(we);
			// checkElementVisiblity(we);
			// String actual_hd_txt=we.getText();
			if (we.isDisplayed()) {
				flag = true;
				//logger.info("--------------" + we.getText() + " element is displayed-----------------");
			} else {
				flag = false;
				//logger.info("--------------" + we.getText() + " element is not displayed-----------------");
			}

			return flag;
		}

		catch (Throwable t) {
			return false;
		}

	}

	public static boolean verifyPageHeader(WebElement we, String expVerifyText) {

		try {
			// checkPageIsReady();
			// GenericUtilLib.checkElementVisiblity(we);
			//checkElementVisiblity(we);
			String actual_hd_txt = we.getText();
			Thread.sleep(1000);
			Assert.assertEquals(actual_hd_txt, expVerifyText);
			System.out.println(expVerifyText + "is displayed successfully");
			// log.debug(expVerifyText + "text is displayed successfully");
			return true;
		}

		catch (Throwable t) {
			System.out.println(expVerifyText + "header text is not displayed");
			ErrorCollector.addVerificationFailure(t);
			// log.error(expVerifyText + "element is not present");
			return false;
		}

	}

	public static boolean clickElement(WebElement we) {

		try {

			Assert.assertTrue(we.isDisplayed(), "Element" + we + "is not visible");
			Thread.sleep(2000);
			we.click();
			// log.debug("Submit button is clicked successfully");
			System.out.println(we + "clicked successfully");
			return true;
		}

		catch (Exception e) {

			System.out.println("No such element" + we);
			// gm_WaitVisibility(we,40);
			ErrorCollector.addVerificationFailure(e);
			// log.error("Could not click " + we);
			System.out.println(we + "could not be clicked");
			// strErrMsg_GenLib =e.getMessage();
			return false;

		}

	}

	public final boolean SelectCheckbox(WebElement element) {
		boolean flag = false;
		try {
			if (!element.isSelected()) {
				element.click();
				flag = true;
			} else if (element.isSelected()) {
				flag = true;
			} else {
				throw new RuntimeException("Could not select Checkbox.");
			}
		} catch (RuntimeException e) {
			flag = false;
			// strErrMsg_GenLib = e.getMessage();
		}
		return flag;
	}

	public boolean isClickable(WebElement we) {
		try {

			/*
			 * WebElement parent = (WebElement) ((JavascriptExecutor)
			 * driver).executeScript( "return arguments[0].parentNode;", we);
			 */
			WebElement myElement = we;
			WebElement parent = myElement.findElement(By.xpath(".."));
			String attrValue = parent.getAttribute("Class");

			if (!attrValue.contains("disabled")) {
				// WebDriverWait wait = new WebDriverWait(setup.driver, 20);
				// wait.until(ExpectedConditions.elementToBeClickable(we));
				// wait.until(ExpectedConditions.visibilityOf(myElement));

				we.click();
				return true;
			}

			else {
				System.out.println("Element is not clickable");
				return false;
			}

		} catch (Exception e) {
			System.out.println("Element is not clickable" + e.getMessage());
			return false;
		}
	}

	public WebElement handleStaleElement(String elementName) throws InterruptedException {
		int count = 0;
		WebElement staledElement = null;

		while (count < 4) {
			try {

				Thread.sleep(2000);
				// staledElement =
				// setup.driver.findElement(By.xpath(elementName));
				// return staledElement;

			} catch (StaleElementReferenceException e) {
				e.toString();
				System.out.println("Trying to recover from a stale element :" + e.getMessage());
				count = count + 1;
			}
			count = count + 4;
		}
		return staledElement;

	}

	public static boolean EnterText(WebElement we, String inputValue, String elementName) {

		boolean flag = false;
		try {
			Assert.assertTrue(we.isDisplayed() && we.isEnabled(),
					"Element" + elementName + "is not displayed & visible");
			we.clear();
			we.sendKeys(inputValue);
			flag = true;

		} catch (NoSuchElementException ne) {

			System.out.println("No such element dear");
			ne.getStackTrace();
			ErrorCollector.addVerificationFailure(ne);
			flag = false;

		} catch (ElementNotVisibleException nv) {

			System.out.println("No such element dear");
			nv.getStackTrace();
			ErrorCollector.addVerificationFailure(nv);
			flag = false;

		}

		catch (Throwable e) {
			System.out.println("No such element dear");
			e.getStackTrace();
			ErrorCollector.addVerificationFailure(e);
			flag = false;
		}

		return flag;
	}

	public void checkElementToBeClickable(WebElement we) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(we));

		}

		catch (RuntimeException e) {
			System.out.println(e.getMessage());
			// strErrMsg_GenLib=e.getMessage();
		}
	}

	public void checkElementTextTobePresent(WebElement we) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			// wait.until(ExpectedConditions.textToBePresentInElement(we,
			// "Groups"));
			wait.until(ExpectedConditions.elementToBeClickable(we));

		}

		catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean selectFromDropdown(WebElement we, String inputValue, String elementName) {
		try {
			Assert.assertTrue(we.isDisplayed() && we.isEnabled(),
					"Element" + elementName + "is not displayed & visible");
			// we.click();
			Select select = new Select(we);
			List<WebElement> options = select.getOptions();
			System.out.println(options.size());
			for (WebElement opt : options) {
				if (opt.getText().equals(inputValue)) {
					// opt.sendKeys(Keys.ENTER);
					Thread.sleep(2000);
					select.selectByVisibleText(inputValue);
					// javascriptclick(opt);
					break;
				}
			}
			return true;
		} catch (Throwable e) {
			System.out.println("No such element dear");
			ErrorCollector.addVerificationFailure(e);
			return false;
		}
	}

	/*public static void javascriptclick(WebElement element) {
		// WebElement webElement=driver.findElement(By.xpath(element));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].click();", element);
		System.out.println("javascriptclick" + " " + element);

	}*/

	public static boolean click_Element(WebElement we, String elementName) {
		try {
			Assert.assertTrue(we.isDisplayed() && we.isEnabled(), elementName + "is not visible");
			we.click();
			System.out.println(elementName + "clicked successfully");
			return true;
		} catch (Throwable ne) {
			System.out.println("No such element" + we);

			ErrorCollector.addVerificationFailure(ne);

			System.out.println(we + "could not be clicked");
			return false;
		}

	}

	public static boolean click_ElementCal(WebElement we, String elementName) {
		try {

			Assert.assertTrue(we.isDisplayed() && we.isEnabled(), elementName + "is not visible");
			we.sendKeys(Keys.ENTER);
			System.out.println(elementName + "clicked successfully");

			return true;
		} catch (Throwable ne) {
			System.out.println("No such element" + we);

			ErrorCollector.addVerificationFailure(ne);

			System.out.println(we + "could not be clicked");
			return false;
		}
	}

	/*public static void checkPageIsReady() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Initially bellow given if condition will check ready state of page.
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}

		// This loop will rotate for 25 times to check If page Is ready after
		// every 1 second.
		for (int i = 0; i < 25; i++) {
			try {
				Thread.sleep(1000);
			}

			catch (InterruptedException e)

			{

			}

			if (js.executeScript("return document.readyState").toString().equals("complete"))
				break;
		}
	}*/

	/*public static void checkElementClickable(WebElement we) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(we));

		// wait.until(ExpectedConditions.;
	}*/

	

	public final boolean ClickUsingJS(int Timeout) {
		boolean flag = false;
		try {
			WebElement element = driver.findElement(By.id("gbqfd"));

			JavascriptExecutor executor = (JavascriptExecutor) driver;

			executor.executeScript("arguments[0].click();", element);
		} catch (RuntimeException e) {
			String strErrMsg_GenLib = e.getMessage();
		}
		return flag;
	}

	public final boolean MouseHover(WebElement element) {
		boolean flag = false;
		try {
			// if (strErrMsg_GenLib != null)
			// return false;

			Actions builder = new Actions(driver);
			builder.moveToElement(element).build().perform();
			// genericLibrary.PerformAction("browser", Action.WaitForPageToLoad,
			// "5");
			flag = true;
		} catch (RuntimeException e) {
			flag = false;
			String strErrMsg_GenLib = e.getMessage();
		}
		return flag;
	}

	public static String GetText(WebElement we) {
		String strElementText = "";
		String strErrMsg_GenLib = "";
		try {
			strElementText = we.getText();
		} catch (RuntimeException e) {
			strElementText = "";
			strErrMsg_GenLib = e.getMessage();
		}
		return strElementText;
	}

	public static String GetValue(WebElement we) {
		String strElementValue = "";
		try {
			// if (strErrMsg_GenLib != null)
			// throw new Exception(strErrMsg_GenLib);

			strElementValue = we.getAttribute("value");

		} catch (RuntimeException e) {
			strElementValue = "";
			String strErrMsg_GenLib = e.getMessage();
		}
		return strElementValue;
	}

	public final boolean WaitForElement(String strObject, int iTimeout) {
		boolean flag = false;
		try {
			for (int i = 0; i <= iTimeout; i++) {
				if (strObject.contains("something")) {

					flag = true;
					break;
				} else {
					Thread.sleep(1000);
				}
			}
			if (!flag) {
				String strErrMsg_GenLib = "OBJECT '" + strObject + "' is not found. Waited for "
						+ (new Integer(iTimeout)).toString() + " seconds.";
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}

