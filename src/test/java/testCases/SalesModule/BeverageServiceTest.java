package testCases.SalesModule;

import java.util.List;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BeverageservicePage;
import pageObjects.EventDashboardPage;
import pageObjects.MenuServicePage;
import testBase.BaseClass;

public class BeverageServiceTest extends BaseClass{

	@Test
	public void beveageservice()
	{
		EventDashboardPage dashboard = new EventDashboardPage(DriverFactory.getDriver());


		List<String> service = List.of("Beverage");
		List<String> status = List.of("New","Prog","Resent","None");
		List<String> iconlabel = List.of("Service Request");
		if(dashboard.clickServiceLabelIcon(service, status, iconlabel))
		{
			MenuServicePage mp = new MenuServicePage(DriverFactory.getDriver());
			BeverageservicePage bevService = new BeverageservicePage(DriverFactory.getDriver());
			bevService.uncheckIfOutsourcedOrNotRequired();
			mp.clickSearchAndAddbtn();
			bevService.showMappedItems();
			
			bevService.enterQuantity();
			mp.clickListSave();
			bevService.clickOkInInventoryPopup();
			mp.clickListClose();
			bevService.validateItems();

			if(bevService.clickReserveIfPresent())
			{
				System.out.println("Reserved Qty");
				bevService.clickOkInInventoryPopup();
			}
			
			else
				System.out.println("No Reserve Button Available");

		}
	}
}
