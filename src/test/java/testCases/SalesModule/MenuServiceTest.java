package testCases.SalesModule;

import java.util.List;
import java.util.Map;

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
import utilities.DataGenerator;
import utilities.LoggerManager;
import workFlows.EventFlow;
import workFlows.ServicesWorkFlows;

public class MenuServiceTest extends BaseClass {
	MenuServicePage menuServicePage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlow;
	EventFlow eventFlow;
	ServicesLevelNewEventApi serviceEventApi;
	List<String> service;
	List<String> finalizedStatus;
	Map<String, String> eventData;
	MenuViewPage menuViewPage;

	@BeforeMethod
	public void setup() {
		menuServicePage = new MenuServicePage(DriverFactory.getDriver());
		servicesPage = new ServicesPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		menuViewPage = new MenuViewPage(DriverFactory.getDriver());
		eventFlow = new EventFlow(DriverFactory.getDriver());
		serviceEventApi = new ServicesLevelNewEventApi();
		service = List.of("Menu");
		DataGenerator dataGenerator = new DataGenerator();
		eventData = dataGenerator.generate(eventJsonPath, "MenuServiceEvent");
	}

	// @Test(priority = 1, groups = { "Regression", "All" })
	public void menuServiceRequest() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		LoggerManager.info(eventNo);
		if (eventNo == null) {

			eventFlow.createEventfromEventPage(eventData);

			servicesFlow.openServiceRequestFromEventDashboard(service);

		} else if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {

			return;
		}
		menuServicePage.addMenuItems();
		servicesFlow.finalizeService(eventNo, service);
	}

	// @Test(priority = 2, groups = { "Regression", "All" })

	public void deleteMenuItem() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		LoggerManager.info(eventNo);
		if (eventNo == null) {

			eventFlow.createEventfromEventPage(eventData);

			servicesFlow.openServiceRequestFromEventDashboard(service);

		} else if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {

			return;
		}
		menuServicePage.addMenuItems();
		String itemName = menuServicePage.getMenuItemNameFromServiceRequest();
		menuServicePage.clickDeleteIcon();
		servicesPage.clickAlertYes();
		// System.out.println(itemName);
		Assert.assertFalse(servicesPage.isItemPresent(itemName), "Item is Not deleted");
	}

	// @Test(priority = 3, groups = { "Regression", "All" })

	public void editMenuItemQty() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		LoggerManager.info(eventNo);
		if (eventNo == null) {

			eventFlow.createEventfromEventPage(eventData);

			servicesFlow.openServiceRequestFromEventDashboard(service);

		} else if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {

			return;
		}
		menuServicePage.addMenuItems();
		menuServicePage.editQuantity();
		servicesPage.clickServiceSave();
	}

	// @Test(priority = 4, groups = { "Regression", "All" })
	public void validateMenuView() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		LoggerManager.info(eventNo);
		if (eventNo == null) {

			eventFlow.createEventfromEventPage(eventData);

			servicesFlow.openServiceRequestFromEventDashboard(service);

		} else if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {

			return;
		}

		menuServicePage.addMenuItems();

		String menuOption = menuServicePage.getMenuOption();
		String course = menuServicePage.getCourse();
		String itemName = menuServicePage.getMenuItemNameFromServiceRequest();
		System.out.println(menuOption + "," + course + "," + itemName);

		servicesPage.openMenuBar();
		menuServicePage.navigateToMenuView();

		Assert.assertEquals(menuOption, menuViewPage.getMenuOption(), "Menu Option not found");
		Assert.assertEquals(course, menuViewPage.getCourse(), "Menu Course not found");
		Assert.assertEquals(itemName, menuViewPage.getMenuItem(), "Menu Item not found");
		LoggerManager.info("Item validated sucessfully in Menu View ");

	}

	@Test(priority = 5, groups = { "Regression", "All" })
	public void verifyMenuItemIsAddedUsingAdd() {
		String eventNo = serviceEventApi.newServiceEventId(loginId, service);
		LoggerManager.info(eventNo);
		if (eventNo == null) {

			eventFlow.createEventfromEventPage(eventData);

			servicesFlow.openServiceRequestFromEventDashboard(service);

		} else if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {

			return;
		}
		servicesPage.clickAddbtn();
		menuServicePage.addMenuItemFromAdd();
		String menuItem = menuServicePage.getAddScreenMenuItem();
		servicesPage.clickAddSave();
		servicesPage.clickAddClose();
		Assert.assertEquals(menuItem, menuServicePage.getMenuItemNameFromServiceRequest(), "Add Menu Item Failed");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}
