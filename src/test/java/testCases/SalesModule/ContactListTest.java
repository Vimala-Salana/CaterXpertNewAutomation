package testCases.SalesModule;

import org.testng.Assert;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.AEDashboardPage;
import pageObjects.ContactListPage;
import testBase.BaseClass;

public class ContactListTest extends BaseClass{

	@Test
	public void validateContactListhdr() throws InterruptedException
	{
		AEDashboardPage aepage = new AEDashboardPage(DriverFactory.getDriver());
		aepage.clickhambergerMenu();
		aepage.clickContcat();
		
		ContactListPage cp = new ContactListPage(DriverFactory.getDriver());
		Assert.assertEquals(cp.getContactListHeader(), "Contact List");
		
		cp.clickNewContact();  //For New Contact flow
		
	}
}
