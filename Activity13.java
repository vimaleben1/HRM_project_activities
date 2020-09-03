package project;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;


public class Activity13 {


	WebDriver driver;
	WebDriverWait wait;
	File file = new File("src/main/java/project/importData.csv");

	@BeforeMethod
	public void beforeMethod() {
		//Browser initialization

		driver= new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();


	}
	@Test
	@Parameters({"url","username","password"})
	public void act13 (String url,String username, String password) throws IOException, InterruptedException, CsvException 
	{
		//Login
		driver.get(url);
		int z=0;
		Actions acc=new Actions(driver);
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();
		
		// Mouse hover and moving to Element
		WebElement pim5= driver.findElement(By.id("menu_pim_viewPimModule"));
		//pim5.click();
		
		WebElement config= driver.findElement(By.id("menu_pim_Configuration"));
		//config.click();
		Thread.sleep(1000);
		WebElement export= driver.findElement(By.id("menu_admin_pimCsvImport"));
		acc.moveToElement(pim5).build().perform();
		acc.moveToElement(config).build().perform();
		acc.moveToElement(export).click().build().perform();
		//Upload CSV file
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		WebElement upload = driver.findElement(By.xpath("//*[@value='Upload']"));
		wait.until(ExpectedConditions.visibilityOf(upload));
		WebElement fileU=driver.findElement(By.xpath("//*[@name='pimCsvImport[csvFile]']"));
		fileU.sendKeys(file.getAbsolutePath());
		upload.click();
		WebElement h1=driver.findElement(By.id("pimCsvImportHeading"));
		System.out.println(h1.getText());

		//Reading CSV file for validation
		CSVReader reader= new CSVReader(new FileReader("src/main/java/project/importData.csv"));
		List<List<String>> data = new ArrayList<List<String>>();
		List<String[]> content =reader.readAll();
		Iterator<String[]> it= content.iterator();

		while(it.hasNext())
		{
			List<String> rowData = new ArrayList<String>();

			String[] values=it.next();
			for(int i=0;i<values.length;i++)
			{
				{
					rowData.add(values[i]);
				}
			}
			data.add(rowData);
		}
		
		
		// Validation in screen
		driver.findElement(By.id("menu_pim_viewEmployeeList")).click();
		System.out.println(data.size());
		for(z=1;z<data.size();z++)
		{
			WebElement empname= driver.findElement(By.id("empsearch_employee_name_empName"));
			empname.clear();
			empname.sendKeys(data.get(z).get(0));
			driver.findElement(By.id("searchBtn")).click();
			driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);			
			WebElement getText=driver.findElement(By.xpath("//*[@id='resultTable']/tbody/tr/td[3]"));
			Assert.assertEquals(getText.getText(), data.get(z).get(0));
			
			Reporter.log(" Employee "+z+" Data Created and Retrieved with Name: "+data.get(z).get(0));

		}
	}
	
	@AfterMethod
	public void am()
	{
		//Closing browser
		//driver.close();
	}

}

