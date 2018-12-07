package com.backbase.utilLibrary;

public class DriverFactory {
	
	private static String chromeDriverExePath;
	private static String geckoDriverExePath;
	private static String IEDriverExePath;
	private static String configPropertyFile;
	private static String gridPath;
	private static boolean isRemote;
	
	
	
	
	public static boolean isRemote() {
		return isRemote;
	}
	public static void setRemote(boolean isRemote) {
		DriverFactory.isRemote = isRemote;
	}
	public static String getChromeDriverExePath() {
		return chromeDriverExePath;
	}
	public static void setChromeDriverExePath(String chromeDriverExePath) {
		DriverFactory.chromeDriverExePath = chromeDriverExePath;
	}
	public static String getGeckoDriverExePath() {
		return geckoDriverExePath;
	}
	public static void setGeckoDriverExePath(String geckoDriverExePath) {
		DriverFactory.geckoDriverExePath = geckoDriverExePath;
	}
	public static String getIEDriverExePath() {
		return IEDriverExePath;
	}
	public static void setIEDriverExePath(String iEDriverExePath) {
		IEDriverExePath = iEDriverExePath;
	}
	public static String getConfigPropertyFile() {
		return configPropertyFile;
	}
	public static void setConfigPropertyFile(String configPropertyFile) {
		DriverFactory.configPropertyFile = configPropertyFile;
	}
	public static String getGridPath() {
		return gridPath;
	}
	public static void setGridPath(String gridPath) {
		DriverFactory.gridPath = gridPath;
	}
	

}
