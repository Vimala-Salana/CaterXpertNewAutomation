package testCases.SalesModule;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.ContactListPage;
import testBase.BaseClass;
import utilities.DataGenerator;
import utilities.ExcelUtility;
import utilities.ReportManager;
import workFlows.ContactFlow;

public class CreateContactTest extends BaseClass {

	ExcelUtility excelUtil;
	private ContactListPage contactListpage;
	private ContactFlow contactFlow;
	Map<String, String> data;
	Map<String, String> contactData;
	private String sheetname;

	@BeforeMethod
	public void setup() {
		contactFlow = new ContactFlow();
		contactListpage = new ContactListPage(DriverFactory.getDriver());

		excelUtil = new ExcelUtility(filepath);
		sheetname = "Create Contact";
		data = excelUtil.getMandatoryFieldData(sheetname);
		DataGenerator dataGenerator = new DataGenerator();
		contactData = dataGenerator.generate(contactJsonPath, "ContactTest");

		contactFlow.navigatetoCreateContact();
	}

	@Test(groups = { "Regression", "All" })
	public void createContact() throws Exception {

		// Assert.assertEquals(contactPage.getContacthdr(), "Create Contact");
		contactFlow.createContact(contactData);

		// String contactfirstnamexl = excelUtil.getCellValue(sheetname, 1, 0);
		// String contactlastnamexl = excelUtil.getCellValue(sheetname, 1, 1);
		String expectedFirstName = contactData.get("First Name");
		String expectedLastName = contactData.get("Last Name");

		String contactname = expectedLastName + ", " + expectedFirstName;
		System.out.println(contactname);

		// System.out.println(contactPage.getContactNamefromList()+"
		// "+contactPage.getContactNamefromList().size());
		// DriverFactory.getDriver().findElement(By.xpath("//span[text()=' event
		// ']")).click();

		/*
		 * for(String contactsName : contactPage.getContactNamefromList()) {
		 * System.out.println(contactsName);
		 * if(contactnamexl.equalsIgnoreCase(contactsName)) { Thread.sleep(1000);
		 * contactPage.clickNewEventIcon(); } else
		 * System.out.println("Contact name not found"); }
		 */

		contactListpage.searchContactName(contactname);

		Assert.assertEquals(contactListpage.getContactNamefromlst().trim(), contactname, "Contact Name not found");

		ReportManager.pass("Contact Created Successfully...");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}
}
