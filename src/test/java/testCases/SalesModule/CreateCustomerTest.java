package testCases.SalesModule;

import org.testng.Assert;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.CreateCustomerPage;
import testBase.BaseClass;
import utilities.ExcelUtility;

public class CreateCustomerTest extends BaseClass {

	ExcelUtility excel;

	@Test(priority = 1)
	public void createCustomer() throws Exception {
		excel = new ExcelUtility(filepath);

		String sheetname = "Create Customer";

		CreateCustomerPage cp = new CreateCustomerPage(DriverFactory.getDriver(), filepath, sheetname);

		//Assert.assertEquals(cp.getCreateCustomerhdr(), "Create Customer");

		//cp.fillCustomerMandatoryfields(); 
		Thread.sleep(1000); 
		//cp.clickCreatebtn();

		// searching customer

		excel = new ExcelUtility(filepath);
		String customernameexl = excel.getCellValue(sheetname, 1, 1);
		System.out.println(excel.getCellValue(sheetname, 1, 1));
		Thread.sleep(2000);
		cp.enterCustomerNameinSearch(customernameexl);
		Thread.sleep(1000);
		//System.out.println(cp.getCustomerNamefromlst()); // Customer Name from CustomerList
		//System.out.println(customernameexl); // Customer name from Excel
		Thread.sleep(1000);
		if (cp.getCustomerNamefromlst().trim().equalsIgnoreCase(customernameexl)) // validating ListName and ExcelName
		{
			cp.clickCustomerNamefromlst();
		}

		Thread.sleep(1000);
		cp.clickContactstab();
	}
}
