package testCases.SalesModule;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import components.HambergerMenuPage;
import components.HeaderPage;
import factory.DriverFactory;
import pageObjects.CreateEventPage;
import testBase.BaseClass;

public class CreateEventTest extends BaseClass{
	
	
	@Test(groups = {"Regression", "EventCreationDirect"})
	public void createEventDirect()
	{
		HambergerMenuPage hamberger = new HambergerMenuPage(DriverFactory.getDriver()); 
		HeaderPage header = new HeaderPage(DriverFactory.getDriver());
		header.clickhambergerMenu();
		hamberger.clickCreateEvent();
	}
	 
	@Test(groups = {"Regression", "All"})
	public void createEventFromContact(ITestContext context) throws Exception
	{
		
		String sheetname = "Create Event";
		//driver.findElement(By.xpath("//button[text()='OK']")).click();
		CreateEventPage eventpage = new CreateEventPage(DriverFactory.getDriver(), filepath,sheetname);
		//validating CreateEvent header
		Assert.assertEquals(eventpage.getCreateEventhdr(), "Create Event");
		
		eventpage.fillEventMandatoryfields(); //filling all mandatory fields
		eventpage.clickCreatebtn();
		
		boolean constraintExists = eventpage.eventConstraints(); 
		String eventNo = eventpage.getEventNo(); 
		context.setAttribute("eventNo", eventNo); //To use Event Number in other classes
		eventpage.clickClosebtn();
		System.out.println("Event No : "+eventNo);
		HambergerMenuPage hamberger = new HambergerMenuPage(DriverFactory.getDriver()); 
		HeaderPage header = new HeaderPage(DriverFactory.getDriver());
		if(constraintExists)
		{
			header.clickhambergerMenu();
			hamberger.clickApprovals();
		eventpage.ApproveEventConstraints(constraintExists,eventNo);
		}
	}

}
