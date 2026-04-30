package testCases.SalesModule;

import java.util.List;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.AEDashboardPage;
import pageObjects.CreateEventPage;
import pageObjects.EventDashboardPage;
import pageObjects.MenuServicePage;
import testBase.BaseClass;

public class MenuServiceTest extends BaseClass
{
	@Test
	public void menuServiceRequest(ITestContext context) throws InterruptedException
	{

		EventDashboardPage dashboard = new EventDashboardPage(DriverFactory.getDriver());

		Thread.sleep(3000);
		List<String> service = List.of("Menu");
		List<String> status = List.of("New","Prog","Resent");
		List<String> iconlabel = List.of("Service Request");
		
		if(dashboard.clickServiceLabelIcon(service, status, iconlabel))
		{
			MenuServicePage mp = new MenuServicePage(DriverFactory.getDriver());
			if (mp.getmenuServiceHdr().equals("Event Services - Menu"))
			{
				mp.clickSearchAndAddbtn();
				mp.selectMenuItems();
				mp.clickListSaveandClose();
				mp.clickFinalize();
				boolean constraintExists = mp.menuServiceConstraints(); 
				mp.menuInfo();
				mp.menuServiceClose();
				if(constraintExists)
				{
					AEDashboardPage aepage = new AEDashboardPage(DriverFactory.getDriver());
					aepage.clickhambergerMenu();
					aepage.clickApprovals();
					String eventNo = (String) context.getAttribute("eventNo");
					if(eventNo == null)
					{
						throw new SkipException("Event No not set - CreateEventTest may have failed");
					}
					mp.approveMenuserviceConstraints(constraintExists,eventNo);
				}
				for(String header : dashboard.readAllHeaders())
				{
					if(header.contains("Menu"))
					{
						System.out.println("Service Status : "+header);
						break;
					}
				}
			}
			else
				System.out.println("Menu Service not mapped to the Event Business Unit.");
		}
		else
		{
			System.out.println("Menu Service Not Avaliable");
		}

	}
}

