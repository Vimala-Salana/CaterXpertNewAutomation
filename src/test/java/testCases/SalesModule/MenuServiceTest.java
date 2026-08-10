package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import apis.EventListApi;
import apis.ServicesLevelNewEventApi;
import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.MenuServicePage;
import pageObjects.ServicesPage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class MenuServiceTest extends BaseClass
{
	
	BasetoSalesNavigationPage baseNavPage;
	MenuServicePage menuServicePage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlow;
	ServicesLevelNewEventApi serviceEventApi;
	List<String> service;
	@BeforeMethod
	public void setup()
	{
		baseNavPage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		menuServicePage =  new MenuServicePage(DriverFactory.getDriver());
		servicesPage = new ServicesPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		serviceEventApi = new ServicesLevelNewEventApi();
		service = List.of("Menu");
		basicLogin();
		baseNavPage.salesNewNavigation();

	}
	@Test(groups = {"Regression", "All"})
	public void menuServiceRequest()
	{
		String eventNo =  serviceEventApi.newServiceEventId(service);
		servicesFlow.openServiceRequestFromEventListing(eventNo, service);
		Assert.assertTrue(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s)),
				"Menu Service not Mapped/Service not present in the Service list.");
		menuServicePage.addMenuItems();
		servicesFlow.finalizeService(eventNo, service);
	}
}

