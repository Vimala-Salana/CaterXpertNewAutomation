package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ServiceUtil;
import utilities.WaitUtils;

public class StaffingServicePage {

	public WebDriver driver;
	WaitUtils waitutil;
	public WebDriverWait wait;
	ServiceUtil serviceUtil;
	public StaffingServicePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		serviceUtil = new ServiceUtil(driver);
	}

	@FindBy(xpath = "//span[contains(normalize-space(text()),'Event Services')]") WebElement Staffinghdr;
	public String getStaffingServiceHdr()
	{
		return Staffinghdr.getText();
	}
	
	@FindBy(xpath = "//input[@maxlength='5']") List<WebElement> staffQty;
	public void giveStaffQty()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.visibilityOfAllElements(staffQty));
		int count = 1;
		for(WebElement qty :staffQty)
		{
			if(count<=5)
			{
				wait.until(ExpectedConditions.elementToBeClickable(qty));
				qty.clear();
				qty.sendKeys("1");
				count++;
			}
		}
	}

	@FindBy(xpath = "//button[text()=' Save ']") WebElement btnSave;

	public void clickSave() throws InterruptedException
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(btnSave));
		btnSave.click();
		waitutil.waitForOverlay();
	}

	@FindBy(xpath = "//button[text()=' Finalize ']") WebElement btnFinalize;

	public void clickFinalize()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(btnFinalize));
		btnFinalize.click();
		waitutil.waitForOverlay();
	}

	public boolean staffingConstraints()
	{
		return serviceUtil.Constraints();
	}
	
	public void staffingInfo()
	{
		serviceUtil.Info();
	}

	@FindBy(xpath = "(//button[text()=' Close '])[2]") WebElement staffclosebtn;

	public void clickStaffClose()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(staffclosebtn));
		staffclosebtn.click();
	}
	
	public void approveStaffingConstraints(boolean constraintsExists, String eventNo) throws InterruptedException
	{
		serviceUtil.approveConstraints(constraintsExists, eventNo);
		EventListingPage eventlist = new EventListingPage(driver);
		eventlist.EventDashboardNavigation(eventNo);
	}
}
