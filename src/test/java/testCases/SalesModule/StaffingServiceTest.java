package testCases.SalesModule;

import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import pageObjects.AEDashboardPage;
import pageObjects.CreateEventPage;
import pageObjects.EventDashboardPage;
import pageObjects.StaffingServicePage;
import testBase.BaseClass;

public class StaffingServiceTest extends BaseClass
{

	@Test
	public void staffingRequest(ITestContext context) throws InterruptedException
	{
		EventDashboardPage dashboard = new EventDashboardPage(driver);
		StaffingServicePage staff = new StaffingServicePage(driver);

		List<String> service = List.of("Personnel","Staffing","Scheduling");
		List<String> status = List.of("New","Prog","Resent");
		List<String> iconlabel = List.of("Service Request");
		if(dashboard.clickServiceLabelIcon(service,status,iconlabel))
		{
			Thread.sleep(1000);
			String serviceName  = staff.getStaffingServiceHdr().trim();
			System.out.println("service  "+service+"  serviceNameheader : "+serviceName);
			if(service.contains(serviceName))
			{
				staff.giveStaffQty();
				staff.clickSave();
				staff.clickFinalize();
				boolean constraintExists = staff.staffingConstraints();
				staff.staffingInfo();
				staff.clickStaffClose();
				
				if(constraintExists)
				{
					AEDashboardPage aepage = new AEDashboardPage(driver);
					aepage.clickhambergerMenu();
					aepage.clickApprovals();
					String eventNo = (String) context.getAttribute("eventNo");
					staff.approveStaffingConstraints(constraintExists, eventNo);
				}
				for(String header : dashboard.readAllHeaders())
				{
					if(header.contains("Staffing") || header.contains("Personnel"))
					{
						System.out.println("Service Status : "+header);
						break;
					}
				}
			}
			else
			System.out.println("Staffing Service not mapped to the Event Business Unit.");
		}
		else
			System.out.println("Staffing Service Not Avaliable");
	}
}
