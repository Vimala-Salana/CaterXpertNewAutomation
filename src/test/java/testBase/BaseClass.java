package testBase;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import factory.DriverFactory;
import utilities.ConfigReader;
import utilities.ExcelUtility;

public class BaseClass {

	public String filepath = "src/test/resources/TestData/SalesTestData.xlsx";
	public ConfigReader config = new ConfigReader();
	ExcelUtility excel;
	
	@BeforeTest(alwaysRun = true)
	@Parameters("browser")
	
	public void setUpTest(String browser)
	{
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments( "user-data-dir=C:\\AutomationProfile");
		//driver = new ChromeDriver(options);
			DriverFactory.initDriver(browser);
	        DriverFactory.getDriver().get(config.getProperty("url"));
	}

	
	//@AfterSuite
	  public void tearDown() 
	  { 
		DriverFactory.quitDriver();
	  
	  }
	 
}
