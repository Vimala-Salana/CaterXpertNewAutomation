package testCases.SalesModule;
import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.ContactListPage;
import pageObjects.CreateContactPage;
import pageObjects.CreateCustomerPage;
import pageObjects.CustomerOrPotentialCustomerListPage;
import pageObjects.HambergerMenuPage;
import testBase.BaseClass;
import utilities.ExcelUtility;
import utilities.JsonUtil;
import utilities.ReportManager;
import workFlows.ContactFlow;


public class CreateContactTest extends BaseClass{

	ExcelUtility excelUtil;
	private CreateContactPage contactPage;
	private BasetoSalesNavigationPage basePage; 
	private HambergerMenuPage hamburgerMenuPage;
	private ContactListPage contactListpage;
	private ContactFlow contactFlow;
	Map<String, String> data;
	Map<String, String> contactData;
	private String sheetname;
	
	@BeforeMethod
	public void setup() throws IOException
	{
		basePage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		contactFlow = new ContactFlow();
		contactListpage = new ContactListPage(DriverFactory.getDriver());
		basicLogin();
		
		basePage.salesNewNavigation();
		
		excelUtil = new ExcelUtility(filepath);
		sheetname = "Create Contact";
		data = excelUtil.getMandatoryFieldData(sheetname);
		JsonUtil jsonUtil = new JsonUtil("src/test/resources/TestData/Contact.json");
		contactData = jsonUtil.getData();
		contactFlow.navigatetoCreateContact();
	}
	
	@Test(groups = {"Regression", "All"})
	public void createContact() throws Exception
	{
		
		//Assert.assertEquals(contactPage.getContacthdr(), "Create Contact");
		contactFlow.createContact(contactData);
	
		String contactfirstnamexl = excelUtil.getCellValue(sheetname, 1, 0);
		String contactlastnamexl = excelUtil.getCellValue(sheetname, 1, 1);

		String contactnamexl = contactlastnamexl+", "+contactfirstnamexl;
		System.out.println(contactnamexl);
		
		//System.out.println(contactPage.getContactNamefromList()+" "+contactPage.getContactNamefromList().size());
		//DriverFactory.getDriver().findElement(By.xpath("//span[text()=' event ']")).click();
		
		/*for(String contactsName : contactPage.getContactNamefromList())
		{
			System.out.println(contactsName);
			if(contactnamexl.equalsIgnoreCase(contactsName)) {
				Thread.sleep(1000);
				contactPage.clickNewEventIcon();
			}
			else
				System.out.println("Contact name not found");
		} */
		
		contactListpage.searchContactName(contactnamexl);
		
		Assert.assertEquals(contactListpage.getContactNamefromlst().trim(), contactnamexl,"Contact Name not found");
		
		ReportManager.pass("Contact Created Successfully...");
	}
}
