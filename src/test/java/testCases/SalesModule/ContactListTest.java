package testCases.SalesModule;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AEDashboardPage;
import pageObjects.ContactListPage;
import testBase.BaseClass;

public class ContactListTest extends BaseClass{

	@Test
	public void validateContactListhdr() throws InterruptedException
	{
		AEDashboardPage aepage = new AEDashboardPage(driver);
		aepage.clickhambergerMenu();
		aepage.clickContcat();
		
		ContactListPage cp = new ContactListPage(driver);
		Assert.assertEquals(cp.getCreateContacthdr(), "Contact List");
		
		cp.clickNewContact();  //For New Contact flow
		
	}
}
