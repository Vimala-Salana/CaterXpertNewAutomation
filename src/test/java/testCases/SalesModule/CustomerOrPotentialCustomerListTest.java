package testCases.SalesModule;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.AEDashboardPage;
import pageObjects.CustomerOrPotentialCustomerListPage;
import testBase.BaseClass;
import utilities.ExcelUtility;

public class CustomerOrPotentialCustomerListTest extends BaseClass{
	ExcelUtility excel;
	@Test(priority=4)
	public void CustomerOrPotentialCustomerList() throws InterruptedException, IOException
	{
		AEDashboardPage aepage = new AEDashboardPage(DriverFactory.getDriver());
		aepage.clickhambergerMenu();
		aepage.clickCustomerOrPotentialCustomer();
		CustomerOrPotentialCustomerListPage customerlistpage = new CustomerOrPotentialCustomerListPage(DriverFactory.getDriver());
	//	String hdrCustomerList = customerlistpage.getCustomerOrPotentialCustomerListhdr();
	//	Assert.assertEquals(hdrCustomerList, "Customer/Potential Customer List","header mismatch");
		Thread.sleep(1000);
		//customerlistpage.ClickNewCustomer(); //For New Customer flow
		
	
		
		
	}
}
