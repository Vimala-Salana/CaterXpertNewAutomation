package testCases.SalesModule;

import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.MenuServicePage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class MenuServiceTest extends BaseClass
{
	
	BasetoSalesNavigationPage baseNavPage;
	MenuServicePage menuServicePage;
	ServicesWorkFlows servicesFlow;
	List<String> service;
	@BeforeMethod
	public void setup()
	{
		baseNavPage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		menuServicePage =  new MenuServicePage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		service = List.of("Menu");
		basicLogin();
		baseNavPage.salesNewNavigation();

	}
	@Test(groups = {"Regression", "All"})
	public void menuServiceRequest(ITestContext context) throws InterruptedException
	{
		servicesFlow.openServiceRequestFromEventListing("428", service);
		menuServicePage.addMenuItems();
		servicesFlow.finalizeService("428", service);
	}
}

