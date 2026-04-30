package testCases.SalesModule;

import org.testng.Assert;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.AEDashboardPage;
import testBase.BaseClass;

public class AEDashbordTest extends BaseClass{

	@Test(priority = 2)
	public void aEDashboard() throws InterruptedException
	{
		AEDashboardPage aePage = new AEDashboardPage(DriverFactory.getDriver());
		String aeDashboardhdr = aePage.getAEDashboardHeader();
		Assert.assertEquals(aeDashboardhdr, "AE Dashboard","AE Dashboard header text mismatch!");
	}
}
