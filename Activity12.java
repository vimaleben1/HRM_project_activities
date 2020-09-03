package project;

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

public class Activity12 {


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
	@Parameters({"url","username","password"})
	public void act12 (String url,String username, String password) throws IOException, CsvException, InterruptedException 
	{
		//Login
		driver.get(url);
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		int z=0;
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();
		driver.manage().timeouts().implicitlyWait(2000,TimeUnit.SECONDS);
		WebElement pim5= driver.findElement(By.id("menu_pim_viewPimModule"));
		pim5.click();
		driver.findElement(By.id("menu_pim_addEmployee")).click();
		//Reading CSV file
		CSVReader reader= new CSVReader(new FileReader("src/main/java/project/test.csv"));
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
		System.out.println("DATA"+data);
		//using CSV values to input fields
		for(z=0;z<data.size();z++)
		{
			driver.findElement(By.cssSelector("#firstName")).sendKeys(data.get(z).get(0));
			driver.findElement(By.cssSelector("#lastName")).sendKeys(data.get(z).get(1));
			WebElement chkBox= driver.findElement(By.cssSelector("#chkLogin"));
			chkBox.click();
			driver.findElement(By.cssSelector("#user_name")).sendKeys(data.get(z).get(2));
			driver.findElement(By.cssSelector("#user_password")).sendKeys(data.get(z).get(3));
			driver.findElement(By.cssSelector("#re_password")).sendKeys(data.get(z).get(4));
			driver.findElement(By.cssSelector("#btnSave")).click();
			WebElement edit=driver.findElement(By.xpath("//*[@value='Edit']"));
			wait.until(ExpectedConditions.visibilityOf(edit));
			driver.findElement(By.cssSelector("#menu_pim_viewEmployeeList")).click();
			driver.findElement(By.cssSelector("#empsearch_employee_name_empName")).sendKeys(data.get(z).get(0));
			driver.findElement(By.cssSelector("#searchBtn")).click();
			WebElement value =driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[3]"));
			String Actualt= value.getText();
			Assert.assertEquals(Actualt, data.get(z).get(0));

			driver.findElement(By.id("menu_pim_addEmployee")).click();		
		}
		Reporter.log("All records from CSV inserted");
	}
	@AfterMethod
	public void am()
	{
		//Closing browser
		driver.close();
	}

}