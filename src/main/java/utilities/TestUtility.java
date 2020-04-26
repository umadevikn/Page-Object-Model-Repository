package utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import base.Page;
import utilities.ExcelReader;





public class TestUtility extends Page{

	
	public static String destpath;
	
	public static void CaptureScreenshot(String testName)
	{
		
		TakesScreenshot ts=(TakesScreenshot)driver;
		
		File source=ts.getScreenshotAs(OutputType.FILE);
		
		Date d=new Date();
		String date =d.toString().replace(":", "_").replace(" ", "_");
		
		
		destpath=System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+testName+"_"+date+".png";
		
		File file=new File(destpath);
		
		try {
			FileUtils.copyFile(source, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m)
	{
		String sheetName=m.getName();
		int rows=excel.getRowCount(sheetName);
		int colms=excel.getColumnCount(sheetName);
		
		
		Object[][] data=new Object[rows-1][colms];
		
		
		for(int i=2;i<=rows;i++) 
		{
			
			for(int j=0;j<colms;j++)
			{
				data[i-2][j]=excel.getCellData(sheetName,j,i);
			}
		
			
	}
		return data;

}
	
	
	public static boolean isTestRunnable(String testName,ExcelReader excel)
	{
		String sheetName="TestCases";
		
		int rowNum=excel.getRowCount(sheetName);
		
		for(int row=2;row<=rowNum;row++)
		{
			String testCase=excel.getCellData(sheetName, "TC_ID", row);
			if(testCase.equalsIgnoreCase(testName))
			{
			String runmode=excel.getCellData(sheetName, "runMode", row);
			
			if(runmode.equalsIgnoreCase("Y"))	
				return true;
			else
				return false;
			}
		
		
	}
		return false;
	
	}
}
