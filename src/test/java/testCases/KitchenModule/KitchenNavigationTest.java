package testCases.KitchenModule;

import java.util.List;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.EventListingPage;
import pageObjects.KitchenNavigationPage;
import testBase.BaseClass;

public class KitchenNavigationTest extends BaseClass{

	@Test
	public void menu(ITestContext context)
	{
		
		BasetoSalesNavigationPage base = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		base.navigateTokitchen();
		KitchenNavigationPage kitchenMenu = new KitchenNavigationPage(DriverFactory.getDriver());
		kitchenMenu.handleChangeRequestPopUp();
		EventListingPage eventlist = new EventListingPage(DriverFactory.getDriver());
		
		String eventNo = (String) context.getAttribute("eventNo");
		if(eventNo == null)
		{
			throw new SkipException("Event No not set - CreateEventTest may have failed");
		}
		  eventlist.enterEventNo(eventNo); 
		 // eventlist.enterEventNo("2644");
		  //eventlist.clickEventDashboardicon();
		
		  kitchenMenu.handleChangeRequestPopUp();
		
		

		//MenuServicePage Menupage = new MenuServicePage(driver);
		
		List<String> service = List.of("Ack","Sent","ReSent","Accpt");
		kitchenMenu.clickKitchenMenu(service);
		kitchenMenu.acceptMenuService();
		kitchenMenu.clickPlusIcon();
		kitchenMenu.clickQuantificationLink();
		kitchenMenu.clickPlusIcon();
		kitchenMenu.clickgatherAll();
		kitchenMenu.clickPlusIcon();
		kitchenMenu.clickQuantifyAll();
		kitchenMenu.clickPlusIcon();
		kitchenMenu.clickMenuServicelnk();
		kitchenMenu.clickMenuServiceBillbtn();
		kitchenMenu.clickmenuServiceClose();
		kitchenMenu.handleChangeRequestPopUp();
		kitchenMenu.getQuantificationStatus();
		
	}
}
