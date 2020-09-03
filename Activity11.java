package project;

import java.io.File;
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

public class Activity11 {

	WebDriver driver;
	WebDriverWait wait;
	//File file = new File("/Users/vimal/Desktop/Alchemy.docx");
	
	File file = new File("src/main/java/project/Alchemy.docx");


	@BeforeMethod
	public void beforeMethod() {
		//Browser initialization

		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();

	}
	@Test
	@Parameters({"url","username","password","fname","lname","email","contact","jName","jTitle","jHM"})
	public void act11 (String url,String username, String password, String fname,String lname,String email,String contact,String jName,String jTitle,String jHM) 
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
		// Adding Candidate details
		driver.findElement(By.cssSelector("#menu_recruitment_viewCandidates")).click();
		driver.findElement(By.cssSelector("#btnAdd")).click();

		driver.findElement(By.cssSelector("#addCandidate_firstName")).sendKeys(fname);
		driver.findElement(By.cssSelector("#addCandidate_lastName")).sendKeys(lname);
		driver.findElement(By.cssSelector("#addCandidate_email")).sendKeys(email);
		driver.findElement(By.cssSelector("#addCandidate_contactNo")).sendKeys(contact);
		Select nsw= new Select(driver.findElement(By.cssSelector("#addCandidate_vacancy")));
		nsw.selectByVisibleText(jName);	
		WebElement fileU= driver.findElement(By.cssSelector("#addCandidate_resume"));
		fileU.sendKeys(file.getAbsolutePath());
		driver.findElement(By.cssSelector("#btnSave")).click();
		driver.findElement(By.cssSelector("#btnBack")).click();
		driver.manage().timeouts().implicitlyWait(2000,TimeUnit.SECONDS);
		driver.findElement(By.cssSelector("#menu_recruitment_viewCandidates")).click();

		Select vacsSearch= new Select(driver.findElement(By.cssSelector("#candidateSearch_jobTitle")));
		Select CandSearch=new Select(driver.findElement(By.cssSelector("#candidateSearch_jobVacancy")));
		Select vacHM=new Select(driver.findElement(By.cssSelector("#candidateSearch_hiringManager")));

		vacsSearch.selectByVisibleText(jTitle);
		String concat=fname+" "+lname;
		CandSearch.selectByVisibleText(jName);
		vacHM.selectByVisibleText(jHM);

		driver.findElement(By.cssSelector("#candidateSearch_candidateName")).sendKeys(concat);
		driver.findElement(By.cssSelector("#btnSrch")).click();
		WebElement bName= driver.findElement(By.xpath("//*[@id='resultTable']/tbody/tr/td[2]"));

		Assert.assertEquals(jName, bName.getText());
		Reporter.log("Candidate Added and found Successfully");
	}

	@AfterMethod
	public void am()
	{
		//Closing browser
		driver.close();
	}


}


