package workFlows;

import java.util.List;

import org.openqa.selenium.WebDriver;

import components.HamburgerMenuPage;
import components.HeaderPage;
import pageObjects.EventDashboardPage;
import pageObjects.EventListingPage;
import pageObjects.ServicesPage;
import utilities.ReportManager;

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

	public boolean openServiceRequestFromEventDashboard(List<String> serviceName) {
		// Navigating to Service Request from Event Dash board
		boolean isServiceMapped = eventDashboardPage.clickServiceLabelIcon(serviceName, status, iconlabel);

		if (!isServiceMapped) {
			ReportManager.info(serviceName + " is not mapped to the Event Business Unit.");
			return false;
		}
		String serviceHeader = servicesPage.getServiceHdr();
		boolean isServiceInList = serviceName.stream().anyMatch(serviceHeader::contains);

		if (!isServiceInList) {
			ReportManager.info(serviceName + " is not Present in Service List.");
			return false;
		}
		return true;
	}

	public void navigateToEventDashboard(String eventNo) {
		eventlistPage.enterEventNo(eventNo);
		eventlistPage.closeInventoryPopupIfPresent();
		eventlistPage.clickEventDashboardIcon(eventNo);
	}

	public boolean openServiceRequestFromEventListing(String eventNo, List<String> serviceName) {
		hamburgerMenuPage.navigatetoEventListing();
		navigateToEventDashboard(eventNo);
		return openServiceRequestFromEventDashboard(serviceName);
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
