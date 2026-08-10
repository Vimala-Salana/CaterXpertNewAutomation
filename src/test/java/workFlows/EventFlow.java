package workFlows;


import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import components.HamburgerMenuPage;
import components.HeaderPage;
import pageObjects.CreateEventPage;

public class EventFlow {

	private HamburgerMenuPage hambergerMenuPage;
	private HeaderPage headerPage;
	CreateEventPage eventPage;
	public EventFlow(WebDriver driver)
	{
		hambergerMenuPage = new HamburgerMenuPage(driver);
		headerPage = new HeaderPage(driver);
		eventPage = new CreateEventPage(driver);
	}

	public void navigatetoCreateEvent()
	{
		headerPage.clickhambergerMenu();
		hambergerMenuPage.clickCreateEvent();
	}

	public String createEvent(Map<String, String> data) throws Exception
	{
		Assert.assertEquals(eventPage.getCreateEventhdr(), "Create Event");

		eventPage.fillEventMandatoryfields(data); //filling all mandatory fields
		eventPage.clickCreatebtn();
		
		boolean constraintExists = eventPage.eventConstraints(); 
		String eventNo = eventPage.getEventNo(); 
		eventPage.clickClosebtn();
		System.out.println("Event No : "+eventNo);
		if(constraintExists)
		{
			headerPage.clickhambergerMenu();
			hambergerMenuPage.clickApprovals();
			eventPage.ApproveEventConstraints(eventNo);
		}
		return eventNo;
	}
	
	public String createEventfromEventPage(Map<String, String> data) throws Exception
	{
		navigatetoCreateEvent();
		return createEvent(data);
	}
}
