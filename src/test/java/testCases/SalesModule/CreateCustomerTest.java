package testCases.SalesModule;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.CreateCustomerPage;
import pageObjects.CustomerOrPotentialCustomerListPage;
import pageObjects.HambergerMenuPage;
import testBase.BaseClass;
import utilities.ExcelUtility;
import utilities.ReportManager;

public class CreateCustomerTest extends BaseClass {

	ExcelUtility excelUtil;
	private CreateCustomerPage customerPage;
	private BasetoSalesNavigationPage basePage; 
	private HambergerMenuPage hamburgerMenuPage;
	private CustomerOrPotentialCustomerListPage customerListpage;
	Map<String, String> data;
	private String sheetname;
	
	@BeforeMethod
	public void setup() throws IOException
	{
		customerPage = new CreateCustomerPage(DriverFactory.getDriver());
		hamburgerMenuPage = new HambergerMenuPage(DriverFactory.getDriver());
		customerListpage = new CustomerOrPotentialCustomerListPage(DriverFactory.getDriver());
		basePage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		basicLogin();
		
		basePage.salesNewNavigation();
		
		excelUtil = new ExcelUtility(filepath);
		sheetname = "Create Customer";
		data = excelUtil.getMandatoryFieldData(sheetname);
		
		hamburgerMenuPage.navigateToCreateCustomer();
		
		//String hdrCustomerList = customerlistpage.getCustomerOrPotentialCustomerListhdr();
		//Assert.assertEquals(hdrCustomerList, "Customer/Potential Customer List","header mismatch");
		//Thread.sleep(1000);
		customerListpage.ClickNewCustomer();
		
	}

	@Test(priority = 1, groups = {"Regression", "All"})
	public void verifyCreateCustomerWithMandatoryFieldsOnly() throws Exception {

		//Assert.assertEquals(customerPage.getCreateCustomerhdr(), "Create Customer");
		customerPage.fillCustomerMandatoryfields(data); 
		customerPage.clickCreatebtn();
		customerPage.clickContactClose();
		

		// searching customer

		String customernameexl = excelUtil.getCellValue(sheetname, 1, 1);
		System.out.println(excelUtil.getCellValue(sheetname, 1, 1));
		
		customerPage.enterCustomerNameinSearch(customernameexl);
		//System.out.println(customerPage.getCustomerNamefromlst()); // Customer Name from CustomerList
		//System.out.println(customernameexl); // Customer name from Excel
		
		Assert.assertEquals(customerPage.getCustomerNamefromlst().trim(), customernameexl, "Customer Not Found");
		
		ReportManager.pass("Customer is found in Customer List");
		/*
		 * if (customerPage.getCustomerNamefromlst().trim().equalsIgnoreCase(
		 * customernameexl)) // validating ListName and ExcelName {
		 * customerPage.clickCustomerNamefromlst(); }
		 * 
		 * customerPage.clickContactstab();
		 */
	}
}
