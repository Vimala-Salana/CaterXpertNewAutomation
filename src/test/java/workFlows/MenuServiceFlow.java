package workFlows;

import java.util.List;

import org.apache.poi.ss.usermodel.Header;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.SkipException;

import components.HambergerMenuPage;
import components.HeaderPage;
import pageObjects.EventDashboardPage;
import pageObjects.EventListingPage;
import pageObjects.MenuServicePage;

public class MenuServiceFlow {
	
	private MenuServicePage menuServicePage;
	private HambergerMenuPage hambergerMenuPage;
	private EventDashboardPage eventDashboardPage;
	EventListingPage eventlistPage;
	private HeaderPage headerPage;
	
	public MenuServiceFlow(WebDriver driver)
	{
		menuServicePage = new MenuServicePage(driver);
		hambergerMenuPage = new HambergerMenuPage(driver);
		eventDashboardPage = new EventDashboardPage(driver);
		eventlistPage = new EventListingPage(driver);
		headerPage = new HeaderPage(driver);
	}
	
	public void openMenuServicefromEventDashboard()
	{
		//Navigating to Menu Service from Event Dashboard
		List<String> service = List.of("Menu");
		List<String> status = List.of("New","Prog","Resent");
		List<String> iconlabel = List.of("Service Request");
		eventDashboardPage.clickServiceLabelIcon(service, status, iconlabel);
	}
	
	public void openMenuServiceFromEventListing(String eventNo)
	{
		hambergerMenuPage.navigatetoEventListing();
		
		eventlistPage.enterEventNo(eventNo);
		eventlistPage.closeInventoryPopupIfPresent();
		eventlistPage.clickEventDashboardIcon(eventNo);
		openMenuServicefromEventDashboard();
	}
	
	public void addMenuItems() throws InterruptedException
	{
			menuServicePage.clickSearchAndAddbtn();
			menuServicePage.addMenuItems();			
	}
	
	
	public void finalizeservice(String eventNo) throws InterruptedException
	{
		menuServicePage.clickFinalize();
		boolean constraintExists = menuServicePage.menuServiceConstraints(); 
		menuServicePage.fillmenuInfo();
		menuServicePage.menuServiceClose();
		if(constraintExists)
		{
			headerPage.clickhambergerMenu();
			hambergerMenuPage.clickApprovals();
			if(eventNo == null)
			{
				throw new SkipException("Event No not set - CreateEventTest may have failed");
			}
			menuServicePage.approveMenuserviceConstraints(constraintExists,eventNo);
		}
		for(String header : eventDashboardPage.readAllHeaders())
		{
			if(header.contains("Menu"))
			{
				System.out.println("Service Status : "+header);
				break;
			}
		}
	}
	
}
