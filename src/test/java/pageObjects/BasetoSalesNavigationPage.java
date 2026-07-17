package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
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
	public ElementInteractionUtil elementInteractionUtils;
	public BasetoSalesNavigationPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		this.elementInteractionUtils = new ElementInteractionUtil(driver);
	}

	@FindBy(xpath = "//frame[@name='header']") WebElement frameHdr;
	@FindBy(xpath = "//img[@title='Home']") List<WebElement> homeIcon;
	@FindBy(xpath = "//frame[@name='right']") WebElement frameright;
	@FindBy(xpath = "//a[normalize-space(text())='Sales New']") WebElement lnkSalesNew;


	public void salesNewNavigation()
	{
		
		driver.switchTo().frame(frameHdr);
		if(!homeIcon.isEmpty())
		{
			homeIcon.get(0).click();
		}
		driver.switchTo().defaultContent();
		
		driver.switchTo().frame(frameright);
		wait.until(ExpectedConditions.elementToBeClickable(lnkSalesNew));
		lnkSalesNew.click();
		driver.switchTo().defaultContent();
	}
	
	@FindBy(xpath = "//span[text()=' dining ']") WebElement baseNavigation;
	public void navigateTokitchen()
	{
		baseNavigation.click();
		WebElement frameright = driver.findElement(By.xpath("//frame[@name='right']"));
		driver.switchTo().frame(frameright);
		driver.findElement(By.xpath("//a[normalize-space(text())='Kitchen New']")).click();
		driver.switchTo().defaultContent();
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));

	}
	
	
}
