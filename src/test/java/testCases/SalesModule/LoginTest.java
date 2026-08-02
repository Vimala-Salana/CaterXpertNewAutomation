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
		System.out.println("Maven userId: " + System.getProperty("userId"));
		System.out.println("Maven password: " + System.getProperty("password"));
		login.enterCaterId(System.getProperty("url", config.getProperty(env + ".caterid")));
		login.enterUserId(System.getProperty("caterid",config.getProperty(env+".userid")));
		login.enterPassword(System.getProperty("caterid",config.getProperty(env+".password")));
		login.clickLogin();
	}
}
