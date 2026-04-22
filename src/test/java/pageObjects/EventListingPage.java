package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.WaitUtils;

public class EventListingPage {

	WebDriver driver;
	WebDriverWait wait;
	WaitUtils waitutil;
	public EventListingPage(WebDriver driver)
	{
		this.driver =driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		waitutil = new WaitUtils(driver);
	}
	
	
	
	@FindBy(xpath = "//input[@placeholder='Event #']") WebElement searchEvent;
	
	public void enterEventNo(String EventNo)
	{
		waitutil.waitForOverlay();
	    wait.until(ExpectedConditions.visibilityOf(searchEvent));
	    //searchEvent.clear();
		searchEvent.sendKeys(EventNo,Keys.ENTER);
		waitutil.waitForOverlay();
	}
	
	//@FindBy(xpath = "//span[text()=' dashboard ']") WebElement iconEventDashboard;
	By iconEventDashboard = By.xpath("//span[text()=' dashboard ']");
	
	public void clickEventDashboardicon() throws InterruptedException
	{
		waitutil.waitForOverlay();
		WebElement dashboard = wait.until(ExpectedConditions.elementToBeClickable(iconEventDashboard));
		dashboard.click();
	}
	
	public void EventDashboardNavigation(String eventNo) throws InterruptedException
	{
		AEDashboardPage aepage = new AEDashboardPage(driver);
		aepage.clickhambergerMenu();
		aepage.clickEventListinglnk();
		
		EventListingPage eventlist = new EventListingPage(driver);
		eventlist.enterEventNo(eventNo);
		eventlist.clickEventDashboardicon();
	}
	
	@FindBy(xpath = "//span[text()='  Sent  ']") WebElement lnkServiceStatus;
	
	public void clickonServiceStatus()
	{
		lnkServiceStatus.click();
	}
}
