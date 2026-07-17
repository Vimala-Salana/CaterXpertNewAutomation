package testCases.SalesModule;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.HambergerMenuPage;
import pageObjects.EventListingPage;
import testBase.BaseClass;

public class EventListingTest extends BaseClass{

	@Test
	public void eventListing()
	{
		HambergerMenuPage aePage = new HambergerMenuPage(DriverFactory.getDriver());
		if(!aePage.isEventListingPresent())
		{
		aePage.clickhambergerMenu();
		aePage.clickEventListinglnk();
		}
		
		String eventNo = "3149";
		EventListingPage eventlist = new EventListingPage(DriverFactory.getDriver());
		eventlist.enterEventNo(eventNo);
		eventlist.closeInventoryPopupIfPresent();
		eventlist.clickEventDashboardIcon(eventNo);
		
		//eventlist.clickEventDashboardIcon(eventNo);
	}
}
