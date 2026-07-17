package testCases.SalesModule;

import org.testng.Assert;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.HambergerMenuPage;
import testBase.BaseClass;

public class HambergerMenuTest extends BaseClass{

	@Test(priority = 2)
	public void aEDashboard()
	{
		HambergerMenuPage aePage = new HambergerMenuPage(DriverFactory.getDriver());
		String aeDashboardhdr = aePage.getAEDashboardHeader();
		Assert.assertEquals(aeDashboardhdr, "AE Dashboard","AE Dashboard header text mismatch!");
	}
}
