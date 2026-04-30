package testCases.SalesModule;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.AEDashboardPage;
import pageObjects.EventListingPage;
import testBase.BaseClass;

public class EventListingTest extends BaseClass{

	@Test
	public void eventListing() throws InterruptedException
	{
		AEDashboardPage aePage = new AEDashboardPage(DriverFactory.getDriver());
		Thread.sleep(2000);
		if(!aePage.hdrEventListing.isDisplayed())
		{
		aePage.clickhambergerMenu();
		aePage.clickEventListinglnk();
		}
		
		EventListingPage eventlist = new EventListingPage(DriverFactory.getDriver());
		Thread.sleep(2000);
		eventlist.enterEventNo("2365");
		Thread.sleep(2000);
		eventlist.clickEventDashboardicon();
	}
}
