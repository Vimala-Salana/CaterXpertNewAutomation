package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.ServicesLevelNewEventApi;
import factory.DriverFactory;
import pageObjects.BeverageServicePage;
import pageObjects.ServicesPage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class BeverageServiceTest extends BaseClass {

	BeverageServicePage beverageServicePage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlow;
	ServicesLevelNewEventApi servicesNewApi;
	List<String> service;

	@BeforeMethod
	public void setup() {
		servicesNewApi = new ServicesLevelNewEventApi();
		beverageServicePage = new BeverageServicePage(DriverFactory.getDriver());
		servicesPage = new ServicesPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		service = List.of("Beverage", "Non Alc Bev", "Soft Beverages", "Beverages");

	}

	@Test(priority = 1, groups = { "Regression", "All" })
	public void beveageservice() {
		String eventNo = servicesNewApi.newServiceEventId(loginId, service);

		if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {
			return;
		}
		beverageServicePage.addBeverageItems();

		beverageServicePage.validateItems();

		servicesFlow.finalizeService(eventNo, service);

	}

	@Test(priority = 2, groups = { "Regression", "All" })
	public void editBeverageItem() {
		String eventNo = servicesNewApi.newServiceEventId(loginId, service);

		if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {
			return;
		}
		beverageServicePage.addBeverageItems();
		beverageServicePage.editQuantity();
		servicesPage.clickServiceSave();
	}

	@Test(priority = 3, groups = { "Regression", "All" })
	public void deleteBeverageItem() {
		String eventNo = servicesNewApi.newServiceEventId(loginId, service);

		if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {
			return;
		}
		beverageServicePage.addBeverageItems();
		String itemName = beverageServicePage.getBeverageItemName();
		beverageServicePage.deleteItem();
		servicesPage.clickAlertOk();

		Assert.assertFalse(servicesPage.isItemPresent(itemName), "Item is Not deleted");
	}

	@Test(priority = 4, groups = { "Regression", "All" })
	public void verifyBeverageItemUsingAdd() {
		String eventNo = servicesNewApi.newServiceEventId(loginId, service);

		if (!servicesFlow.openServiceRequestFromEventListing(eventNo, service)) {
			return;
		}
		servicesPage.clickAddbtn();
		beverageServicePage.clickExpandAll();
		beverageServicePage.addItemsFromAdd("10");
		servicesPage.clickSideBarSave();
		servicesPage.clicSideBarClose();

	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}
}
