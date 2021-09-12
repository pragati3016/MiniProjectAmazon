package test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestAmazon {

	//Declarations
	private WebDriver driver;
	private String baseURL;
	private String Username;
	private String Password;
	static String fileName;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		
		Properties prop = new Properties();
		
		//Login Details
		prop.load(new FileInputStream(".\\Configuration\\LoginDetails.txt"));
		baseURL=prop.getProperty("URL");
		Username = prop.getProperty("Username");
		Password = prop.getProperty("Password");
		
		//Chrome Driver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Pragati\\eclipse\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		
		//Firefox Driver
		//System.setProperty("webdriver.gecko.driver", "C:\\Users\\Pragati\\eclipse\\geckodriver-v0.29.1-win64\\geckodriver.exe");
		//driver = new FirefoxDriver();
		
		//Maximize Window
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void test() throws IOException {
		
		driver.get(baseURL);
		
		//Login Activity
		driver.findElement(By.xpath(".//*[@id='nav-link-accountList-nav-line-1']")).click();
		
		//Entering Username
		driver.findElement(By.xpath(".//*[@id=\'ap_email\']")).sendKeys(Username);
		driver.findElement(By.xpath(".//*[@id=\'continue\']")).click();
		
		//Entering Password
		driver.findElement(By.xpath(".//*[@id=\'ap_password\']")).sendKeys(Password);
		driver.findElement(By.xpath(".//*[@id=\'signInSubmit\']")).click();
		
		//Naming Text File with the Current Date and Time 
		fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//Declarations
		String ModuleName = "Login";
		String Result = "Pass";
		String Comment = "User Logged In Successfully";
		String item1 = null;
		String item2 = null;
		String item3 = null;
		String item4 = null;
		String item5 = null;
		String ary[];
		ary = new String[100];
		int k = 1;
		
		//Function Call to Write Login Module Details to Text File
		writeText(ModuleName, Result, Comment, item1, item2, item3, item4, item5, ary[k]);
		
		//Obtaining Bytes from the Excel File
		FileInputStream fs = new FileInputStream(new File("C:\\Users\\Pragati\\eclipse-workspace\\MiniProject\\DataPool\\List.xlsx"));
		
		//Creating Workbook Instance that refers to .xlsx File
		XSSFWorkbook hb = new XSSFWorkbook(fs);
		
		//Creating a Sheet Object to retrieve Object
		XSSFSheet hs = hb.getSheetAt(0);
		
		//While Loop to Access Excel File Elements
		while(k < 4)
		{	
			//Accessing Excel File Elements
			Cell c = hb.getSheetAt(0).getRow(k).getCell(1);
			
			//Conversion of Cell Data to String 
	        ary[k] = c.toString();	
	        if(ary[k] != null)
	        {
	        	//Clearing Search Box
	        	driver.findElement(By.xpath(".//*[@id=\'twotabsearchtextbox\']")).clear();
	        	
	        	//Sending Search Query from Excel File to Amazon Search Box
	        	driver.findElement(By.xpath(".//*[@id=\'twotabsearchtextbox\']")).sendKeys(ary[k]);
	        	driver.findElement(By.xpath(".//*[@id=\'nav-search-submit-button\']")).click();
	        	
	        	//Get the Text of Searched Items from Amazon's Web-site and Store in Variables
	        	item1 = driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[2]/div/span/div/div/div")).getText();
	        	System.out.println(item1);
	        	System.out.println("");
	        	item2 = driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[3]/div/span/div/div/div")).getText();
	        	System.out.println(item2);
	        	System.out.println("");
	        	item3 = driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[4]/div/span/div")).getText();
	        	System.out.println(item3);
	        	System.out.println("");
	        	item4 = driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[5]/div/span/div")).getText();
	        	System.out.println(item4);
	        	System.out.println("");
	        	item5 = driver.findElement(By.xpath(".//*[@id=\'search\']/div[1]/div[1]/div/span[3]/div[2]/div[6]/div/span/div")).getText();
	        	System.out.println(item5);

	        }
	        
	        //String Declarations
	        String ModuleName1 = "Search";
			String Result1 = "Pass";
			String Comment1 = "Items Searched Successfully";
			
			//Function Call to Write Search Results to Text File
			writeText(ModuleName1, Result1, Comment1, item1, item2, item3, item4, item5, ary[k]);
			
			//Array Index Increment
	        k++;
		}
	}
	
	public static void writeText(String ModuleName, String Result, String Comment, String item1, String item2, String item3, String item4, String item5, String ary) throws IOException {
	
		//FileWriter to Write to the Text File
		FileWriter fileWriter = new FileWriter(".\\DataPool\\"+fileName,true);
		
		//Writing Search Results to Text File
		fileWriter.write("\n"+ModuleName +" - "+ Result +" - "+ Comment + "\n" + ary + "\n\nResult 1 : "+ item1 + "\n\nResult 2 : " + item2 + "\n\nResult 3 : " + item3 + "\n\nResult 4 : " + item4 + "\n\nResult 5 : " + item5 + "\n");
		
		//Closing File Writer
		fileWriter.close();
	}

	@After
	public void tearDown(){
		
		//Dispose Browser Window
		driver.close();
	
	}
}

