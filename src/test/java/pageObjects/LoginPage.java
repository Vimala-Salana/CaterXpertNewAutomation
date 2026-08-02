package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigReader;
import utilities.ElementInteractionUtil;

public class LoginPage {

	ElementInteractionUtil elementUtil;

	public LoginPage(WebDriver driver)
	{
		this.elementUtil = new ElementInteractionUtil(driver);
	}

	private final By txtCaterId = By.xpath("//input[@id='CatererId']");
	private final By txtUserId = By.xpath("//input[@id='UserName']");
	private final By txtPassword = By.xpath("//input[@id='Password']");

	private final By btnGo = By.xpath("//input[@value='Go']");

	public void enterCaterId(String caterId)
	{

		elementUtil.typeText(txtCaterId, caterId);
	}
	public void enterUserId(String userId)
	{
		elementUtil.typeText(txtUserId, userId);

	}
	public void enterPassword(String password)
	{
		elementUtil.typeText(txtPassword,password );

	}
	public void clickLogin()
	{
		elementUtil.click(btnGo);
	}
}
