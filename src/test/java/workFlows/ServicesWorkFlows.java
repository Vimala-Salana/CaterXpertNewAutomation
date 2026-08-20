package workFlows;

import java.util.List;

import org.openqa.selenium.WebDriver;

import components.HamburgerMenuPage;
import components.HeaderPage;
import pageObjects.EventDashboardPage;
import pageObjects.EventListingPage;
import pageObjects.ServicesPage;
import utilities.LoggerManager;

public class ServicesWorkFlows {

	private static final List<String> status = List.of("New", "Prog", "Resent");
	private static final List<String> iconlabel = List.of("Service Request", "Estimates", "Estimates Lite");

	private ServicesPage servicesPage;
	private HamburgerMenuPage hamburgerMenuPage;
	private EventDashboardPage eventDashboardPage;
	EventListingPage eventlistPage;
	private HeaderPage headerPage;

	public ServicesWorkFlows(WebDriver driver) {
		servicesPage = new ServicesPage(driver);
		hamburgerMenuPage = new HamburgerMenuPage(driver);
		eventDashboardPage = new EventDashboardPage(driver);
		eventlistPage = new EventListingPage(driver);
		headerPage = new HeaderPage(driver);
	}

	public void openServiceRequestFromEventDashboard(List<String> serviceName) {
		// Navigating to Service Request from Event Dashboard
		boolean isServicePresent = eventDashboardPage.clickServiceLabelIcon(serviceName, status, iconlabel);
		if (!isServicePresent) {
			LoggerManager.info(serviceName + " is not Mapped to the Event Business Unit.");
			return;
		}

		String serviceHeader = servicesPage.getServiceHdr();

		boolean isServiceInList = serviceName.stream().anyMatch(serviceHeader::contains);

		if (!isServiceInList) {
			LoggerManager.info(serviceName + "Service not present in the Service list.");
			return;
		}

	}

	public void navigateToEventDashboard(String eventNo) {
		eventlistPage.enterEventNo(eventNo);
		eventlistPage.closeInventoryPopupIfPresent();
		eventlistPage.clickEventDashboardIcon(eventNo);
	}

	public void openServiceRequestFromEventListing(String eventNo, List<String> serviceName) {
		hamburgerMenuPage.navigatetoEventListing();
		navigateToEventDashboard(eventNo);
		openServiceRequestFromEventDashboard(serviceName);
	}

	public void finalizeService(String eventNo, List<String> serviceName) {
		servicesPage.clickFinalize();
		boolean constraintExists = servicesPage.hasServiceConstraints();
		servicesPage.fillInfo();
		servicesPage.clickServiceClose();

		if (constraintExists) {
			approveServiceConstraints(eventNo);
		}
		eventDashboardPage.validateServiceStatus(serviceName);

	}

	private void approveServiceConstraints(String eventNo) {
		headerPage.clickhambergerMenu();
		hamburgerMenuPage.clickApprovals();
		servicesPage.approveServiceConstraints(eventNo);
		navigateToEventDashboard(eventNo);
	}
}
