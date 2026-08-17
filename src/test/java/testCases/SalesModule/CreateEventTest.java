package testCases.SalesModule;

import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.CreateEventPage;
import testBase.BaseClass;
import utilities.DataGenerator;
import utilities.ExcelUtility;
import workFlows.EventFlow;

public class CreateEventTest extends BaseClass {

	BasetoSalesNavigationPage baseNavPage;
	EventFlow eventFlow;
	ExcelUtility excelUtil;
	String sheetname;
	CreateEventPage eventPage;
	Map<String, String> data;
	Map<String, String> eventData;

	@BeforeMethod
	public void setup() {
		eventFlow = new EventFlow(DriverFactory.getDriver());

		excelUtil = new ExcelUtility(filepath);
		sheetname = "Create Event";

		DataGenerator dataGenerator = new DataGenerator();
		eventData = dataGenerator.generate(eventJsonPath, "EventTest");
		data = excelUtil.getMandatoryFieldData(sheetname);
	}

	@Test(groups = { "Regression", "All" })
	public void createEventFromEventPage() throws Exception {
		eventPage = new CreateEventPage(DriverFactory.getDriver());

		eventFlow.createEventfromEventPage(eventData);
		// context.setAttribute("eventNo", eventNo); //To use Event Number in other
		// classes
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}
