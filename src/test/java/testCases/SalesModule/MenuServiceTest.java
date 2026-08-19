package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.ServicesLevelNewEventApi;
import factory.DriverFactory;
import pageObjects.MenuServicePage;
import pageObjects.ServicesPage;
import testBase.BaseClass;
import utilities.LoggerManager;
import utilities.WaitUtils;
import workFlows.ServicesWorkFlows;

public class MenuServiceTest extends BaseClass {
	MenuServicePage menuServicePage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlow;
	ServicesLevelNewEventApi serviceEventApi;
	List<String> service;
	List<String> finalizedStatus;

	@BeforeMethod
	public void setup() {
		menuServicePage = new MenuServicePage(DriverFactory.getDriver());
		servicesPage = new ServicesPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		serviceEventApi = new ServicesLevelNewEventApi();
		service = List.of("Menu");
	}

	@Test(priority = 1, groups = { "Regression", "All" })
	public void menuServiceRequest() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		servicesFlow.openServiceRequestFromEventListing(eventNo, service);
		Assert.assertTrue(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s)),
				"Menu Service not Mapped/Service not present in the Service list.");
		menuServicePage.addMenuItems();
		servicesFlow.finalizeService(eventNo, service);
	}

	@Test(priority = 2, groups = { "Regression", "All" })

	public void deleteMenuItem() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		servicesFlow.openServiceRequestFromEventListing(eventNo, service);
		Assert.assertTrue(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s)),
				"Menu Service not Mapped/Service not present in the Service list.");
		menuServicePage.addMenuItems();
		String itemName = menuServicePage.getItemName();
		menuServicePage.clickDeleteIcon();
		menuServicePage.clickAlertYes();
		System.out.println(itemName);
		Assert.assertFalse(servicesPage.isItemPresent(itemName),"Item Not deleted");
	}

	@Test(priority = 3, groups = { "Regression", "All" })

	public void editMenuItemQty() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		servicesFlow.openServiceRequestFromEventListing(eventNo, service);
		Assert.assertTrue(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s)),
				"Menu Service not Mapped/Service not present in the Service list.");
		menuServicePage.addMenuItems();
		menuServicePage.editQuantity();
		servicesPage.clickServiceSave();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}
}
