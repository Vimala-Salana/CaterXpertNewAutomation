package workFlows;

import java.util.Map;

import factory.DriverFactory;
import pageObjects.ContactListPage;
import pageObjects.CreateContactPage;
import pageObjects.HambergerMenuPage;
import utilities.ExcelUtility;

public class ContactFlow {
	ExcelUtility excelUtil;
	private CreateContactPage contactPage;
	private HambergerMenuPage hamburgerMenuPage;
	private ContactListPage contactListpage;

	public ContactFlow()
	{
		contactPage = new CreateContactPage(DriverFactory.getDriver());
		hamburgerMenuPage = new HambergerMenuPage(DriverFactory.getDriver());
		contactListpage = new ContactListPage(DriverFactory.getDriver());
	}
	
	public void navigatetoCreateContact()
	{
		hamburgerMenuPage.clickhambergerMenu();
		hamburgerMenuPage.clickCustomerOrPotentialCustomerdrp();
		hamburgerMenuPage.clickContcat();
		contactListpage.clickNewContact();
	}
	
	public void createContact(Map<String, String> data)
	{
		contactPage.fillContactMandatoryfileds(data);
		contactPage.clickCreatebtn();
	}

}
