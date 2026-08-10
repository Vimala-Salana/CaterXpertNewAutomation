package testBase;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import factory.DriverFactory;
import utilities.ConfigReader;
import utilities.ElementInteractionUtil;
import utilities.ServiceUtil;
import utilities.WaitUtils;

public class BasePage {
	protected WebDriver driver;
	protected ConfigReader config = new ConfigReader();
	protected WebDriverWait wait; 
	protected WaitUtils waitutil;
	protected JavascriptExecutor js;
	protected ServiceUtil serviceUtil;
	protected ElementInteractionUtil elementUtil;
	
	public BasePage(WebDriver driver)
	{  
		
		this.driver = driver;	
		PageFactory.initElements(driver,this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver,config.getDuration("explicitWait"));
		elementUtil = new ElementInteractionUtil(driver);
		js = (JavascriptExecutor) driver;
		serviceUtil = new ServiceUtil(driver);
	}
}
