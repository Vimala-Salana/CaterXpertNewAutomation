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
		LoginPage login = new LoginPage(DriverFactory.getDriver());
		login.enterCaterId(System.getProperty("caterid", config.getProperty(env + ".caterid")));
		login.enterUserId(System.getProperty("userid",config.getProperty(env+".userid")));
		login.enterPassword(System.getProperty("password",config.getProperty(env+".password")));
		login.clickGo();
	}
}
