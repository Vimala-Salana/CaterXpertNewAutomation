package testCases.SalesModule;
import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import components.HambergerMenuPage;
import components.HeaderPage;
import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.CreateEventPage;
import testBase.BaseClass;
import utilities.ExcelUtility;
import workFlows.EventFlow;

public class CreateEventTest extends BaseClass{
	
	BasetoSalesNavigationPage baseNavPage;
	EventFlow eventFlow;
	ExcelUtility excelUtil;
	String sheetname;
	CreateEventPage eventPage;
	Map<String, String> data;
	@BeforeMethod
	public void setup()
	{
		baseNavPage = new BasetoSalesNavigationPage(DriverFactory.getDriver());
		eventFlow =  new EventFlow(DriverFactory.getDriver());
		
		basicLogin();
		baseNavPage.salesNewNavigation();
		
		excelUtil = new ExcelUtility(filepath);
		sheetname = "Create Event";
		data = excelUtil.getMandatoryFieldData(sheetname);
	}
	
	@Test(groups = {"Regression", "EventCreationDirect"})
	public void createEventDirect()
	{
		eventFlow.navigatetoCreateEvent();
	}
	 
	@Test(groups = {"Regression", "All"})
	public void createEventFromEventPage(ITestContext context) throws Exception
	{
		eventPage = new CreateEventPage(DriverFactory.getDriver());
		//validating CreateEvent header
		//Assert.assertEquals(eventPage.getCreateEventhdr(), "Create Event");
		
		String eventNo = eventFlow.createEventfromEventPage(data);
		context.setAttribute("eventNo", eventNo);   //To use Event Number in other classes
	}
			
}
