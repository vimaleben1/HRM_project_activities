package project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Activity1 {

	WebDriver driver;
	WebDriverWait wait;


	@BeforeMethod
	public void beforeMethod() {

		//Browser initialization and get URL
		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		Reporter.log("Browser Strated");

	}

	@Test
	@Parameters({"url","ExpTitle"})

	public void act1(String url, String ExpTitle)
	{
		// Validation of title
		driver.get(url);
		String acutalTitle= driver.getTitle();
		Assert.assertEquals(acutalTitle, ExpTitle);
		Reporter.log("Page Title Macthes with expected Title : "+acutalTitle);
	}

	@AfterMethod
	public void am()
	{
		//Closing browser
		driver.close();
		Reporter.log("Browser Closed");

	}

}