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
	protected String env;
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
			env = System.getProperty("env", config.getProperty("default.env"));
			String url = System.getProperty("url", config.getProperty(env + ".url"));
	        DriverFactory.getDriver().get(url);
	}
	  
	  public void basicLogin()
	  {
		  LoginPage login = new LoginPage(DriverFactory.getDriver());
		  String caterid = System.getProperty("caterid", config.getProperty(env + ".caterid"));
		  String userid = System.getProperty("userid",config.getProperty(env+".userid"));
		  String password = System.getProperty("password",config.getProperty(env+".password"));
		  
		  login.login(caterid,userid,password); 
	  } 	
	 
	  @AfterMethod
	  public void tearDown() 
	  { 
		  DriverFactory.quitDriver();
	  
	  }
}
