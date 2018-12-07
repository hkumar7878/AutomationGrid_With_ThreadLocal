package com.zoho.baseSetUp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import com.backbase.utilLibrary.DriverFactory;
import com.backbase.utilLibrary.ExcelReader;
import com.backbase.utilLibrary.ExtentManager_zoho;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.zoho.extentlisteners.ExtentListeners;


public class BaseSetUp_Grid {

	//public static final Logger logger = Logger.getLogger(Test.class.getName());
	
	public String app_Url;
	public String browserName;
	DesiredCapabilities cap=null;
	public static ExcelReader excelReader = null;
	public static Properties prop;
	public static ExcelReader ExcelRd_Obj_Test_Suite = null;
	public WebDriver driver1;
	//protected RemoteWebDriver driver;
	protected WebDriver driver;
	protected RemoteWebDriver driver_td;
	//public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<RemoteWebDriver>();
	public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();
	public static ThreadLocal<ExtentTest> exTest= new ThreadLocal<ExtentTest>(); 
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\LoginSuite.xlsx");
	public static ExcelReader mstrSuite = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\MasterSuite.xlsx");
	public ExtentReports rep = ExtentManager_zoho.getInstance();
	public static ExtentTest test;
	public static String screenshotPath;
	public static String screenshotName;
	public static Logger logger = Logger.getLogger("devpinoyLogger");
	public FileInputStream fis;
	private Properties Config = new Properties();
	public boolean gridExecution=false;
	
