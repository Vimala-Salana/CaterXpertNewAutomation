package testBase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import factory.DriverFactory;
import utilities.ConfigReader;
import utilities.ExcelUtility;

public class BaseClass {

	//protected WebDriver driver;
	public String filepath = "src/test/resources/TestData/SalesTestData.xlsx";
	public ConfigReader config = new ConfigReader();
	ExcelUtility excel;
	protected String env;
	
	@BeforeTest(alwaysRun = true)
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

	
	//@AfterSuite
	  public void tearDown() 
	  { 
		DriverFactory.quitDriver();
	  
	  }
	 
}
