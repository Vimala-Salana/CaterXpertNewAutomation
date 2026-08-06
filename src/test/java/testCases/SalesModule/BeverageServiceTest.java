package testCases.SalesModule;

import java.util.List;

import org.checkerframework.checker.units.qual.s;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.BeverageServicePage;
import pageObjects.ServicesPage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class BeverageServiceTest extends BaseClass{
	
	BasetoSalesNavigationPage baseNavPage;
	BeverageServicePage beverageServicePage;
	ServicesPage servicesPage;
	ServicesWorkFlows servicesFlow;
	List<String> service;
	
	@BeforeMethod
	public void setup()
	{
		baseNavPage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		beverageServicePage =  new BeverageServicePage(DriverFactory.getDriver());
		servicesPage = new ServicesPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		service = List.of("Beverage","Non Alc Bev");
		basicLogin();
		baseNavPage.salesNewNavigation();

	}

	@Test(groups = {"Regression", "All"})
	public void beveageservice(ITestContext context) throws InterruptedException
	{
	
			servicesFlow.openServiceRequestFromEventListing("DO-76" , service);
	
			Assert.assertTrue(service.stream().anyMatch(s -> servicesPage.getServiceHdr().contains(s)),
					"BeverageService not Mapped/Service not present in the Service list.");
			servicesPage.clickSearchAndAddbtn();
			servicesPage.uncheckIfOutsourcedOrNotRequired();
			beverageServicePage.showMappedItems();
			
			beverageServicePage.enterQuantity();
			servicesPage.clickListSave();
			beverageServicePage.closeInventoryPopupIfPresent();
			servicesPage.clickListClose();
			

			if(beverageServicePage.clickReserveIfPresent())
			{
				System.out.println("Reserved Qty");
				beverageServicePage.closeInventoryPopupIfPresent();
			}
			
			else
				System.out.println("No Reserve Button Available");
			beverageServicePage.validateItems();
			
			servicesFlow.finalizeService("DO-76", service);

		}
	}
