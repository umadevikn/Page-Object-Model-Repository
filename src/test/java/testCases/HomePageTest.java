package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HomePage;

public class HomePageTest extends BaseTest{
	
	
	@Test
	public void homePageTest()
	{
		
		HomePage home=new HomePage();
		home.doLogin();	
		//Assert.fail("Home page test failed.");
		
	}
	
	
	
	

}
