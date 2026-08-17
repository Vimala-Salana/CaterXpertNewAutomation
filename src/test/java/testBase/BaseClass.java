package testBase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.LoginPage;
import utilities.ConfigReader;
import utilities.ExcelUtility;

public class BaseClass {

	public String filepath = "src/test/resources/TestData/SalesTestData.xlsx";

	public String customerJsonPath = "src/test/resources/TestData/Customer.json";
	public String contactJsonPath = "src/test/resources/TestData/Contact.json";
	public String eventJsonPath = "src/test/resources/TestData/Event.json";

	public static String currentUrl;
	public static String loginId;
	WebDriver driver;

	public ConfigReader config = new ConfigReader();
	ExcelUtility excel;

	protected LoginPage loginPage;

	/**
	 * Login ONCE for the entire TestNG suite.
	 * 
	 * @throws InterruptedException
	 */
	@BeforeSuite(alwaysRun = true)
	@Parameters("browser")
	public void loginOnce(String browser) throws InterruptedException {

		System.out.println("========== SUITE LOGIN START ==========");

		DriverFactory.initDriver(browser);
		driver = DriverFactory.getDriver();

		driver.get(config.getUrl());

		LoginPage login = new LoginPage(driver);

		login.login(config.getCaterId(), config.getUserId(), config.getPassword());

		// Navigate to Sales New
		BasetoSalesNavigationPage bp = new BasetoSalesNavigationPage(driver);

		loginId = bp.salesNewNavigation();
		currentUrl = bp.getSalesUrl();

		System.out.println("Current URL: " + currentUrl);

		DriverFactory.quitDriver();

	}

	/**
	 * Create a new browser for every test and restore the authenticated cookies.
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public void setUpTest(String browser) {

		DriverFactory.initDriver(browser);
		driver = DriverFactory.getDriver();

		System.out.println(currentUrl);

		driver.get(currentUrl);

		System.out.println("URL after launching captured URL = " + driver.getCurrentUrl());
	}
}
