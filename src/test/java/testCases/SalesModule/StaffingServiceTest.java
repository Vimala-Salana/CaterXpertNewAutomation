package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.ServicesLevelNewEventApi;
import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.ServicesPage;
import pageObjects.StaffingServicePage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class StaffingServiceTest extends BaseClass {
	BasetoSalesNavigationPage baseNavPage;
	StaffingServicePage staffingServicePage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlow;
	ServicesLevelNewEventApi serviceEventApi;
	List<String> service;

	@BeforeMethod
	public void setup() {
		staffingServicePage = new StaffingServicePage(DriverFactory.getDriver());
		servicesPage = new ServicesPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		serviceEventApi = new ServicesLevelNewEventApi();
		service = List.of("Personnel", "Staffing", "Scheduling");
	}

	@Test(groups = { "Regression", "All" })
	public void staffingRequest() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		servicesFlow.openServiceRequestFromEventListing(eventNo, service);
		Assert.assertTrue(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s)),
				"Staffing Service not present in the Service list.");

		staffingServicePage.giveStaffQty();
		servicesPage.clickServiceSave();
		servicesFlow.finalizeService(eventNo, service);
	}

	@AfterMethod()
	public void quitDriver() {
		DriverFactory.quitDriver();
	}
}
