package utilities;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import base.Page;




	public class CustomListener extends Page implements ITestListener,ISuiteListener
	{

		public String messageBody;
		
		public void onTestStart(ITestResult result) 
		{
			Reporter.log("Test execution started!!");
			test=extent.createTest(result.getMethod().getMethodName());
		}

		public void onTestSuccess(ITestResult result) 
		{
			Reporter.log("Test execution success!!");
			test.log(Status.PASS, "Test case PASS");
			extent.flush();
		}

		public void onTestFailure(ITestResult result)
		{
			System.setProperty("org.uncommons.reportng.escape-output","false");
			Reporter.log("Test execution failed.");
			
			test.log(Status.FAIL,result.getThrowable() );
			
			TestUtility.CaptureScreenshot(result.getMethod().getMethodName());
			
			try {
				test.addScreenCaptureFromPath(TestUtility.destpath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Reporter.log("<a target=\"_blank\" href="+TestUtility.destpath+">Click here to view screenshot <img src="+TestUtility.destpath+" height=50 width=50></a>");
						
			extent.flush();
		}

		public void onTestSkipped(ITestResult result) 
		{
			Reporter.log("Test execution skipped for:"+result.getMethod().getMethodName());
			test.log(Status.SKIP, "Test execution skipped for:"+result.getMethod().getMethodName());
			extent.flush();
		}

		public void onFinish(ISuite arg0) {
			
			MonitoringMail mail = new MonitoringMail();
			 
			try {
				messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
						+ ":8080/job/PageObjectModelFrameworkProject/ExtentReport/";
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			try {
				mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}

		public void onStart(ISuite arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onFinish(ITestContext arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onStart(ITestContext arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
			// TODO Auto-generated method stub
			
		}
		


}
