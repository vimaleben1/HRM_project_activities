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

public class Activity6 {

	WebDriver driver;
	WebDriverWait wait;


	@BeforeMethod
	public void beforeMethod() {
		//Browser initialization

		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		Reporter.log("Started Browser");

	}
	@Test
	@Parameters({"url","username","password","dirname"})
	public void act6(String url,String username, String password,String dirname) throws InterruptedException   
	{
		//Login
		driver.get(url);
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();
		WebElement welcome=driver.findElement(By.id("welcome"));
		wait.until(ExpectedConditions.visibilityOf(welcome));
		// Directory  menu and click event
		WebElement dir= driver.findElement(By.id("menu_directory_viewDirectory"));
		Assert.assertTrue(dir.isEnabled());
		if(dir.isEnabled())
		{
			dir.click();
			WebElement header=driver.findElement(By.xpath("//*[@class='head']/h1"));
			String text2=header.getText();
			Assert.assertEquals(text2,dirname);
			Reporter.log("Verified Directory menu item is visible and clickable");
		}
	}

	@AfterMethod
	public void am()
	{
		//driver.close();
		Reporter.log("Closing browser");

	}
}
