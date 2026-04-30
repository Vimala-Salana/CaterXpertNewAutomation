package testBase;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import factory.DriverFactory;
import utilities.ConfigReader;
import utilities.ExcelUtility;

public class BaseClass {

	public String filepath = "src/test/resources/TestData/SalesTestData.xlsx";
	public ConfigReader config = new ConfigReader();
	ExcelUtility excel;
	
	@Parameters("browser")
	@BeforeSuite
	public void setUpTest(String browser)
	{
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments( "user-data-dir=C:\\AutomationProfile");
		//driver = new ChromeDriver(options);
			DriverFactory.initDriver(browser);
	        DriverFactory.getDriver().get(config.getProperty("url"));
	}

	
	@AfterSuite
	  public void tearDown() 
	  { 
		DriverFactory.quitDriver();
	  
	  }
	 
}
