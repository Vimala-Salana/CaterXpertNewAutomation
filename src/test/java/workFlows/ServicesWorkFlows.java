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

	private ServicesPage servicesPage;
	private HamburgerMenuPage hamburgerMenuPage;
	private EventDashboardPage eventDashboardPage;
	EventListingPage eventlistPage;
	private HeaderPage headerPage;

	public ServicesWorkFlows(WebDriver driver)
	{
		servicesPage = new ServicesPage(driver);
		hamburgerMenuPage = new HamburgerMenuPage(driver);
		eventDashboardPage = new EventDashboardPage(driver);
		eventlistPage = new EventListingPage(driver);
		headerPage = new HeaderPage(driver);
	}

	public void openServiceRequestFromEventDashboard(List<String> serviceName)
	{
		//Navigating to Menu Service from Event Dashboard

		List<String> status = List.of("New","Prog","Resent");
		List<String> iconlabel = List.of("Service Request","Estimates","Estimates Lite");

		if(!eventDashboardPage.clickServiceLabelIcon(serviceName, status, iconlabel))

			throw new RuntimeException(serviceName+" is not Mapped");

	}
	
	public void navigateToEventDashboard(String eventNo)
	{
		eventlistPage.enterEventNo(eventNo);
		eventlistPage.closeInventoryPopupIfPresent();
		eventlistPage.clickEventDashboardIcon(eventNo);
	}

	public void openServiceRequestFromEventListing(String eventNo, List<String> serviceName)
	{
		hamburgerMenuPage.navigatetoEventListing();

		navigateToEventDashboard(eventNo);
		openServiceRequestFromEventDashboard(serviceName);
	}

	public void finalizeService(String eventNo, List<String> serviceName)
	{
		servicesPage.clickFinalize();
		boolean constraintExists = servicesPage.hasServiceConstraints(); 
		servicesPage.fillInfo();
		servicesPage.clickServiceClose();

		if(constraintExists) { 
			approveServiceConstraints(eventNo); 
		}

		validateServiceStatus(serviceName);

	}

	private void approveServiceConstraints(String eventNo)
	{
		headerPage.clickhambergerMenu();
		hamburgerMenuPage.clickApprovals();
		servicesPage.approveServiceConstraints(eventNo);
	}

	public void validateServiceStatus(List<String> serviceName)
	{
		for(String header : eventDashboardPage.readAllHeaders())
		{
			if(serviceName.stream().anyMatch(header::contains))
			{
				LoggerManager.info("Service Status : "+ header);
				break;
			}
			else {

			}
		}
	}
}


