package testCases.SalesModule;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.EstimatesPage;
import pageObjects.EventDashboardPage;
import pageObjects.MenuServicePage;
import testBase.BaseClass;
import utilities.ServiceUtil;
import utilities.WaitUtils;

public class BeverageServiceTest extends BaseClass{

	@Test
	public void beveageservice() throws InterruptedException
	{
		EventDashboardPage dashboard = new EventDashboardPage(DriverFactory.getDriver());


		List<String> service = List.of("Beverage");
		List<String> status = List.of("New","Prog","Resent");
		List<String> iconlabel = List.of("Service Request");
		if(dashboard.clickServiceLabelIcon(service, status, iconlabel))
		{
				//mp.clickSearchAndAddbtn();
				EstimatesPage estimates = new EstimatesPage(DriverFactory.getDriver());
				estimates.validation();
				
			

		}
	}
}
