package project;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Activity5 {

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
	@Parameters({"url","username","password", "empFname","empLname","DOB","mstatus","nation"})
	public void act5(String url,String username, String password, String empFname,String empLname,String DOB,String mstatus, String nation) throws InterruptedException 
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
		// Navigating to MyInfo and filling inputs
		WebElement pim1= driver.findElement(By.id("menu_pim_viewMyDetails"));
		

		Actions acc=new Actions(driver);
		
		acc.moveToElement(pim1).click().build().perform();
		//pim1.click();


		//WebElement save=driver.findElement(By.xpath("//*[@value='Edit']"));
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		WebElement save=driver.findElement(By.id("btnSave"));
		wait.until(ExpectedConditions.visibilityOf(save));
		save.click();
		WebElement personal_txtEmpFirstName=driver.findElement(By.id("personal_txtEmpFirstName"));
		personal_txtEmpFirstName.clear();	
		personal_txtEmpFirstName.sendKeys(empFname);
		WebElement personal_txtEmpLastName=driver.findElement(By.id("personal_txtEmpLastName"));
		personal_txtEmpLastName.clear();
		personal_txtEmpLastName.sendKeys(empLname);


		WebElement dateOfBirth=driver.findElement(By.id("personal_DOB"));
		dateOfBirth.clear();	
		dateOfBirth.sendKeys(DOB);

		// Radio button and Dropdown
		driver.findElement(By.id("personal_optGender_1")).click();
		Select martial=new Select(driver.findElement(By.id("personal_cmbMarital")));
		martial.selectByVisibleText(mstatus);
		Select nationty=new Select(driver.findElement(By.id("personal_cmbNation")));
		nationty.selectByVisibleText(nation);
		driver.findElement(By.id("btnSave")).click();
		Assert.assertTrue(true);
		Reporter.log("Edited user’s information");

		System.out.println("Edited user’s information");
	
	}

	@AfterMethod
	public void am()
	{
		//Closing Browser
		driver.close();
		Reporter.log("Browser Closed");

	}
}

