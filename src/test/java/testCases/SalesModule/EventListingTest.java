package testCases.SalesModule;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.HambergerMenuPage;
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
		
		//ServicesWorkFlows servicesWorkFlows = new ServicesWorkFlows(DriverFactory.getDriver());
		//servicesWorkFlows.openServiceRequestFromEventListing(eventNo, null);
		
		//eventlist.clickEventDashboardIcon(eventNo);
	}
}
