package testCases.SalesModule;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import pageObjects.AEDashboardPage;
import pageObjects.CreateEventPage;
import testBase.BaseClass;

public class CreateEventTest extends BaseClass{

	@Test
	public void createEvent(ITestContext context) throws Exception
	{
		String sheetname = "Create Event";
		//driver.findElement(By.xpath("//button[text()='OK']")).click();
		AEDashboardPage aepage = new AEDashboardPage(driver); 
		aepage.clickhambergerMenu();
		aepage.clickCreateEvent();


		CreateEventPage eventpage = new CreateEventPage(driver,filepath,sheetname);
		//	Thread.sleep(2000);//Add explicit wait


		Assert.assertEquals(eventpage.getCreateEventhdr(), "Create Event");
		//validating CreateEvent header
		eventpage.fillEventMandatoryfields(); //filling all mandatory fields
		eventpage.clickCreatebtn();
		Thread.sleep(1000); 
		boolean constraintExists = eventpage.eventConstraints(); 
		String eventNo = eventpage.getEventNo(); 
		context.setAttribute("eventNo", eventNo); //To use Event No in other classes
		eventpage.clickClosebtn();
		System.out.println("Event No : "+eventNo);
		
		if(constraintExists)
		{
		aepage.clickhambergerMenu();
		aepage.clickApprovals();
		eventpage.ApproveEventConstraints(constraintExists,eventNo);
		}
	}
}
