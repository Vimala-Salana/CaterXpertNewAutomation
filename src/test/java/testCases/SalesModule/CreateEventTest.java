package testCases.SalesModule;

import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import factory.DriverFactory;
import testBase.BaseClass;
import utilities.DataGenerator;
import utilities.ExcelUtility;
import workFlows.EventFlow;

public class CreateEventTest extends BaseClass {

	EventFlow eventFlow;
	ExcelUtility excelUtil;
	String sheetname;
	Map<String, String> data;
	Map<String, String> eventData;

	@BeforeMethod
	public void setup() {
		eventFlow = new EventFlow(DriverFactory.getDriver());

		excelUtil = new ExcelUtility(filepath);
		sheetname = "Create Event";
		data = excelUtil.getMandatoryFieldData(sheetname);

		DataGenerator dataGenerator = new DataGenerator();
		eventData = dataGenerator.generate(eventJsonPath, "EventTest");
	}

	@Test(groups = { "Regression", "All" })
	public void createEventFromEventPage() {

		eventFlow.createEventfromEventPage(eventData);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}
