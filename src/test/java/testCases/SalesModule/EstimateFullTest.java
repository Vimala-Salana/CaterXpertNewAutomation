package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.FullEventApI;
import apis.LiteEventApi;
import factory.DriverFactory;
import pageObjects.EstimatesPage;
import pageObjects.EventDashboardPage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class EstimateFullTest extends BaseClass {

	EventDashboardPage dashboardPage;
	EstimatesPage estimatesPage;
	ServicesWorkFlows servicesFlow;
	FullEventApI fullEventApI;
	LiteEventApi liteEventApi;
	List<String> service;
	List<String> iconLabel;

	@BeforeMethod
	public void setup() {
		estimatesPage = new EstimatesPage(DriverFactory.getDriver());
		dashboardPage = new EventDashboardPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		fullEventApI = new FullEventApI();
		service = List.of("Estimates");
		iconLabel = List.of("Estimates Lite", "Estimates");

	}

	@Test(priority = 1, groups = { "Regression", "EventCreationDirect" })
	public void estimateFull() {

		String fullServiceEeventNo = fullEventApI.getFullEstimateEventId(loginId);
		servicesFlow.navigateToEventDashboard(fullServiceEeventNo);
		dashboardPage.clickServiceLabelIcon(service, null, iconLabel);

		Assert.assertTrue(estimatesPage.isFullEstimateDisplayed(), "Full Estimates not found");
		estimatesPage.giveEstimates();
		estimatesPage.saveTotalEstimates();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}
}
