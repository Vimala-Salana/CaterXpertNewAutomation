package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.BasePage;
import utilities.LoggerManager;

public class EventListingPage extends BasePage {

	WebDriverWait shortWait;

	public EventListingPage(WebDriver driver) {
		super(driver);
		shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
	}

	// @FindBy(xpath = "//input[@placeholder='Event #']") WebElement searchEvent;
	By searchEvent = By.xpath("//input[@placeholder='Event #']");

	public void enterEventNo(String eventNo) {
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.overlay")));

		// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		waitutil.waitForOverlay();

		LoggerManager.info("Searched Event No : " + eventNo);
		wait.until(ExpectedConditions.elementToBeClickable(searchEvent));
		// searchEvent.sendKeys(eventNo);
		elementUtil.typeText(searchEvent, eventNo);
		waitutil.waitForOverlay();
	}

	// @FindBy(xpath = "//span[text()=' dashboard ']") WebElement
	// iconEventDashboard;
	By iconEventDashboard = By.xpath("//span[text()=' dashboard ']");

	public void clickEventDashboardIcon(String eventNo) {

		// elementUtil.click(iconEventDashboard);

		By dashboardIcon = By
				.xpath("//tr[.//span[(normalize-space()='" + eventNo + "')]]//span[@ptooltip='Event Dashboard']");

		List<WebElement> dashboard = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dashboardIcon));
		dashboard.get(0).click();

	}

	@FindBy(xpath = "//span[text()='  Sent  ']")
	WebElement lnkServiceStatus;

	public void clickonServiceStatus() {
		lnkServiceStatus.click();
	}

	@FindBy(xpath = "//p[contains(normalize-space(),'Inventory Status Alert')]")
	List<WebElement> hdrInventoryAlert;
	// @FindBy(xpath = "//button[.=' Close ']") WebElement inventoryAlertClose;
	private final By inventoryAlertClose = By.xpath("//button[.=' Close ']");

	public void closeInventoryPopupIfPresent() {

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
			// waitutil.waitForOverlay();
			shortWait.until(ExpectedConditions.visibilityOfAllElements(hdrInventoryAlert));
			elementUtil.click(inventoryAlertClose);
		} catch (TimeoutException | StaleElementReferenceException e) {
			LoggerManager.info("Inventory popup not displayed");
		}
	}
}
