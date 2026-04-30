package testCases.SchedulingModule;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.AEDashboardPage;
import pageObjects.CreateEventPage;
import pageObjects.EventListingPage;
import pageObjects.SchedulingDashboardPage;
import testBase.BaseClass;

public class SchedulingDashboardTest extends BaseClass{

	@Test
	public void schedulingDashboard(ITestContext context) throws InterruptedException 
	{
		SchedulingDashboardPage scheduling = new SchedulingDashboardPage(DriverFactory.getDriver());
		scheduling.clickSchedulingIcon();

		if(scheduling.getSchedulingHeader().equalsIgnoreCase("Schedule Dashboard"))
			System.out.println("Validated Scheduling Dashboard Header");

		AEDashboardPage aedashbaord = new AEDashboardPage(DriverFactory.getDriver());
		aedashbaord.clickhambergerMenu();
		aedashbaord.clickEventListinglnk();

		EventListingPage eventpage = new EventListingPage(DriverFactory.getDriver());
		Thread.sleep(5000);
		eventpage.enterEventNo((String) context.getAttribute("eventNo"));
		System.out.println(context.getAttribute("eventNo"));

	}

}
