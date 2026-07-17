package testCases.SalesModule;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.EventListingPage;
import testBase.BaseClass;

public class SalesNewNavigationTest extends BaseClass{
	
	@Test(priority = 1)
	public void salesNewNavigation()
	{
			BasetoSalesNavigationPage base = new BasetoSalesNavigationPage(DriverFactory.getDriver());
			base.salesNewNavigation();
			EventListingPage eventlist = new EventListingPage(DriverFactory.getDriver());
			eventlist.closeInventoryPopupIfPresent();
	}
	
	

}
