package testCases.SalesModule;

import org.testng.Assert;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.ContactListPage;
import pageObjects.HambergerMenuPage;
import testBase.BaseClass;

public class ContactListTest extends BaseClass{

	@Test
	public void validateContactListhdr()
	{
		//HambergerMenuPage hm = new HambergerMenuPage(DriverFactory.getDriver());
		//hm.clickhambergerMenu();
		//hm.clickContcat();
		
		ContactListPage cp = new ContactListPage(DriverFactory.getDriver());
		Assert.assertEquals(cp.getContactListHeader(), "Contact List");
		
		//cp.clickNewContact();  //For New Contact flow
		
	}
}
