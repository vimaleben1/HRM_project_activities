package project;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Activity10 {

	WebDriver driver;
	WebDriverWait wait;

	@BeforeMethod
	public void beforeMethod() {
		//Browser initialization

		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();

	}
	@Test
	@Parameters({"url","username","password","jTitle","jName","jHM","pos","vacdes"})
	public void act10 (String url,String username, String password, String jTitle,String jName,String jHM,String pos,String vacdes) 
	{
		driver.get(url);
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();
		driver.manage().timeouts().implicitlyWait(2000,TimeUnit.SECONDS);
		WebElement recurit= driver.findElement(By.id("menu_recruitment_viewRecruitmentModule"));
		recurit.click();

		// Adding Vacancy
		WebElement vacancy= driver.findElement(By.id("menu_recruitment_viewJobVacancy"));
		vacancy.click();
		driver.findElement(By.id("btnAdd")).click();
		Select jt= new Select(driver.findElement(By.cssSelector("#addJobVacancy_jobTitle")));
		jt.selectByVisibleText(jTitle);
		driver.findElement(By.cssSelector("#addJobVacancy_name")).sendKeys(jName);
		driver.findElement(By.cssSelector("#addJobVacancy_hiringManager")).sendKeys(jHM);
		driver.findElement(By.cssSelector("#addJobVacancy_noOfPositions")).sendKeys(pos);
		driver.findElement(By.cssSelector("#addJobVacancy_description")).sendKeys(vacdes);
		driver.findElement(By.cssSelector(".savebutton")).click();

		driver.findElement(By.cssSelector("#btnBack")).click();

		//Searching Vacancy
		Select sjt= new Select(driver.findElement(By.cssSelector("#vacancySearch_jobTitle")));
		sjt.selectByVisibleText(jTitle);
		Select sjv=new Select(driver.findElement(By.cssSelector("#vacancySearch_jobVacancy")));
		sjv.selectByVisibleText(jName);
		Select hmn=new Select(driver.findElement(By.cssSelector("#vacancySearch_hiringManager")));
		hmn.selectByVisibleText(jHM);		
		driver.findElement(By.cssSelector("#btnSrch")).click();

		// validation of data
		WebElement aName= driver.findElement(By.xpath("//*[@id='resultTable']/tbody/tr/td[2]"));

		Assert.assertEquals(jName, aName.getText());
		Reporter.log("Candidate applied success");
	}

	@AfterMethod
	public void am()
	{
		//Closing browser
		driver.close();
		Reporter.log("Browser closed");

	}

}
