package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
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
	WebDriverWait shortWait;
	public EventListingPage(WebDriver driver)
	{
		this.driver =driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		waitutil = new WaitUtils(driver);
		shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}



	@FindBy(xpath = "//input[@placeholder='Event #']") WebElement searchEvent;

	public void enterEventNo(String eventNo)
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.visibilityOf(searchEvent));
		//searchEvent.clear();
		searchEvent.sendKeys(eventNo, Keys.ENTER);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		waitutil.waitForOverlay();
	}

	//@FindBy(xpath = "//span[text()=' dashboard ']") WebElement iconEventDashboard;
	By iconEventDashboard = By.xpath("//span[text()=' dashboard ']");

	public void clickEventDashboardIcon(String eventNo) {
		waitutil.waitForOverlay();

		By dashboardIcon = By.xpath("//tr[.//span[contains(normalize-space(),'" + eventNo + "')]]//span[@ptooltip='Event Dashboard']");

		List<WebElement> dashboard = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dashboardIcon));
		dashboard.get(0).click();
	}

	public void EventDashboardNavigation(String eventNo)
	{
		/*
		 * HambergerMenuPage aepage = new HambergerMenuPage(driver);
		 * aepage.clickhambergerMenu(); aepage.clickEventListinglnk();
		 */

		EventListingPage eventlist = new EventListingPage(driver);
		eventlist.enterEventNo(eventNo);
		waitutil.waitForOverlay();
		eventlist.clickEventDashboardIcon(eventNo);
	}

	@FindBy(xpath = "//span[text()='  Sent  ']") WebElement lnkServiceStatus;

	public void clickonServiceStatus()
	{
		lnkServiceStatus.click();
	}

	@FindBy(xpath = "//p[contains(normalize-space(),'Inventory Status Alert')]") List<WebElement> hdrInventoryAlert;
	@FindBy(xpath = "//button[.=' Close ']") WebElement inventoryAlertClose;
	public void closeInventoryPopupIfPresent()
	{
		
		try
		{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
			//waitutil.waitForOverlay();
			shortWait.until(ExpectedConditions.visibilityOfAllElements(hdrInventoryAlert));
			shortWait.until(ExpectedConditions.elementToBeClickable(inventoryAlertClose));
			inventoryAlertClose.click();
			shortWait.until(ExpectedConditions.invisibilityOfAllElements(hdrInventoryAlert));

		}
		catch (TimeoutException | StaleElementReferenceException e)
		{
			System.out.println("Inventory popup not displayed");
		}
	}
}
