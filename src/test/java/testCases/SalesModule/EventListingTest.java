package testCases.SalesModule;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.AEDashboardPage;
import pageObjects.EventListingPage;
import testBase.BaseClass;

public class EventListingTest extends BaseClass{

	@Test
	public void eventListing()
	{
		AEDashboardPage aePage = new AEDashboardPage(DriverFactory.getDriver());
		if(!aePage.isEventListingPresent())
		{
		aePage.clickhambergerMenu();
		aePage.clickEventListinglnk();
		}
		
		EventListingPage eventlist = new EventListingPage(DriverFactory.getDriver());
		eventlist.enterEventNo("DO-63322");
		eventlist.clickEventDashboardicon();
	}
}
