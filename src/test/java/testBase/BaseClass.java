package testBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeSuite;
import utilities.ConfigReader;
import utilities.ExcelUtility;

public class BaseClass {

	public String filepath = "src/test/resources/TestData/SalesTestData.xlsx";
	public static WebDriver driver;
	public ConfigReader config = new ConfigReader();
	ExcelUtility excel;
	@BeforeSuite
	/*
	 * @Parameters(value= {"browser"}) public void setUpTest(String browser) {
	 * if(browser.equals("chrome")) { // WebDriverManager.chromedriver().setup();
	 * driver = new ChromeDriver(); }else if(browser.equals("firefox")) { //
	 * WebDriverManager.firefoxdriver().setup(); driver = new FirefoxDriver(); }else
	 * { System.out.println("No Browser is defined in testng.xml file"); }
	 */

	public void setUpTest()
	{
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments( "user-data-dir=C:\\AutomationProfile");
		//driver = new ChromeDriver(options);
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		//driver.manage().deleteAllCookies();
		//driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(config.getProperty("url"));

	}

	/*
	 * @AfterTest public void tearDown() { driver.quit();
	 * 
	 * }
	 */
}
