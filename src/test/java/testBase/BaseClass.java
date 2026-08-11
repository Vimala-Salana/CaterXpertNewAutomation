package testBase;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import factory.DriverFactory;
import pageObjects.LoginPage;
import utilities.ConfigReader;
import utilities.ExcelUtility;

public class BaseClass {

	//protected WebDriver driver;
	public String filepath = "src/test/resources/TestData/SalesTestData.xlsx";
	public ConfigReader config = new ConfigReader();
	ExcelUtility excel;
	protected LoginPage loginPage;
	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	
	public void setUpTest(String browser)
	{
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments( "user-data-dir=C:\\AutomationProfile");
		//driver = new ChromeDriver(options);
			DriverFactory.initDriver(browser);
			//driver = DriverFactory.getDriver();
	        DriverFactory.getDriver().get(config.getUrl());
	}
	  
	  public void basicLogin()
	  {
		  LoginPage login = new LoginPage(DriverFactory.getDriver());
		  String caterid = config.getCaterId();
		  String userid = config.getUserId();
		  String password = config.getPassword();
		  
		  login.login(caterid,userid,password); 
	  } 	
	 
	  @AfterMethod
	  public void tearDown() 
	  { 
		  DriverFactory.quitDriver();
	  
	  }
}
