package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.LiteEventApi;
import factory.DriverFactory;
import pageObjects.EstimatesPage;
import pageObjects.EventDashboardPage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class EstimatesTest extends BaseClass {

	EventDashboardPage dashboardPage;
	EstimatesPage estimatesPage;
	ServicesWorkFlows servicesFlow;
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

	@Test(priority = 1, groups = { "Regression", "EventCreationDirect" })
	public void estimates() {
		String eventNo = liteEventApi.getAllNewServicesEventId(loginId);
		servicesFlow.navigateToEventDashboard(eventNo);
		dashboardPage.clickServiceLabelIcon(service, null, iconLabel);

		// System.out.println(estimates.getEstimateshdr());

		if (estimatesPage.isFullEstimateDisplayed()) {
			estimatesPage.giveEstimates();
			estimatesPage.saveTotalEstimates();
		} else {
			estimatesPage.clickEstimateLiteSave();
			estimatesPage.clickEstimateLiteClose();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}
}
