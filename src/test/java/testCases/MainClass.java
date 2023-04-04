package testCases;

import java.awt.Desktop.Action;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.Constants;

public class MainClass {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void beforeMethod() {
		
		WebDriverManager.firefoxdriver().setup();
		
		driver = new FirefoxDriver();
		
		driver.get("https://www.redfin.com/");
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		driver.manage().window().maximize();
		
		
		
	}
	
	@Test(enabled = true)
	public void readAddressWritePrice() throws IOException {
		
		FileInputStream fs = new FileInputStream(Constants.excelDataPath);
		
		Workbook wb = new XSSFWorkbook(fs);
		
		Sheet sheet = wb.getSheetAt(6);
		
		int lastRow = sheet.getLastRowNum();
		
		for (int i = 1; i<= 6; i++) {
			
			driver.get("https://www.redfin.com/");
			
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			Row row = sheet.getRow(i);
			String address = row.getCell(4).getStringCellValue();
			driver.findElement(By.xpath("//*[@class='TabContent']")).click();
			driver.findElement(By.xpath("//*[@id='tabContentId0']/div/div/form/div/div/input")).sendKeys(address);
			driver.findElement(By.xpath("//*[@class='Tabs']/div[2]/div/div/form/div/button")).click();
			try {
			if (driver.findElement(By.xpath("//*[@class='item-row item-row-show-sections clickable'][1]")).isDisplayed()) {
				driver.findElement(By.xpath("//*[@class='item-row item-row-show-sections clickable'][1]")).click();
			} 
			else 
				System.out.println("bruh");
			} catch(NoSuchElementException e) {
	            System.out.println("Impossible to click the pop-up.");
			}
			try {
				if (driver.findElement(By.xpath("//*[@class='HomeViews']/div/div/div[1]")).isDisplayed()) {
					String price = driver.findElement(By.xpath("//*[@class='HomeViews']/div/div/div[1]/div/div/div[2]/span")).getText();
					Cell cell = row.createCell(14);
					cell.setCellValue(price);
					continue;
				}
				else
					System.out.println("bruh");
			} catch (NoSuchElementException e) {
				System.out.println("DOOR STUCK");
			}
			String price = driver.findElement(By.xpath("//*[@class='home-main-stats-variant']/div[1]/div")).getText();
			Cell cell = row.createCell(14);
			cell.setCellValue(price);
			
		}
		
		Row row = sheet.getRow(1);
		Cell cell = row.createCell(3);
		cell.setCellValue("test");
		
		FileOutputStream fos = new FileOutputStream(Constants.excelDataPath);
		wb.write(fos);
		fos.close();
		wb.close();
	}

}
