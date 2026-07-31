package testCases.SalesModule;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.WaitUtils;

public class LoginTest extends BaseClass{

	@Test(priority = 1, groups = {"Regression", "All"})
	public void login()
	{
		//System.out.println("Driver value: " + DriverFactory.getDriver());
		LoginPage login = new LoginPage(DriverFactory.getDriver());
		login.enterCaterId();
		login.enterUserId();
		login.enterPassword();
		login.clickLogin();
	}
}
