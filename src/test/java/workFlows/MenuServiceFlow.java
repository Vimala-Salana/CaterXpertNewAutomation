package workFlows;

import java.util.List;

import org.apache.poi.ss.usermodel.Header;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;

import components.HamburgerMenuPage;
import components.HeaderPage;
import pageObjects.EventDashboardPage;
import pageObjects.EventListingPage;
import pageObjects.MenuServicePage;

public class MenuServiceFlow {
	
	private MenuServicePage menuServicePage;
	private HamburgerMenuPage hambergerMenuPage;
	private EventDashboardPage eventDashboardPage;
	EventListingPage eventlistPage;
	private HeaderPage headerPage;
	ServicesWorkFlows servicesFlows;
	
	
	public MenuServiceFlow(WebDriver driver)
	{
		menuServicePage = new MenuServicePage(driver);
		hambergerMenuPage = new HamburgerMenuPage(driver);
		eventDashboardPage = new EventDashboardPage(driver);
		eventlistPage = new EventListingPage(driver);
		headerPage = new HeaderPage(driver);
		
	}
	
	public void openServiceRequestFromEventListing(String eventNo)
	{
		hambergerMenuPage.navigatetoEventListing();
		
		eventlistPage.enterEventNo(eventNo);
		eventlistPage.closeInventoryPopupIfPresent();
		eventlistPage.clickEventDashboardIcon();
		//openServiceFromEventDashboard();
	}
	
	
	
	
	public void finalizeService(String eventNo) throws InterruptedException
	{
		menuServicePage.clickFinalize();
		boolean constraintExists = menuServicePage.menuServiceConstraints(); 
		menuServicePage.fillMenuInfo();
		menuServicePage.menuServiceClose();
		if(constraintExists)
		{
			headerPage.clickhambergerMenu();
			hambergerMenuPage.clickApprovals();
			menuServicePage.approveMenuserviceConstraints(eventNo);
		}
		
		for(String header : eventDashboardPage.readAllHeaders())
		{
			//if(service.stream().anyMatch(header::contains))
			{
				System.out.println("Service Status : "+header);
				break;
			}
		}
	}
	
}
