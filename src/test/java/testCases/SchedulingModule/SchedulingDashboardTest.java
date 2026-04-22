package testCases.SchedulingModule;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import pageObjects.AEDashboardPage;
import pageObjects.CreateEventPage;
import pageObjects.EventListingPage;
import pageObjects.SchedulingDashboardPage;
import testBase.BaseClass;

public class SchedulingDashboardTest extends BaseClass{

	@Test
	public void schedulingDashboard(ITestContext context) throws InterruptedException 
	{
		SchedulingDashboardPage scheduling = new SchedulingDashboardPage(driver);
		scheduling.clickSchedulingIcon();

		if(scheduling.validateSchedulinghdr().equalsIgnoreCase("Schedule Dashboard"))
			System.out.println("Validated Scheduling Dashboard Header");

		AEDashboardPage aedashbaord = new AEDashboardPage(driver);
		aedashbaord.clickhambergerMenu();
		aedashbaord.clickEventListinglnk();

		EventListingPage eventpage = new EventListingPage(driver);
		Thread.sleep(5000);
		eventpage.enterEventNo((String) context.getAttribute("eventNo"));
		System.out.println(context.getAttribute("eventNo"));

	}

}
