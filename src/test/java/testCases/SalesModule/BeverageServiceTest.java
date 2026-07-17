package testCases.SalesModule;

import java.util.List;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BeverageservicePage;
import pageObjects.EventDashboardPage;
import pageObjects.HambergerMenuPage;
import pageObjects.MenuServicePage;
import testBase.BaseClass;

public class BeverageServiceTest extends BaseClass{

	@Test
	public void beveageservice(ITestContext context) throws InterruptedException
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
			

			if(bevService.clickReserveIfPresent())
			{
				System.out.println("Reserved Qty");
				bevService.clickOkInInventoryPopup();
			}
			
			else
				System.out.println("No Reserve Button Available");
			bevService.validateItems();
			bevService.clickFinalize();
			boolean constraintExists = mp.menuServiceConstraints(); 
			mp.fillmenuInfo();
			bevService.clickBeverageServiceClose();
			if(constraintExists)
			{
				HambergerMenuPage aepage = new HambergerMenuPage(DriverFactory.getDriver());
				aepage.clickhambergerMenu();
				aepage.clickApprovals();
				String eventNo = (String) context.getAttribute("eventNo");
				if(eventNo == null)
				{
					throw new SkipException("Event No not set - CreateEventTest may have failed");
				}
				mp.approveMenuserviceConstraints(constraintExists,eventNo);
			}

		}
	}
}