	public BaseSetUp_Grid() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//com//backbase//configuration//configuration.properties");
			prop.load(ip);
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeSuite
	public void setUpFramework()
	{
		DriverFactory.setGridPath("http://localhost:4444/wd/hub");
		DriverFactory.setConfigPropertyFile(System.getProperty("user.dir") + "//src//main//java//com//backbase//configuration//configuration.properties");
		DriverFactory.setChromeDriverExePath(System.getProperty("user.dir") + "//Browser Exes//chromedriver.exe");
		DriverFactory.setGeckoDriverExePath(System.getProperty("user.dir") + "//Browser Exes//geckodriver.exe");
		try {
			fis= new FileInputStream(DriverFactory.getConfigPropertyFile());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Config.load(fis);
			logger.info("Property file loaded!!!!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void configureLogging()
	{
		String log4jConfigFile=System.getProperty("user.dir") +"//src//test//resources//properties//log4j1.properties";
		PropertyConfigurator.configure(log4jConfigFile);
	}
	
	/*protected RemoteWebDriver getDriver() {
        return dr.get();
    }*/
	
	protected WebDriver getDriver() {
        return dr.get();
    }
	
	/*public void setWebDriver(RemoteWebDriver driver){
		dr.set(driver);
	}*/
	
	public void setWebDriver(WebDriver driver){
		dr.set(driver);
	}
	
	public ExtentTest getExtTest() {
        return exTest.get();
    }
	
	public void setExtentTest(ExtentTest et){
		exTest.set(et);
	}	
	public void reportPass(String msg){
		getExtTest().log(LogStatus.PASS, msg);
	}	
	public void reportFailure(String msg){
		getExtTest().log(LogStatus.FAIL, msg);
		captureScreenshot();
		
	}
	
	public void captureScreenshot()  {

		File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		try {
			FileUtils.copyFile(scrFile,new File(System.getProperty("user.dir") + "\\reports\\" + screenshotName));
		} catch (IOException e) {
	
		}
		getExtTest().log(LogStatus.INFO, " Screen shot--> " + test.addScreenCapture(System.getProperty("user.dir") + "\\reports\\"+screenshotName));

	}

	public void addLog(String msg,String browser1)
	{
		logger.debug("Running thread value is : " + getThreadValue(dr.get())+   "   for Browser : " + "  "+ browser1 + "" +msg);	
		logger.info("Browser : " + browser1 + "" +msg);
	}
	
	
	
	public void passInfo(String messaage)
	{
		ExtentListeners.testReport.get().pass(messaage);
	}
	
	public void failInfo(String messaage)
	{
		ExtentListeners.testReport.get().fail(messaage);
	}
	
	public String getThreadValue(Object value)
	{
		String text=value.toString();
		String [] nextText=text.split(" ");
		String text2=nextText[nextText.length-1].replace("(", "").replace(")","");
		String [] newText2=text2.split("-");	
		String reqText=newText2[newText2.length-1];
		System.out.println("Thread value is " + reqText);
		
		return reqText;
	}
	
	public void initializeTestBaseSetup3(String browserType) {
		
		
		String browser=browserType;
		if(System.getenv("ExecutionType")!=null && System.getenv("ExecutionType").equalsIgnoreCase("Grid")){
			gridExecution=true;
		}
		DriverFactory.setRemote(gridExecution);
		app_Url = prop.getProperty("App_URL");
		//app_Url = Config.getProperty("App_URL");
		
		if(DriverFactory.isRemote())		
		{		
			if (browserType.contains("Firefox")) 
				{										
					System.out.println("Local Thread- Docker--Launching firefox browser");
					logger.info("Creating a object of Firefox Browser");
					logger.info("Navigating to " + app_Url + "for Firefox browser");		
					cap = DesiredCapabilities.firefox();
					cap.setBrowserName("firefox");
					cap.setPlatform(Platform.ANY);													
				}			
				else if (browserType.contains("Chrome")) 
				{	
					logger.info("Creating a object of Chrome Browser");
					System.out.println("Local Thread--Docker - Config- Launching Chrome browser......");			
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.addArguments("disable-gpu");
					cap = DesiredCapabilities.chrome();
					cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);						
				}
				try {
					driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				logger.info("Starting the grid session");
		}
		
		else 
		{
			if (browserType.contains("Chrome")) 
			{
				System.out.println("Launching Chrome browser");
				System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				driver= new ChromeDriver();	
				logger.info("Chrome browwer launched");
			}
			else if(browserType.contains("Firefox")) 
			{
				System.out.println("Launching Firefox browser");
				FirefoxOptions firefox_options  = new FirefoxOptions();
				firefox_options.setCapability("marionette", true);
				System.setProperty("webdriver.gecko.driver", DriverFactory.getGeckoDriverExePath());
				driver= new FirefoxDriver(firefox_options);	
				logger.info("Firefox browser launched");
			}
				
		}
		
		setWebDriver(driver);
		getDriver().manage().window().maximize();
		//String s1=app_Url;
		//getDriver().get("http://computer-database.herokuapp.com/computers");
		getDriver().get(app_Url);
		getExtTest().log(LogStatus.INFO, "Opening application and navigating to Application");
		getExtTest().log(LogStatus.INFO, "Application opened successfully for " + browser + " browser");
		System.out.println(dr.get());
		System.out.println("Application opened successfully for " + browser);
		System.out.println("Application opened successfully for " + browser);
				
	} 				
	

	
	/*public void initializeTestBaseSetup3(String browserType) {
		//DriverFactory.setRemote(true);
		String browser=browserType;
		//app_Url = prop.getProperty("App_URL");
		app_Url = Config.getProperty("App_URL");
			try {
				if (browserType.contains("Firefox")) 
				{										
					System.out.println("Local Thread- Docker--Launching firefox browser");
					logger.info("Creating a object of Firefox Browser");
					logger.info("Navigating to " + app_Url + "for Firefox browser");		
					cap = DesiredCapabilities.firefox();
					cap.setBrowserName("firefox");
					cap.setPlatform(Platform.ANY);			
					//driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);															
				}			
				else if (browserType.contains("Chrome")) 
				{	
					logger.info("Creating a object of Chrome Browser");
					System.out.println("Local Thread--Docker - Config- Launching Chrome browser......");			
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.addArguments("disable-gpu");
					cap = DesiredCapabilities.chrome();
					cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);		
					//driver=new RemoteWebDriver(new URL ("http://localhost:4444/wd/hub"), cap);						
				}
				driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
				setWebDriver(driver);
				//driver_td=getDriver();
				getDriver().manage().window().maximize();
				getDriver().get(app_Url);
				getExtTest().log(LogStatus.INFO, "Opening application and navigating to Application");
				getExtTest().log(LogStatus.INFO, "Application opened successfully for " + browser + " browser");
				System.out.println(dr.get());
				System.out.println("Application opened successfully for " + browser);
				//ExtentListeners.testReport.get().info("Test");
				String s1=browser;
				//logInfo(s1);
				System.out.println("Application opened successfully for " + browser);
				
			} 				
			catch (Exception e) 
			{
				System.out.println("Error....." + e.getMessage());
			}	
}*/
	
	
	
	public void logInfo(String messaage)
	{
		try{
			ExtentListeners.testReport.get().info(messaage);
		}
		catch(Exception e){
			System.out.println("Error is" + e.getMessage());
		}
	}
	
	public static String [][] getExcelData1(String ExcelName,String SheetName)
	 {
	    	String path= System.getProperty("user.dir")+"\\Test Excel Files\\" + ExcelName;
	    	excelReader=new ExcelReader(path);
	    	String[][] excelData = excelReader.getDatafromExcel(SheetName, ExcelName);
	    	return excelData;
	 }	  
	
	public static String [][] getExcelHashData(String ExcelName,String SheetName)
	 {
	    	String path= System.getProperty("user.dir")+"\\Test Excel Files\\" + ExcelName;
	    	excelReader=new ExcelReader(path);
	    	String[][] excelData = excelReader.getDatafromExcel(SheetName, ExcelName);
	    	return excelData;
	 }

	
	
	// This method can be used while environmental variables i.e browser name
	// from Jenkins
	public void initializeTestBaseSetup1(String browserType) {
		if(System.getenv("Browser")!=null && !System.getenv("browser").isEmpty())
			//browser = System.getenv("browser");
		//else
			//browser = prop.getProperty("browser");
		
		//prop.setProperty("Browser_Type", browser);
		app_Url = prop.getProperty("App_URL");
		browserName=prop.getProperty("Browser_Type");
		
		
		try {		
			//if (browserName.contains("Firefox")) {
			if (prop.getProperty("Browser_Type").equals("Firefox")) {
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "\\" + "Browser Exes/geckodriver.exe");
				System.out.println("Launching firefox browser");
				logger.info("Creating a object of Firefox Browser");
				logger.info("Navigating to " + app_Url + "for Firefox browser");	
				driver = new FirefoxDriver();		
				driver.get(app_Url);			
				driver.manage().window().maximize();
			}
			
			//else if (browserName.contains("Chrome")) {
			else if (prop.getProperty("Browser_Type").equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\" + "Browser Exes/chromedriver.exe");
				System.out.println("Launching chrome browser");
				logger.info("Creating a object of chrome Browser");
				logger.info("Navigating to " + app_Url + "for chrome browser");		
				driver = new ChromeDriver();			
				driver.get(app_Url);			
				driver.manage().window().maximize();
			}
			
		} catch (Exception e) {
			System.out.println("Error....." + e.getMessage());
		}

	}
	
	
	//@BeforeTest	   
	 public void onBeforeTest(ITestContext testContext)
	    {
	
		 String log4jConfigPath=System.getProperty("user.dir")+"\\"+ "log4j.properties";
	     PropertyConfigurator.configure(log4jConfigPath);          
	        try
	        {	            
	                System.out.println("Inside Before Test class of BASE CLASS: FIREFOX");
	                String filePath=System.getProperty("user.dir")+"\\"+ "TestReportsAllURIsValidation.html";
	                String filePath1=System.getProperty("user.dir")+"\\"+ "TestReportsAllURIsValidation1.html";
	                //report=new ExtentReports(filePath,true, DisplayOrder.OLDEST_FIRST);  
	                //report1=new ExtentReports(filePath1,true, DisplayOrder.OLDEST_FIRST);
	        }
	        catch(Exception e)
	        {
	            System.out.println(e.getMessage());
	        }
	    }
	
}
