package testCases.SalesModule;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BaseToSalesNavigationPage;
import pageObjects.EventListingPage;
import testBase.BaseClass;

public class SalesNewNavigationTest extends BaseClass {

	@Test(priority = 1, groups = { "Regression", "All" })
	public void salesNewNavigation() {
		BaseToSalesNavigationPage basePage = new BaseToSalesNavigationPage(DriverFactory.getDriver());
		basePage.salesNewNavigation();
		EventListingPage eventlist = new EventListingPage(DriverFactory.getDriver());
		eventlist.closeInventoryPopupIfPresent();
	}

}
