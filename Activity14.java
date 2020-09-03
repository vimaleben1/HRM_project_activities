package project;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvException;

public class Activity14 {

	WebDriver driver;
	WebDriverWait wait;
	File file = new File("src/main/java/project/Excel.xlsx");
	
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
		Activity14 srcExcel = new Activity14();
		List<List<String>> data = srcExcel.readExcel(file);
		//	List<String> row;
		System.out.println(data.size());
		//Open the browser
		driver.get(url);
		WebElement lUsername= driver.findElement(By.cssSelector("#txtUsername"));
		WebElement lPassword= driver.findElement(By.cssSelector("#txtPassword"));
		lUsername.sendKeys(username);
		lPassword.sendKeys(password);
		driver.findElement(By.cssSelector("#btnLogin")).click();	
		driver.findElement(By.cssSelector("#menu_recruitment_viewRecruitmentModule")).click();
		driver.findElement(By.cssSelector("#menu_recruitment_viewJobVacancy")).click();
		//Find the input fields and enter text
		for(int i=0;i<data.size();i++)
		{
			driver.findElement(By.cssSelector("#btnAdd")).click();		
			Select jt= new Select(driver.findElement(By.cssSelector("#addJobVacancy_jobTitle")));
			jt.selectByVisibleText(data.get(i).get(0));
			driver.findElement(By.cssSelector("#addJobVacancy_name")).sendKeys(data.get(i).get(1));
			driver.findElement(By.cssSelector("#addJobVacancy_hiringManager")).sendKeys(data.get(i).get(2));
			driver.findElement(By.cssSelector("#addJobVacancy_noOfPositions")).sendKeys(data.get(i).get(3));
			driver.findElement(By.cssSelector("#addJobVacancy_description")).sendKeys(data.get(i).get(4));		
			driver.findElement(By.cssSelector(".savebutton")).click();
			driver.findElement(By.cssSelector("#btnBack")).click();
		}
		System.out.println("Data Added Successfully");
		driver.findElement(By.cssSelector("#menu_recruitment_viewJobVacancy")).click();
		for(int j=0;j<data.size();j++)
		{

			Select selv= new Select(driver.findElement(By.cssSelector("#vacancySearch_jobTitle")));
			selv.selectByVisibleText(data.get(j).get(0));
			
			Select cs=new Select(driver.findElement(By.cssSelector("#vacancySearch_jobVacancy")));
			cs.selectByVisibleText(data.get(j).get(1));
			Select hm= new Select(driver.findElement(By.cssSelector("#vacancySearch_hiringManager")));
			hm.selectByVisibleText(data.get(j).get(2));
			driver.findElement(By.cssSelector("#btnSrch")).click();
			WebElement tt= driver.findElement(By.xpath("//*[@id='resultTable']/tbody/tr/td[2]"));
			Assert.assertEquals(tt.getText(), data.get(j).get(1));

		}
		Reporter.log("All Excel datas are created/validated");

	}


	public List<List<String>> readExcel(File filePath) {
		List<List<String>> data = new ArrayList<List<String>>();
		try {
			FileInputStream file = new FileInputStream(filePath);

			//Create Workbook instance holding reference to Excel file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				//Temp variable
				List<String> rowData = new ArrayList<String>();
				Row row = rowIterator.next();

				//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if(row.getLastCellNum() == 5) {

						//Store row data
						//rowData.add(cell.getStringCellValue());

						if(cell.getCellType()==CellType.STRING) 
							rowData.add(cell.getStringCellValue()); 
						else if(cell.getCellType()==CellType.NUMERIC) 
							rowData.add(String.valueOf(cell.getNumericCellValue()));	
					}
				}
				//Store row data in List
				data.add(rowData);
			}
			file.close();
			workbook.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;

	}

}


