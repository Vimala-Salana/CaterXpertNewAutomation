package testCases.SalesModule;

import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.BasetoSalesNavigationPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.WaitUtils;

public class LoginTest extends BaseClass{

	@Test(priority = 1)
	public void login()
	{
		LoginPage login = new LoginPage(DriverFactory.getDriver());
		login.enterCaterId();
		login.enterUserID();
		login.enterPassword();
		login.clickLogin();
	}
}
