package testCases.SalesModule;

import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.HambergerMenuPage;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.CreateEventPage;
import pageObjects.EventDashboardPage;
import pageObjects.MenuServicePage;
import testBase.BaseClass;
import utilities.ExcelUtility;
import workFlows.EventFlow;
import workFlows.MenuServiceFlow;

public class MenuServiceTest extends BaseClass
{
	
	BasetoSalesNavigationPage baseNavPage;
	MenuServiceFlow menuFlow;
	@BeforeMethod
	public void setup()
	{
		baseNavPage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		menuFlow =  new MenuServiceFlow(DriverFactory.getDriver());
		
		basicLogin();
		baseNavPage.salesNewNavigation();

	}
	@Test(groups = {"Regression", "All"})
	public void menuServiceRequest(ITestContext context) throws InterruptedException
	{

		menuFlow.openMenuServiceFromEventListing("DO-136");
		menuFlow.addMenuItems();
		menuFlow.finalizeservice("DO-136");

	}
}

