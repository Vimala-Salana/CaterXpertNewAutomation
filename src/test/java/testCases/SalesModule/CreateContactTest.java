package testCases.SalesModule;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.CreateContactPage;
import testBase.BaseClass;
import utilities.ExcelUtility;


public class CreateContactTest extends BaseClass{

	ExcelUtility excel;
	@Test
	public void createContcat() throws Exception
	{
		String sheetname = "Create Contact";
		CreateContactPage contactPage = new CreateContactPage(DriverFactory.getDriver(),filepath,sheetname);
		//Assert.assertEquals(contactPage.getContacthdr(), "Create Contact");
		contactPage.fillContactMandatoryfileds();
		contactPage.clickCreatebtn();
		excel = new ExcelUtility(filepath);
		String contactfirstnamexl = excel.getCellValue(sheetname, 1, 0);
		String contactlastnamexl = excel.getCellValue(sheetname, 1, 1);

		String contactnamexl = contactlastnamexl+", "+contactfirstnamexl;
		System.out.println(contactnamexl);
		
		//Need to optimize
		Thread.sleep(2000);
		System.out.println(contactPage.getContactNamefromList()+" "+contactPage.getContactNamefromList().size());
		//DriverFactory.getDriver().findElement(By.xpath("//span[text()=' event ']")).click();
		for(String contactsName : contactPage.getContactNamefromList())
		{
			System.out.println(contactsName);
			if(contactnamexl.equalsIgnoreCase(contactsName)) {
				Thread.sleep(1000);
				contactPage.clickNewEventIcon();
				//DriverFactory.getDriver().findElement(By.xpath("//span[text()=' event ']")).click();
				//contactPage.clickNewEventIcon();
			}
			else
				System.out.println("Contact name not found");
		}
	}
}
