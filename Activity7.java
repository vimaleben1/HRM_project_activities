package project;

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

public class Activity7 {

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
	@Parameters({"url","username","password","cmpy","job","fromDate","toDate","comments"})
	public void act7(String url,String username, String password,String cmpy,String job, String fromDate,String toDate,String comments) throws InterruptedException   
	{
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();
		WebElement welcome=driver.findElement(By.id("welcome"));
		wait.until(ExpectedConditions.visibilityOf(welcome));
		// Navigating to MyInfo
		Actions acc=new Actions(driver);
		WebElement pim1= driver.findElement(By.id("menu_pim_viewMyDetails"));
		acc.moveToElement(pim1).click().build().perform();
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div[1]/ul/li[9]/a")).click();
		WebElement add= driver.findElement(By.cssSelector("#addWorkExperience"));
	
		// Adding Work Experience
		add.click();
		driver.findElement(By.cssSelector("#experience_employer")).sendKeys(cmpy);
		driver.findElement(By.cssSelector("#experience_jobtitle")).sendKeys(job);
		WebElement fromd=driver.findElement(By.cssSelector("#experience_from_date"));
		fromd.clear();
		fromd.sendKeys(fromDate);
		WebElement tod=driver.findElement(By.cssSelector("#experience_to_date"));
		tod.clear();
		tod.sendKeys(toDate);
		driver.findElement(By.cssSelector("#experience_comments")).sendKeys(comments);
		driver.findElement(By.cssSelector("#btnWorkExpSave")).click();	
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		WebElement add1= driver.findElement(By.cssSelector("#addWorkExperience"));
		Assert.assertTrue(add1.isEnabled());
		Reporter.log("Qualifications Added & Validated");
	}
	@AfterMethod
	public void am()
	{
		driver.close();
		Reporter.log("Browswer Closed");

	}
}
