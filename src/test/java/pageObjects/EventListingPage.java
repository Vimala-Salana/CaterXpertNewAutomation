package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import testBase.BasePage;
import utilities.LoggerManager;

public class EventListingPage extends BasePage {

	public EventListingPage(WebDriver driver) {
		super(driver);
	}

	By searchEvent = By.xpath("//input[@placeholder='Event #']");

	public void enterEventNo(String eventNo) {
		waitutil.waitForOverlay();

		LoggerManager.info("Searched Event No : " + eventNo);
		wait.until(ExpectedConditions.elementToBeClickable(searchEvent));
		elementUtil.typeText(searchEvent, eventNo);
		waitutil.waitForOverlay();
	}

	By iconEventDashboard = By.xpath("//span[text()=' dashboard ']");

	public void clickEventDashboardIcon(String eventNo) {

		// elementUtil.click(iconEventDashboard);
		waitutil.waitForOverlay();

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
	private final By inventoryAlertClose = By.xpath("//button[.=' Close ']");

	public void closeInventoryPopupIfPresent() {

		waitutil.waitForOverlay();

		try {
			shortWait.until(ExpectedConditions.visibilityOfAllElements(hdrInventoryAlert));

			elementUtil.click(inventoryAlertClose);

		} catch (TimeoutException e) {

			System.out.println("Inventory popup not displayed");
		}
	}

}
