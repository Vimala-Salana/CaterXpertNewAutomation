package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import testBase.BasePage;
import utilities.LoggerManager;

public class BaseToSalesNavigationPage extends BasePage {

	public BaseToSalesNavigationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//frame[@name='header']")
	WebElement frameHdr;
	@FindBy(xpath = "//img[@title='Home']")
	List<WebElement> homeIcon;
	@FindBy(xpath = "//frame[@name='right']")
	WebElement frameright;
	// @FindBy(xpath = "//a[normalize-space(text())='Sales New']") WebElement
	// lnkSalesNew;

	private final By iconHome = By.xpath("//img[@title='Home']");

	private final By lnkSalesNew = By.xpath("//a[normalize-space(text())='Sales New']");

	public By userId = By.name("userid");

	public String getLoggedInUserId() {
		String userid = elementUtil.getValue(userId);
		LoggerManager.info("Logged in User Id is : " + userid);
		return userid;
	}

	public String url;

	public String salesNewNavigation() {
		wait.until(ExpectedConditions.visibilityOf(frameHdr));
		driver.switchTo().frame(frameHdr);
		elementUtil.clickIfPresent(iconHome);

		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameright));

		String loginId = getLoggedInUserId();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(lnkSalesNew));
		url = elementUtil.getAttribute(lnkSalesNew, "onClick").split("'")[1];
		elementUtil.click(lnkSalesNew);

		LoggerManager.info("Sales New navigation url is : " + url);
		driver.switchTo().defaultContent();
		return loginId;
	}

	public String getSalesUrl() {
		return url;
	}

	private final By lnkBaseNavigation = By.xpath("//span[text()=' dining ']");
	private final By lnkKitchenNew = By.xpath("//a[normalize-space(text())='Kitchen New']");

	public void navigateTokitchen() {
		elementUtil.click(lnkBaseNavigation);
		driver.switchTo().frame(frameright);
		elementUtil.click(lnkKitchenNew);
		driver.switchTo().defaultContent();
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));

	}

}
