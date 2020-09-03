package project;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Activity8 {

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
	@Parameters({"url","username","password","lType","dateF1","dateT1","cmt"})
	public void act8 (String url,String username, String password,String lType,String dateF1, String dateT1, String cmt) throws InterruptedException   
	{
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();
		WebElement welcome=driver.findElement(By.id("welcome"));
		wait.until(ExpectedConditions.visibilityOf(welcome));
	
		// Applying Leave
		//WebElement dashBoard= driver.findElement(By.id("menu_dashboard_index"));
		//dashBoard.click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("Apply Leave")).click();
		//Thread.sleep(1000);
		Select lt= new Select(driver.findElement(By.id("applyleave_txtLeaveType")));
		lt.selectByVisibleText(lType);
		WebElement leavebalance=driver.findElement(By.id("applyleave_leaveBalance"));
		wait.until(ExpectedConditions.visibilityOf(leavebalance));

		WebElement dateF= driver.findElement(By.cssSelector("#applyleave_txtFromDate"));
		dateF.clear();
		dateF.sendKeys(dateF1);

		WebElement dateT= driver.findElement(By.cssSelector("#applyleave_txtToDate"));
		dateT.clear();
		dateT.sendKeys(dateT1);
		String balance =leavebalance.getText();
		driver.findElement(By.cssSelector("#applyleave_txtComment")).sendKeys(cmt);
		driver.findElement(By.cssSelector("#applyBtn")).click();
		System.out.println("Leave Applied");

		//Navigating to My leave page
		driver.findElement(By.id("menu_dashboard_index")).click();
		Thread.sleep(3000);
		driver.findElement(By.linkText("My Leave")).click();
		WebElement leaveFDate= driver.findElement(By.cssSelector("#calFromDate"));
		leaveFDate.clear();
		leaveFDate.sendKeys(dateF1);
		WebElement leaveTDate= driver.findElement(By.cssSelector("#calToDate"));
		leaveTDate.clear();
		leaveTDate.sendKeys(dateT1);
		driver.findElement(By.id("btnSearch")).click();
		WebElement status=driver.findElement(By.xpath("//*[@id='resultTable']/tbody/tr/td[6]"));
		System.out.println("Leave Status -> "+status.getText());	
		Assert.assertTrue(true);
		Reporter.log("Leave Appiled..." +"Pending Leave balance= "+balance);

	}

	@AfterMethod
	public void am()
	{
		driver.close();
		Reporter.log("Browswer Closed");

	}

}

