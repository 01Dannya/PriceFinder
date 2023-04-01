package utilities;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	public static WebDriver driver;
	
	private static Properties configFile;
	private static Properties userDataObject;
	
	static {
		
		try {
			String configFilePath = Constants.configuration_filePath;
			String userDataFilePath = Constants.appDataConfig_filePath;
			
			FileInputStream configInput = new FileInputStream(configFilePath);
			FileInputStream userDataInput = new FileInputStream(userDataFilePath);
			
			configFile = new Properties();
			userDataObject = new Properties();
			
			configFile.load(configInput);
			userDataObject.load(userDataInput);
			
			configInput.close();
			userDataInput.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static WebDriver getDriver() {
		
		if (driver == null) {
			
			switch (BaseClass.getConfigProperty("browser")) {
			
			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				
				break;
			
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				
				break;
				
			}
			
			driver.get(BaseClass.getConfigProperty("url"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Constants.implicitWaitSec, TimeUnit.SECONDS);
			PageInitializer.initialize();
			
			
		
		}
		
		return driver;
		
	}
	
	public static void tearDown() {
		
		if (driver != null) {
			driver.close();
			
		}
		
	}
	
	public static String getConfigProperty(String keyName) {
		return configFile.getProperty(keyName);
	}
	
	public static String getUserProperty(String keyName) {
		return userDataObject.getProperty(keyName);
	}
}
