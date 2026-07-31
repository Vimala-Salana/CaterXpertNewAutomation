package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigReader;
import utilities.ElementInteractionUtil;

public class LoginPage {

	private final ConfigReader config;
	ElementInteractionUtil elementUtil;


	public LoginPage(WebDriver driver)
	{
		this.config = new ConfigReader();
		this.elementUtil = new ElementInteractionUtil(driver);
	}

	private static final By TXT_CATER_ID = By.xpath("//input[@id='CatererId']");
	private static final By TXT_USER_ID = By.xpath("//input[@id='UserName']");
	private static final By TXT_PASSWORD = By.xpath("//input[@id='Password']");

	private static final By BTN_GO = By.xpath("//input[@value='Go']");

	public void enterCaterId()
	{

		elementUtil.typeText(TXT_CATER_ID,config.getProperty("caterid") );
	}
	public void enterUserId()
	{
		elementUtil.typeText(TXT_USER_ID,config.getProperty("userid") );

	}
	public void enterPassword()
	{
		elementUtil.typeText(TXT_PASSWORD,config.getProperty("password") );

	}
	public void clickLogin()
	{
		elementUtil.click(BTN_GO);
	}
}
