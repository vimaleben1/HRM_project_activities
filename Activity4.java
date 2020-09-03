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


public class Activity4 {

	WebDriver driver;
	WebDriverWait wait;


	@BeforeMethod
	public void beforeMethod() {
		//Browser initialization

		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		Reporter.log("Browser Strated");

	}

	@Test
	@Parameters({"url","username","password", "uname1","pwd1","fname","lname"})
	public void act4(String url,String username, String password, String uname1,String pwd1,String fname,String lname)
	{
		//Login
		driver.get(url);
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();

		// Adding Employee
		WebElement pim= driver.findElement(By.id("menu_pim_viewPimModule"));
		wait.until(ExpectedConditions.visibilityOf(pim));
		pim.click();
		driver.findElement(By.id("menu_pim_addEmployee")).click();
		driver.findElement(By.id("firstName")).sendKeys(fname);
		driver.findElement(By.id("lastName")).sendKeys(lname);
		driver.findElement(By.xpath("//*[@name='chkLogin']")).click();
		WebElement username1= driver.findElement(By.cssSelector("#user_name"));
		username1.sendKeys(uname1);
		driver.findElement(By.cssSelector("#user_password")).sendKeys(pwd1);
		driver.findElement(By.cssSelector("#re_password")).sendKeys(pwd1);
		driver.findElement(By.cssSelector("#btnSave")).click();
		
		wait.until(ExpectedConditions.invisibilityOf(username1));
		//WebElement edit= driver.findElement(By.cssSelector("#personal_txtLicenNo"));
		//WebElement edit= driver.findElement(By.xpath("//*[@value='Edit']"));
		//wait.until(ExpectedConditions.visibilityOf(edit));
		// Validating user creation
		driver.findElement(By.xpath("//*[@id='menu_admin_viewAdminModule']")).click();
		driver.findElement(By.cssSelector("#searchSystemUser_userName")).sendKeys(uname1);
		driver.findElement(By.cssSelector("#searchBtn")).click();
		WebElement valUser= driver.findElement(By.xpath("//*[@id='resultTable']/tbody/tr/td[2]/a"));
		String valUserText=valUser.getText();
		Assert.assertEquals(valUserText, uname1);
		Reporter.log("Creation of employee successfully validated");
		System.out.println("Creation of employee successfully validated");
	}

	@AfterMethod
	public void am()
	{
		//Closing Browser
		driver.close();
		Reporter.log("Browser Closed");
		

	}

}