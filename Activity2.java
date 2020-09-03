package project;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class Activity2 {

	WebDriver driver;
	WebDriverWait wait;


	@BeforeClass
	//Browser initialization and get URL

	public void beforeMethod() {
		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		Reporter.log("Browser Strated");

	}
	@Test
	@Parameters({"url"})
	public void act2(String url)
	{
		driver.get(url);
		// Getting Header URl and Printing
		WebElement headerText =driver.findElement(By.xpath("//*[@id='divLogo']//img"));
		wait.until(ExpectedConditions.visibilityOf(headerText));
		String 	sss= headerText.getAttribute("src");
		Reporter.log("URL of Header Image-> "+sss);
		System.out.println("URL of Header Image-> "+sss);

	}
	
	@AfterClass
	public void am()
	{
		//Closing browser
		driver.close();
		Reporter.log("Browser Strated");

	}
}