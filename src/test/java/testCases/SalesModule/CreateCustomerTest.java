package testCases.SalesModule;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.CreateCustomerPage;
import testBase.BaseClass;
import utilities.DataGenerator;
import utilities.ExcelUtility;
import utilities.ReportManager;
import workFlows.CustomerFlow;

public class CreateCustomerTest extends BaseClass {

	ExcelUtility excelUtil;
	private CreateCustomerPage customerPage;
	Map<String, String> data;
	private String sheetname;
	private Map<String, String> customerData;
	private CustomerFlow customerFlow;

	@BeforeMethod
	public void setup() {
		customerFlow = new CustomerFlow(DriverFactory.getDriver());
		customerPage = new CreateCustomerPage(DriverFactory.getDriver());

		excelUtil = new ExcelUtility(filepath);
		sheetname = "Create Customer";
		data = excelUtil.getMandatoryFieldData(sheetname);

		DataGenerator dataGenerator = new DataGenerator();
		customerData = dataGenerator.generate(customerJsonPath, "CustomerTest");

		customerFlow.navigateToCreateCustomer();

	}

	@Test(priority = 1, groups = { "Regression", "All" })
	public void verifyCreateCustomerWithMandatoryFieldsOnly() {

		// Assert.assertEquals(customerPage.getCreateCustomerhdr(), "Create Customer");
		customerFlow.createCustomer(customerData);
		customerPage.clickContactClose();

		String expectedCustomerName = customerData.get("Customer Name");
		customerPage.enterCustomerNameinSearch(expectedCustomerName);

		Assert.assertEquals(customerPage.getCustomerNamefromlst().trim(), expectedCustomerName, "Customer Not Found");

		ReportManager.pass("Customer is found in Customer List");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}
}
