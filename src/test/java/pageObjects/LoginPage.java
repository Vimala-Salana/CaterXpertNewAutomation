package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import testBase.BasePage;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver)
	{
		super(driver);
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
	public void clickGo()
	{
		elementUtil.click(btnGo);
	}
	
	public void login(String caterId,String userId, String password)
	{
		enterCaterId(caterId);
		enterUserId(userId);
		enterPassword(password);
		clickGo();
	}
}
