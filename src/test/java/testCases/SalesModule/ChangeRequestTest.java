package testCases.SalesModule;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.ChangeRequestApi;
import components.HamburgerMenuPage;
import factory.DriverFactory;
import pageObjects.ChangeRequestPage;
import pageObjects.EventDashboardPage;
import pageObjects.EventListingPage;
import pageObjects.ServicesPage;
import testBase.BaseClass;
import utilities.LoggerManager;
import workFlows.ServicesWorkFlows;

public class ChangeRequestTest extends BaseClass {

	WebDriver driver;
	EventDashboardPage eventDashboardPage;
	EventListingPage eventListingPage;
	HamburgerMenuPage hamburgerMenuPage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlowPage;
	ChangeRequestPage changeRequestPage;
	ChangeRequestApi changeRequestApi;
	List<String> service;
	List<String> status;
	List<String> iconChangeRequest;

	@BeforeMethod
	public void setup() {
		driver = DriverFactory.getDriver();
		eventDashboardPage = new EventDashboardPage(driver);
		eventListingPage = new EventListingPage(driver);
		hamburgerMenuPage = new HamburgerMenuPage(driver);
		servicesPage = new ServicesPage(driver);
		servicesFlowPage = new ServicesWorkFlows(driver);
		changeRequestPage = new ChangeRequestPage(driver);
		changeRequestApi = new ChangeRequestApi();
		status = List.of("Sent", "Accpt", "Ack");
		iconChangeRequest = List.of("Change Request");
	}

	@Test(priority = 1, groups = { "Regression", "All" })
	public void menuChangeRequest() {
		hamburgerMenuPage.navigatetoEventListing();
		service = List.of("Menu");
		String eventNo = changeRequestApi.changeRequestEvent(loginId, service, status);
		servicesFlowPage.navigateToEventDashboard(eventNo);
		boolean isChangeRequestExists = eventDashboardPage.clickServiceLabelIcon(service, status, iconChangeRequest);

		Assert.assertTrue(isChangeRequestExists, "Menu Change Request does not Exists");

		changeRequestPage.selectOrder();
		changeRequestPage.enterSentComments("Menu Change Request");
		changeRequestPage.clickAddOrEditItems();
		changeRequestPage.enterQty("10");
		changeRequestPage.saveCurrentTab();
		changeRequestPage.closeCurrentTab();
		changeRequestPage.sendChangeRequest();
		String changeRequestStatus = changeRequestPage.getChangeRequestStatus();
		LoggerManager.info("Menu Change Request status is : " + changeRequestStatus);
		changeRequestPage.closeChangeRequest();
		LoggerManager.info("Menu Change Request Sucessfull....");

	}

	// @Test(priority = 2, groups = {"Regression","All"})
	public void beverageChangeRequest() {
		hamburgerMenuPage.navigatetoEventListing();
		service = List.of("Beverage", "Non Alc Bev", "Soft Beverages", "Beverages");
		String eventNo = changeRequestApi.changeRequestEvent(loginId, service, status);
		servicesFlowPage.navigateToEventDashboard(eventNo);
		boolean isChangeRequestExists = eventDashboardPage.clickServiceLabelIcon(service, status, iconChangeRequest);

		Assert.assertTrue(isChangeRequestExists, "Beverage Change Request does not Exists");

		changeRequestPage.selectOrder();
		changeRequestPage.enterSentComments("Beverage Change Request");
		changeRequestPage.clickAddOrEditItems();
		changeRequestPage.enterQty("10");
		changeRequestPage.saveCurrentTab();
		changeRequestPage.closeCurrentTab();
		changeRequestPage.sendChangeRequest();
		String changeRequestStatus = changeRequestPage.getChangeRequestStatus();
		LoggerManager.info("Beverage Change Request status is : " + changeRequestStatus);
		changeRequestPage.closeChangeRequest();
		LoggerManager.info("Beverage Change Request Sucessfull....");

	}

	// @Test(priority = 3, groups = {"Regression","All"})
	public void staffingChangeRequest() {
		hamburgerMenuPage.navigatetoEventListing();
		service = List.of("Personnel", "Staffing", "Scheduling");
		String eventNo = changeRequestApi.changeRequestEvent(loginId, service, status);
		servicesFlowPage.navigateToEventDashboard(eventNo);
		boolean isChangeRequestExists = eventDashboardPage.clickServiceLabelIcon(service, status, iconChangeRequest);

		Assert.assertTrue(isChangeRequestExists, "Staffing Change Request does not Exists");

		changeRequestPage.enterSentComments("Staffing Change Request");
		changeRequestPage.clickAddOrEditItems();
		changeRequestPage.enterQty("2");
		changeRequestPage.saveCurrentTab();
		changeRequestPage.closeCurrentTab();
		changeRequestPage.sendChangeRequest();
		String changeRequestStatus = changeRequestPage.getChangeRequestStatus();
		LoggerManager.info("Staffing Change Request status is : " + changeRequestStatus);
		changeRequestPage.closeChangeRequest();
		LoggerManager.info("Staffing Change Request Sucessfull....");

	}

	@AfterMethod
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}
