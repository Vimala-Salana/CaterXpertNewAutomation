package testCases.SalesModule;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
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

	BasetoSalesNavigationPage salesNaviagationPage;
	ServicesWorkFlows servicesFlow;
	CustomerFlow customerFlow;
	ContactFlow contactFlow;
	DataGenerator dataGenerator;

	Map<String, String> customerData;

	Map<String, String> contactData;

	EventFlow eventFlow;
	Map<String, String> eventData;

	String eventNo;
	List<String> menuService;
	List<String> staffingService;
	List<String> beverageService;
	List<String> estimateService;
	List<String> estimatesIcon;

	MenuServicePage menuServicePage;
	StaffingServicePage staffingServicePage;
	BeverageServicePage beverageServicePage;
	ServicesPage servicesPage;
	EstimatesPage estimatesPage;
	EventDashboardPage dashboardPage;

	@BeforeMethod

	public void setUp() {
		salesNaviagationPage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		customerFlow = new CustomerFlow(DriverFactory.getDriver());
		contactFlow = new ContactFlow();
		eventFlow = new EventFlow(DriverFactory.getDriver());

		dataGenerator = new DataGenerator();
		menuService = List.of("Menu");
		beverageService = List.of("Beverage", "Non Alc Bev");
		staffingService = List.of("Personnel", "Staffing", "Scheduling");
		estimateService = List.of("Estimates");
		estimatesIcon = List.of("Estimates Lite", "Estimates");

		menuServicePage = new MenuServicePage(DriverFactory.getDriver());
		staffingServicePage = new StaffingServicePage(DriverFactory.getDriver());
		beverageServicePage = new BeverageServicePage(DriverFactory.getDriver());

		servicesPage = new ServicesPage(DriverFactory.getDriver());
		estimatesPage = new EstimatesPage(DriverFactory.getDriver());

		dashboardPage = new EventDashboardPage(DriverFactory.getDriver());
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
		eventFlow.navigateToCreateEvent();
		eventData = dataGenerator.generate(eventJsonPath, "SmokeEventTest");
		eventNo = eventFlow.createEvent(eventData);

		/* Menu Service */
		servicesFlow.openServiceRequestFromEventDashboard(menuService);
		menuServicePage.addMenuItems();
		servicesFlow.finalizeService(eventNo, menuService);

		/* Staffing Service */
		servicesFlow.openServiceRequestFromEventDashboard(staffingService);
		staffingServicePage.giveStaffQty();
		servicesPage.clickServiceSave();
		servicesFlow.finalizeService(eventNo, staffingService);

		/* Beverage Service */
		servicesFlow.openServiceRequestFromEventDashboard(beverageService);

		servicesPage.clickSearchAndAddbtn();
		servicesPage.uncheckIfOutsourcedOrNotRequired();
		beverageServicePage.showMappedItems();

		beverageServicePage.enterQuantity();
		servicesPage.clickListSave();
		beverageServicePage.closeInventoryPopupIfPresent();
		servicesPage.clickListClose();

		beverageServicePage.clickReserveIfPresent();

		beverageServicePage.closeInventoryPopupIfPresent();

		beverageServicePage.validateItems();

		servicesFlow.finalizeService(eventNo, beverageService);

		/* Estimates */
		dashboardPage.clickServiceLabelIcon(estimateService, null, estimatesIcon);
		if (estimatesPage.getEstimateshdr()) {
			estimatesPage.giveEstimates();
			estimatesPage.clickTotalEstimates();
			estimatesPage.selectTotalEstimateOptions();

			Assert.assertEquals(estimatesPage.getEstimatesTotals(), estimatesPage.getActualTotal(),
					"Actual and Calculated Totals are not Same");
			estimatesPage.saveTotalEstimates();
			estimatesPage.closeTotalEstimates();
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
