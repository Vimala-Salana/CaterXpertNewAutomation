package testBase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import factory.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import pageObjects.BaseToSalesNavigationPage;
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
	 */
	@BeforeSuite(alwaysRun = true)
	@Parameters("browser")
	@Step("Base Class Login - Once")
	@Severity(SeverityLevel.BLOCKER)
	public void loginOnce(@Optional("chrome") String browser) {

		DriverFactory.initDriver(browser);
		driver = DriverFactory.getDriver();

		driver.get(config.getUrl());

		LoginPage login = new LoginPage(driver);

		login.login(config.getCaterId(), config.getUserId(), config.getPassword());

		Allure.step("Login Once Successfull");

		// Navigate to Sales New
		BaseToSalesNavigationPage bp = new BaseToSalesNavigationPage(driver);

		loginId = bp.salesNewNavigation();
		currentUrl = bp.getSalesUrl();
		Allure.step("Sales New Navigation Successfull");

		// System.out.println("Current URL: " + currentUrl);

		DriverFactory.quitDriver();

	}

	/**
	 * Create a new browser for every test and restore the authenticated cookies.
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public void setUpTest(@Optional("chrome") String browser) {

		DriverFactory.initDriver(browser);
		driver = DriverFactory.getDriver();

		// System.out.println(currentUrl);

		driver.get(currentUrl);
		Allure.step("Navigated to Sales New SucessFully");

		// System.out.println("URL after launching captured URL = " +
		// driver.getCurrentUrl());
	}
}
