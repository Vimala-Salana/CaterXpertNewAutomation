package testCases.SalesModule;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.EventDashboardPage;
import testBase.BaseClass;

public class EventDashboardTest extends BaseClass{

	@Test
	public void eventdashboard(ITestContext context) throws InterruptedException
	{
		EventDashboardPage dashboard = new EventDashboardPage(DriverFactory.getDriver());
	
		String eventNo = dashboard.getEventNo();
		context.setAttribute("eventNo", eventNo);
		Thread.sleep(1000);
		System.out.println(dashboard.readAllHeaders());
		
	}
	
}
