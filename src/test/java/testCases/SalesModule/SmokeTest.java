package testCases.SalesModule;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import components.HamburgerMenuPage;
import factory.DriverFactory;
import pageObjects.BeverageServicePage;
import pageObjects.EstimatesPage;
import pageObjects.EventDashboardPage;
import pageObjects.MenuServicePage;
import pageObjects.ServicesPage;
import pageObjects.StaffingServicePage;
import testBase.BaseClass;
import utilities.DataGenerator;
import workFlows.ContactFlow;
import workFlows.CustomerFlow;
import workFlows.EventFlow;
import workFlows.ServicesWorkFlows;

public class SmokeTest extends BaseClass {

	WebDriver driver;
	ServicesWorkFlows servicesFlow;
	CustomerFlow customerFlow;
	ContactFlow contactFlow;
	EventFlow eventFlow;
	DataGenerator dataGenerator;

	String eventNo;
	Map<String, String> eventData;
	Map<String, String> customerData;
	Map<String, String> contactData;

	MenuServicePage menuServicePage;
	StaffingServicePage staffingServicePage;
	BeverageServicePage beverageServicePage;
	ServicesPage servicesPage;
	EstimatesPage estimatesPage;
	EventDashboardPage dashboardPage;
	HamburgerMenuPage hamburgerMenuPage;

	List<String> menuService;
	List<String> staffingService;
	List<String> beverageService;
	List<String> estimateService;
	List<String> estimatesIcon;

	@BeforeMethod
	public void setUp() {
		driver = DriverFactory.getDriver();
		servicesFlow = new ServicesWorkFlows(driver);
		customerFlow = new CustomerFlow(driver);
		contactFlow = new ContactFlow();
		eventFlow = new EventFlow(driver);

		dataGenerator = new DataGenerator();
		menuService = List.of("Menu");
		beverageService = List.of("Beverage", "Non Alc Bev");
		staffingService = List.of("Personnel", "Staffing", "Scheduling");
		estimateService = List.of("Estimates");
		estimatesIcon = List.of("Estimates Lite", "Estimates");

		menuServicePage = new MenuServicePage(driver);
		staffingServicePage = new StaffingServicePage(driver);
		beverageServicePage = new BeverageServicePage(driver);

		servicesPage = new ServicesPage(driver);
		estimatesPage = new EstimatesPage(driver);

		dashboardPage = new EventDashboardPage(driver);
		hamburgerMenuPage = new HamburgerMenuPage(DriverFactory.getDriver());
	}

	@Test
	public void smokeFlow() {

		/*
		 * customerFlow.navigateToCreateCustomer();
		 * 
		 * customerData = dataGenerator.generate(customerJsonPath, "SmokeCustomerTest");
		 * customerFlow.createCustomer(customerData);
		 * 
		 * contactData = dataGenerator.generate(contactJsonPath, "SmokeContactTest");
		 * contactFlow.createContact(contactData);
		 */
		hamburgerMenuPage.navigatetoEventListing();
		eventFlow.navigateToCreateEvent();
		eventData = dataGenerator.generate(eventJsonPath, "SmokeEventTest");
		eventNo = eventFlow.createEvent(eventData);

		/* Menu Service */
		if (!servicesFlow.openServiceRequestFromEventDashboard(menuService))
			return;

		menuServicePage.addMenuItems();
		servicesFlow.finalizeService(eventNo, menuService);

		/* Staffing Service */
		if (!servicesFlow.openServiceRequestFromEventDashboard(staffingService))
			return;

		staffingServicePage.addStaffPositions();
		servicesFlow.finalizeService(eventNo, staffingService);

		/* Beverage Service */
		if (!servicesFlow.openServiceRequestFromEventDashboard(beverageService))
			return;

		beverageServicePage.addBeverageItems();

		beverageServicePage.clickReserveIfPresent();

		servicesFlow.finalizeService(eventNo, beverageService);

		/* Estimates */
		dashboardPage.clickServiceLabelIcon(estimateService, null, estimatesIcon);
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
