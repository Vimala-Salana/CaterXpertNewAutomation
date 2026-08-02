package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ElementInteractionUtil;
import utilities.WaitUtils;

public class BasetoSalesNavigationPage {
	public WebDriver driver;
	WebDriverWait wait;
	WaitUtils waitutil;
	public ElementInteractionUtil elementUtil;
	public BasetoSalesNavigationPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.elementUtil = new ElementInteractionUtil(driver);
	}

	@FindBy(xpath = "//frame[@name='header']") WebElement frameHdr;
	@FindBy(xpath = "//img[@title='Home']") List<WebElement> homeIcon;
	@FindBy(xpath = "//frame[@name='right']") WebElement frameright;
	//@FindBy(xpath = "//a[normalize-space(text())='Sales New']") WebElement lnkSalesNew;
	
	
	private final By frmHeader  = By.xpath("//frame[@name='header']");

	private final By iconHome = By.xpath("//img[@title='Home']");

	private final By frmRight = By.xpath("//frame[@name='right']");

	private final By lnkSalesNew = By.xpath("//a[normalize-space(text())='Sales New']");
	


	public void salesNewNavigation()
	{
		
		driver.switchTo().frame(frameHdr);
		elementUtil.clickIfPresent(iconHome);
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frameright);
		
		elementUtil.click(lnkSalesNew);
		driver.switchTo().defaultContent();
	}
	
	private final By lnkBaseNavigation = By.xpath("//span[text()=' dining ']");
	private final By lnkKitchenNew = By.xpath("//a[normalize-space(text())='Kitchen New']");
	
	public void navigateTokitchen()
	{
		elementUtil.click(lnkBaseNavigation);
		driver.switchTo().frame(frameright);
		elementUtil.click(lnkKitchenNew);
		driver.switchTo().defaultContent();
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));

	}
	
	
}
