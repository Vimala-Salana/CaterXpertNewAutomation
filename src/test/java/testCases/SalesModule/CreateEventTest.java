package testCases.SalesModule;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.CreateEventPage;
import pageObjects.EventListingPage;
import pageObjects.HambergerMenuPage;
import testBase.BaseClass;

public class CreateEventTest extends BaseClass{

	@Test
	public void createEvent(ITestContext context) throws Exception
	{
		String sheetname = "Create Event";
		//driver.findElement(By.xpath("//button[text()='OK']")).click();
		HambergerMenuPage hamberger = new HambergerMenuPage(DriverFactory.getDriver()); 
		hamberger.clickhambergerMenu();
		hamberger.clickCreateEvent();


		CreateEventPage eventpage = new CreateEventPage(DriverFactory.getDriver(),filepath,sheetname);

		//validating CreateEvent header
		Assert.assertEquals(eventpage.getCreateEventhdr(), "Create Event");
		
		eventpage.fillEventMandatoryfields(); //filling all mandatory fields
		eventpage.clickCreatebtn();
		
		boolean constraintExists = eventpage.eventConstraints(); 
		String eventNo = eventpage.getEventNo(); 
		context.setAttribute("eventNo", eventNo); //To use Event Number in other classes
		eventpage.clickClosebtn();
		System.out.println("Event No : "+eventNo);
		
		if(constraintExists)
		{
			hamberger.clickhambergerMenu();
			hamberger.clickApprovals();
		eventpage.ApproveEventConstraints(constraintExists,eventNo);
		}
	}

}
