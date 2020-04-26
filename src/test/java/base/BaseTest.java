package base;

import org.testng.annotations.AfterSuite;

public class BaseTest {
	
	
	@AfterSuite
	public void tearDown()
	{
		Page.quit();
	}
	


}
