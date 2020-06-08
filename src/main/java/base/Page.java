package base;

//comment by uma

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;
import utilities.ExtentManager;

import utilities.TestUtility;

public class Page {

	
	public static WebDriver driver;
	public static FileInputStream fis;
	public static Properties config=new Properties();
	public static Properties OR=new Properties();
	public static Logger log=Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel=new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\Testdata.xlsx");
	public ExtentReports extent=ExtentManager.reportConfig();
	public static ExtentTest test;
	public static WebDriverWait wait;
	public static String Browser;
	
	public Page()
	{
		if(driver==null)
		{
			
			try {
				fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
		try {
			config.load(fis);
			log.debug("Config file loaded"); 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		try
		{
			fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			OR.load(fis);
			log.debug("OR file loaded!!");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		if(System.getenv("Browser")!=null && !System.getenv("Browser").isEmpty())
		{
			Browser=System.getenv("Browser");
			
		}else
			
		{
			Browser=config.getProperty("Browser");
		}
		
		config.setProperty("Browser", Browser);
			
		if(config.getProperty("Browser").equals("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			
			
		}
		else if(config.getProperty("Browser").equals("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}
		else if(config.getProperty("Browser").equals("ie"))
		{
			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
		}

		driver.get(config.getProperty("url"));
		log.debug("Application launched!!!");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		
	}

}
	
	public boolean isElementPresent(String locator)
	{
		try
		{
			if(locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator)));
				test.info(locator+" is present");
				return true;
				}
				else if(locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator)));
				test.info(locator+" is present");
				return true;
				}
				else if(locator.endsWith("_ID"))
				{
					driver.findElement(By.id(OR.getProperty(locator)));
					test.info(locator+" is present");
					return true;
				}
			
			
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public void click(String locator)
	{
		if(locator.endsWith("_XPATH")) {
		driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}
		else if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}
		else if(locator.endsWith("_ID"))
		{
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		test.info("Clicked on:"+locator);
		
	}
	
	public void type(String locator,String data)
	{
		driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(data);
		test.info("Values typed on:"+locator);
	}
	
	
	
	public void verifyEquals(String expected,String actual,String methodName) throws IOException
	{
		
		try
		{
			Assert.assertEquals(expected, actual);
		}
		
		catch(Throwable t)
		{
			TestUtility.CaptureScreenshot(methodName);
			test.log(Status.FAIL,"Verification failed"+t.getMessage());
			test.addScreenCaptureFromPath(TestUtility.destpath);
			Reporter.log("Verification test failed.");				
			Reporter.log("<a target=\"_blank\" href="+TestUtility.destpath+">Click here to view screenshot <img src="+TestUtility.destpath+" height=50 width=50></a>"); 
			
		}							
		
	}
	
	
	public void select(String locator,String value)
	{
		WebElement dropDown=driver.findElement(By.xpath(OR.getProperty(locator)));
		test.info("Element located:"+locator);
		
		Select select=new Select(dropDown);
		select.selectByVisibleText(value);
		test.info("Value selected from dropdown"+value);
		
	}
	
	
	public static void quit()
	{
		driver.quit();
	}
	
	
}
