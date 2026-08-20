package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.ServicesLevelNewEventApi;
import factory.DriverFactory;
import pageObjects.ServicesPage;
import pageObjects.StaffingServicePage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class StaffingServiceTest extends BaseClass {

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
		String serviceHeader = servicesPage.getServiceHdr();
		Assert.assertTrue(service.stream().anyMatch(serviceHeader::contains),
				"Staffing Service not present in the Service list.");

		staffingServicePage.addStaffPositions();
		servicesFlow.finalizeService(eventNo, service);
	}

	@AfterMethod()
	public void quitDriver() {
		DriverFactory.quitDriver();
	}
}
