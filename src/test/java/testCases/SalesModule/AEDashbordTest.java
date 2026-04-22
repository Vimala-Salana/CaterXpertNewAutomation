package testCases.SalesModule;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AEDashboardPage;
import testBase.BaseClass;

public class AEDashbordTest extends BaseClass{

	@Test(priority = 2)
	public void aEDashboard() throws InterruptedException
	{
		AEDashboardPage aePage = new AEDashboardPage(driver);
		String aeDashboardhdr = aePage.getAEDashboardHeader();
		Assert.assertEquals(aeDashboardhdr, "AE Dashboard","AE Dashboard header text mismatch!");
	}
}
