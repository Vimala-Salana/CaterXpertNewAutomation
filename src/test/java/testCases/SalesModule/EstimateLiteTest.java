package testCases.SalesModule;

import java.util.List;

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

public class EstimateLiteTest extends BaseClass {

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
		liteEventApi = new LiteEventApi();
		service = List.of("Estimates");
		iconLabel = List.of("Estimates Lite", "Estimates");

	}

	@Test(priority = 2, groups = { "Regression", "EventCreationDirect" })
	public void estimateLite() {
		String liteEventNo = liteEventApi.getLiteEstimateEventId(loginId);
		servicesFlow.navigateToEventDashboard(liteEventNo);
		dashboardPage.clickServiceLabelIcon(service, null, iconLabel);

		estimatesPage.clickEstimateLiteSave();
		estimatesPage.clickEstimateLiteClose();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}
