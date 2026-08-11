package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.ServicesLevelNewEventApi;
import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.ServicesPage;
import pageObjects.StaffingServicePage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class StaffingServiceTest extends BaseClass
{
	BasetoSalesNavigationPage baseNavPage;
	StaffingServicePage staffingServicePage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlow;
	ServicesLevelNewEventApi serviceEventApi;
	List<String> service;
	String loginId;

	@BeforeMethod
	public void setup()
	{
		baseNavPage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		staffingServicePage =  new StaffingServicePage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		serviceEventApi = new ServicesLevelNewEventApi();
		service = List.of("Personnel","Staffing","Scheduling");
		basicLogin();
		loginId = baseNavPage.salesNewNavigation();

	}

	@Test(groups = {"Regression", "All"})
	public void staffingRequest()
	{
		String eventNo =  serviceEventApi.newServiceEventId(loginId, service);
		servicesFlow.openServiceRequestFromEventListing(eventNo, service);
		Assert.assertTrue(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s)),
				"Staffing Service not present in the Service list.");

		staffingServicePage.giveStaffQty();
		staffingServicePage.clickSave();
		staffingServicePage.staffingInfo();
		servicesFlow.finalizeService(eventNo, service);
	}
}

