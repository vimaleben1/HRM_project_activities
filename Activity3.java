package project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class Activity3 {

	WebDriver driver;
	WebDriverWait wait;


	@BeforeMethod

	//Browser initialization and get URL
	public void beforeMethod() {
		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		Reporter.log("Browser Strated");



	}
	@Test
	@Parameters({"url","username","password"})
	public void act3(String url, String username, String pass)
	{
		driver.get(url);

		// Filling required inputs to Login
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(pass);
		driver.findElement(By.cssSelector("#btnLogin")).click();

		//Validation after Login	
		WebElement buttonCheck= driver.findElement(By.id("MP_link"));
		wait.until(ExpectedConditions.visibilityOf(buttonCheck));
		Assert.assertTrue(buttonCheck.isDisplayed());
		Reporter.log("HomePage has Opened, Login Succesfull");
	}


	@AfterMethod
	public void am()
	{
		driver.close();
		Reporter.log("Browser Closed");

	}

}