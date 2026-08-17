package testCases.SalesModule;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.CreateCustomerPage;
import testBase.BaseClass;
import utilities.DataGenerator;
import utilities.ExcelUtility;
import utilities.ReportManager;
import workFlows.CustomerFlow;

public class CreateCustomerTest extends BaseClass {

	ExcelUtility excelUtil;
	private CreateCustomerPage customerPage;
	private BasetoSalesNavigationPage basePage; 
	Map<String, String> data;
	private String sheetname;
	private Map<String, String> customerData;
	private CustomerFlow customerFlow;
	
	@BeforeMethod
	public void setup()
	{
		customerFlow = new CustomerFlow(DriverFactory.getDriver());
		customerPage = new CreateCustomerPage(DriverFactory.getDriver());
		
		excelUtil = new ExcelUtility(filepath);
		sheetname = "Create Customer";
		data = excelUtil.getMandatoryFieldData(sheetname);
		
		DataGenerator dataGenerator = new DataGenerator();
		customerData = dataGenerator.generate(customerJsonPath,"CustomerTest");
		
		customerFlow.navigateToCreateCustomer();
		
		//String hdrCustomerList = customerlistpage.getCustomerOrPotentialCustomerListhdr();
		//Assert.assertEquals(hdrCustomerList, "Customer/Potential Customer List","header mismatch");
		//Thread.sleep(1000);
		
	}

	@Test(priority = 1, groups = {"Regression", "All"})
	public void verifyCreateCustomerWithMandatoryFieldsOnly() {

		//Assert.assertEquals(customerPage.getCreateCustomerhdr(), "Create Customer");
		customerFlow.createCustomer(customerData);
		customerPage.clickContactClose();
		
		// searching customer

		String customernameexl = excelUtil.getCellValue(sheetname, 1, 1);
		System.out.println(excelUtil.getCellValue(sheetname, 1, 1));
		
		String expectedCustomerName = customerData.get("Customer Name");
		customerPage.enterCustomerNameinSearch(expectedCustomerName);
		//System.out.println(customerPage.getCustomerNamefromlst()); // Customer Name from CustomerList
		//System.out.println(customernameexl); // Customer name from Excel
		
		Assert.assertEquals(customerPage.getCustomerNamefromlst().trim(), expectedCustomerName, "Customer Not Found");
		
		ReportManager.pass("Customer is found in Customer List");
		/*
		 * if (customerPage.getCustomerNamefromlst().trim().equalsIgnoreCase(
		 * customernameexl)) // validating ListName and ExcelName {
		 * customerPage.clickCustomerNamefromlst(); }
		 * 
		 * customerPage.clickContactstab();
		 */
	}
	
	@AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
