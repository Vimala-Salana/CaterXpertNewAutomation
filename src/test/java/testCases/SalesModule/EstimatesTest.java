package testCases.SalesModule;

import java.util.List;

import org.testng.annotations.Test;

import pageObjects.EstimatesPage;
import pageObjects.EventDashboardPage;
import testBase.BaseClass;

public class EstimatesTest extends BaseClass{

	@Test
	public void estimates() throws InterruptedException
	{
		EventDashboardPage dashboard = new EventDashboardPage(driver);
		List<String> service = List.of("Estimates");
		List<String> iconlabel = List.of("Estimates Lite","Estimates");
		dashboard.clickServiceLabelIcon(service, null, iconlabel);
		EstimatesPage estimates = new EstimatesPage(driver);
		System.out.println(estimates.getEstimateshdr());
		
		if(estimates.getEstimateshdr().trim().equals("Estimate Sections"))
		{
			estimates.estimates();
		}
		else
		{
			estimates.clickEstimateLiteSave();
			estimates.clickEstimateLiteClose();
		}
		System.out.println("Estimates Given");
	}
}
