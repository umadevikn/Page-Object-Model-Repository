package utilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;




import io.github.bonigarcia.wdm.WebDriverManager;
import pages.HomePage;

public class RoughWork {
    
	
	public static void main(String[] args) throws UnknownHostException {
		
		String m = null;
		String messageBody;
		
try {
	 m=InetAddress.getLocalHost().getHostAddress();
	System.out.print(m);
} catch (UnknownHostException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


MonitoringMail mail = new MonitoringMail();

messageBody = "http://"+m+":8080/job/PageObjectModelFrameworkProject/ExtentReport/";

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

}
