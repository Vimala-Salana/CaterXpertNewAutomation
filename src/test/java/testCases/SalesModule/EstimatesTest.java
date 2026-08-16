package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import apis.EventListApi;
import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.EstimatesPage;
import pageObjects.EventDashboardPage;
import testBase.BaseClass;
import workFlows.ServicesWorkFlows;

public class EstimatesTest extends BaseClass{
	
	BasetoSalesNavigationPage baseNavPage;
	EventDashboardPage dashboardPage;
	EstimatesPage estimatesPage;
	ServicesWorkFlows servicesFlow;
	EventListApi eventListApi;
	List<String> service;
	List<String> iconLabel;
	
	@BeforeMethod
	public void setup()
	{
		estimatesPage = new EstimatesPage(DriverFactory.getDriver());
		dashboardPage = new EventDashboardPage(DriverFactory.getDriver());
		servicesFlow = new ServicesWorkFlows(DriverFactory.getDriver());
		eventListApi = new EventListApi();
		service = List.of("Estimates");
		iconLabel = List.of("Estimates Lite","Estimates");

	}
	
	@Test (priority=1, groups = {"Regression", "EventCreationDirect"})
	public void estimates()
	{
		String eventNo =  eventListApi.getAllNewServicesEventId(loginId);
		servicesFlow.navigateToEventDashboard(eventNo);
		dashboardPage.clickServiceLabelIcon(service, null, iconLabel);
		
		//System.out.println(estimates.getEstimateshdr());
		
		if(estimatesPage.getEstimateshdr())
		{
			estimatesPage.giveEstimates();
			estimatesPage.clickTotalEstimates();
			estimatesPage.selectTotalEstimateOptions();
			
			Assert.assertEquals(estimatesPage.getEstimatesTotals(), estimatesPage.getActualTotal(),"Actual and Calculated Totals are not Same");
			estimatesPage.saveTotalEstimates();
			estimatesPage.closeTotalEstimates();
		}
		else
		{
			estimatesPage.clickEstimateLiteSave();
			estimatesPage.clickEstimateLiteClose();
		}
	}

	@AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
