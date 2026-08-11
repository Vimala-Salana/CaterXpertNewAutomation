package workFlows;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import pageObjects.CreateCustomerPage;
import pageObjects.CustomerOrPotentialCustomerListPage;
import pageObjects.HambergerMenuPage;
import utilities.ExcelUtility;

public class CustomerFlow{

	ExcelUtility excelUtil;
	private CreateCustomerPage customerPage;
	private HambergerMenuPage hamburgerMenuPage;
	private CustomerOrPotentialCustomerListPage customerListPage;

	public CustomerFlow(WebDriver driver)
	{
		customerPage = new CreateCustomerPage(driver);
		hamburgerMenuPage = new HambergerMenuPage(driver);
		customerListPage = new CustomerOrPotentialCustomerListPage(driver);
	}

	public void navigateToCreateCustomer()
	{
		hamburgerMenuPage.navigateToCreateCustomer();
		customerListPage.ClickNewCustomer();

	}

	public void createCustomer(Map<String, String> data)
	{
		customerPage.fillCustomerMandatoryfields(data);
		customerPage.clickCreatebtn();
	}
}
