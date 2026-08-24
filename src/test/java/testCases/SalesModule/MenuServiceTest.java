package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.ServicesLevelNewEventApi;
import factory.DriverFactory;
import pageObjects.MenuServicePage;
import pageObjects.MenuViewPage;
import pageObjects.ServicesPage;
import testBase.BaseClass;
import utilities.LoggerManager;
import workFlows.ServicesWorkFlows;

public class MenuServiceTest extends BaseClass {
	MenuServicePage menuServicePage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlow;
	ServicesLevelNewEventApi serviceEventApi;
	List<String> service;
	List<String> finalizedStatus;
	MenuViewPage menuViewPage;

	@BeforeMethod
	public void setup() {
		menuServicePage = new MenuServicePage(DriverFactory.getDriver());
		servicesPage = new ServicesPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		menuViewPage = new MenuViewPage(DriverFactory.getDriver());
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
		LoggerManager.info(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s))
				+ "Menu Service not Mapped/Service not present in the Service list.");
		menuServicePage.addMenuItems();
		String itemName = menuServicePage.getMenuItemName();
		menuServicePage.clickDeleteIcon();
		servicesPage.clickAlertYes();
		// System.out.println(itemName);
		Assert.assertFalse(servicesPage.isItemPresent(itemName), "Item is Not deleted");
	}

	@Test(priority = 3, groups = { "Regression", "All" })

	public void editMenuItemQty() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		servicesFlow.openServiceRequestFromEventListing(eventNo, service);
		LoggerManager.info(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s))
				+ "Menu Service not Mapped/Service not present in the Service list.");
		menuServicePage.addMenuItems();
		menuServicePage.editQuantity();
		servicesPage.clickServiceSave();
	}

	@Test(priority = 3, groups = { "Regression", "All" })
	public void validateMenuView() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		servicesFlow.openServiceRequestFromEventListing(eventNo, service);
		LoggerManager.info(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s))
				+ "Menu Service not Mapped/Service not present in the Service list.");
		menuServicePage.addMenuItems();

		String menuOption = menuServicePage.getMenuOption();
		String course = menuServicePage.getCourse();
		String itemName = menuServicePage.getMenuItemName();
		System.out.println(menuOption + "," + course + "," + itemName);

		servicesPage.openMenuBar();
		menuServicePage.navigateToMenuView();

		Assert.assertEquals(menuOption, menuViewPage.getMenuOption(), "Menu Option not found");
		Assert.assertEquals(course, menuViewPage.getCourse(), "Menu Course not found");
		Assert.assertEquals(itemName, menuViewPage.getMenuItem(), "Menu Item not found");
		LoggerManager.info("Item validated sucessfully in Menu View ");

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}
