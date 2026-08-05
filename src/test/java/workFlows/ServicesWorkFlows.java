package workFlows;

import java.util.List;

import org.openqa.selenium.WebDriver;

import components.HamburgerMenuPage;
import components.HeaderPage;
import pageObjects.EventDashboardPage;
import pageObjects.EventListingPage;
import pageObjects.MenuServicePage;
import utilities.LoggerManager;

public class ServicesWorkFlows {

	private MenuServicePage menuServicePage;
	private HamburgerMenuPage hamburgerMenuPage;
	private EventDashboardPage eventDashboardPage;
	EventListingPage eventlistPage;
	private HeaderPage headerPage;
	
	public ServicesWorkFlows(WebDriver driver)
	{
		menuServicePage = new MenuServicePage(driver);
		hamburgerMenuPage = new HamburgerMenuPage(driver);
		eventDashboardPage = new EventDashboardPage(driver);
		eventlistPage = new EventListingPage(driver);
		headerPage = new HeaderPage(driver);
	}
	
	public void openServiceRequestFromEventDashboard(List<String> serviceName)
	{
		//Navigating to Menu Service from Event Dashboard
		
		List<String> status = List.of("New","Prog","Resent");
		List<String> iconlabel = List.of("Service Request");
		
		if(!eventDashboardPage.clickServiceLabelIcon(serviceName, status, iconlabel))
			
			throw new RuntimeException(serviceName+" is not Mapped");
			
	}
	
	public void openServiceRequestFromEventListing(String eventNo, List<String> serviceName)
	{
		hamburgerMenuPage.navigatetoEventListing();
		
		eventlistPage.enterEventNo(eventNo);
		eventlistPage.closeInventoryPopupIfPresent();
		eventlistPage.clickEventDashboardIcon(eventNo);
		openServiceRequestFromEventDashboard(serviceName);
	}
	
	public void finalizeService(String eventNo, List<String> serviceName)
	{
		menuServicePage.clickFinalize();
		boolean constraintExists = menuServicePage.menuServiceConstraints(); 
		menuServicePage.fillMenuInfo();
		menuServicePage.menuServiceClose();
		if(constraintExists)
		{
			approveServiceConstraints(eventNo);
		}
		
		validateServiceStatus(serviceName);
		
	}
	
	private void approveServiceConstraints(String eventNo)
	{
		headerPage.clickhambergerMenu();
		hamburgerMenuPage.clickApprovals();
		menuServicePage.approveMenuserviceConstraints(eventNo);
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


