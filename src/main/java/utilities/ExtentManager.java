package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	
	
public static ExtentReports extent;
	
	
	public static ExtentReports reportConfig()
	{
		String path=System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\extentreport.html";
		ExtentSparkReporter Reporter=new ExtentSparkReporter(path);
		Reporter.config().setReportName("Automation Results");
		Reporter.config().setDocumentTitle("Test results");
		extent=new ExtentReports();
		extent.attachReporter(Reporter);
		extent.setSystemInfo("Tester", "Uma");
		return extent;
	}
	

}
