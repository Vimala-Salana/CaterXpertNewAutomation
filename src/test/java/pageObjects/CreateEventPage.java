package pageObjects;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.BasePage;
import utilities.LoggerManager;
import utilities.MandatoryLabelsUtil;
import utilities.ServiceUtil;
import utilities.WaitUtils;
import workFlows.ServicesWorkFlows;

public class CreateEventPage extends BasePage {

	String filepath;
	String sheetname;
	ServiceUtil serviceutil;
	public String eventNo;
	WebDriverWait shortWait;

	public CreateEventPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		serviceutil = new ServiceUtil(driver);
		shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
	}

	@FindBy(xpath = "//span[normalize-space(text())='Create Event']")
	WebElement hdrCreateEvent;

	public String getCreateEventhdr() {
		waitutil.waitForOverlay();
		return hdrCreateEvent.getText();
	}

	public void fillEventMandatoryfields(Map<String, String> data) {
		MandatoryLabelsUtil.fillMandatoryFields(driver, data);
	}

	private final By btnCreate = By.xpath("//button[normalize-space()='Create']");

	public void clickCreatebtn() {
		elementUtil.click(btnCreate);
	}

	private final By txtTaxExpiryPopUp = By.xpath("//div[contains(text(),'Tax Exempt Certificate')]");
	private final By alertYes = By.xpath("//button[text()='Yes']");

	public void clickYesInTaxExpiryPopupifExists() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
			// waitutil.waitForOverlay();
			shortWait.until(ExpectedConditions.visibilityOfElementLocated(txtTaxExpiryPopUp));
			elementUtil.click(alertYes);
		} catch (TimeoutException | StaleElementReferenceException e) {
			LoggerManager.info("Tax Exipry popup not displayed");
		}
	}

	@FindBy(xpath = "(//button[text()=' Close '])[2]")
	WebElement btnClose;

	public void clickClosebtn() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.elementToBeClickable(btnClose)).click();
	}

	public boolean eventConstraints() {
		waitutil.waitForOverlay();
		return serviceutil.Constraints();
	}

	// @FindBy(xpath ="//label[text()=' Event # ']//following-sibling::label[2]")
	// WebElement eventNumlocator;
	private final By eventNumlocator = By.xpath("//label[text()=' Event # ']//following-sibling::label[2]");

	public String getEventNo() {
		// waitutil.waitForOverlay();
		eventNo = elementUtil.getText(eventNumlocator);
		return eventNo;
	}

	public void ApproveEventConstraints(String eventNo) {
		serviceutil.approveConstraints(eventNo);
		ServicesWorkFlows servicesFlow = new ServicesWorkFlows(driver);
		servicesFlow.navigateToEventDashboard(eventNo);
	}

}
