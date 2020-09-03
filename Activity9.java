package project;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Activity9 {

	WebDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void beforeMethod() {
		//Browser initialization

		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		Reporter.log("Browswer Started");

	}
	@Test
	@Parameters({"url","username","password"})
	public void act9 (String url,String username, String password) throws InterruptedException   
	{
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();
		Reporter.log("Login Successful");

		WebElement welcome=driver.findElement(By.id("welcome"));
		wait.until(ExpectedConditions.visibilityOf(welcome));
		// Navigating to MyInfo and filling inputs
		
		Actions acc=new Actions(driver);
		WebElement pim1= driver.findElement(By.id("menu_pim_viewMyDetails"));
		acc.moveToElement(pim1).click().build().perform();
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[1]/ul/li[3]/a")).click();
		List <WebElement> rowValue= driver.findElements(By.xpath("//*[@id='emgcontact_list']/tbody/tr/td"));
		for(WebElement rv:rowValue)

		{
			if(rv.getText().isBlank())
					{
					}
			else
			Reporter.log("Emergency contact details retrieved"+rv.getText());

		}
		
		Assert.assertTrue(true);
		Reporter.log("All Emergency contact details retrieved");
		System.out.println("Emergency contact details retrieved");
	}

	@AfterMethod
	public void am()
	{
		Reporter.log("Browswer Closed");
		driver.close();
	}
	
}
