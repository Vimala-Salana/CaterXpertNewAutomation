package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.ConfigReader;
import utilities.ElementInteractionUtil;

public class LoginPage {

	public WebDriver driver;
	public ConfigReader config = new ConfigReader();
	ElementInteractionUtil elementInteractionUtils;


	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
		this.elementInteractionUtils = new ElementInteractionUtil(driver);
	}


	@FindBy(xpath = "//input[@id='CatererId']") WebElement txtCaterId;
	@FindBy(xpath = "//input[@id='UserName']") WebElement txtUserId;
	@FindBy(xpath = "//input[@id='Password']") WebElement txtPassword;
	@FindBy(xpath = "//input[@value='Go']") WebElement btnGo;

	public void enterCaterId()
	{
		txtCaterId.sendKeys(config.getProperty("caterid"));
	}
	public void enterUserID()
	{
		txtUserId.sendKeys(config.getProperty("userid"));
	}
	public void enterPassword()
	{
		txtPassword.sendKeys(config.getProperty("password"));
	}
	public void clickLogin()
	{
		elementInteractionUtils.click(btnGo);
		//btnLogin.click();
	}
}
