package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import testBase.BasePage;
import workFlows.ServicesWorkFlows;

public class StaffingServicePage extends BasePage {

	private ServicesPage servicesPage;
	private ServicesWorkFlows servicesFlow;

	public StaffingServicePage(WebDriver driver) {
		super(driver);
		servicesPage = new ServicesPage(driver);
		servicesFlow = new ServicesWorkFlows(driver);
	}

	@FindBy(xpath = "//span[contains(normalize-space(text()),'Event Services')]")
	WebElement staffinghdr;

	public String getStaffingServiceHdr() {
		return staffinghdr.getText();
	}

	@FindBy(xpath = "//input[@maxlength='5']")
	List<WebElement> staffQty;

	public void addStaffPositions() {
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.visibilityOfAllElements(staffQty));
		int count = 1;
		for (WebElement qty : staffQty) {
			if (count <= 5) {
				wait.until(ExpectedConditions.elementToBeClickable(qty));
				qty.clear();
				qty.sendKeys("1");
				count++;
			}
		}
		servicesPage.clickServiceSave();

	}

	@FindBy(xpath = "//div[@role='tabpanel' and (@aria-hidden='false')]//button[text()=' Save ']")
	WebElement btnSave;

	public void clickSave() {
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(btnSave));
		btnSave.click();
		waitutil.waitForSwalPopup();

	}

	@FindBy(xpath = "//button[text()=' Finalize ']")
	WebElement btnFinalize;

	public void clickFinalize() {
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(btnFinalize));
		btnFinalize.click();

	}

	public boolean staffingConstraints() {
		return serviceUtil.Constraints();
	}

	public void staffingInfo() {
		serviceUtil.Info();
	}

	@FindBy(xpath = "(//button[text()=' Close '])[2]")
	WebElement staffclosebtn;

	public void clickStaffClose() {
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(staffclosebtn));
		staffclosebtn.click();
	}

	public void approveStaffingConstraints(String eventNo) {
		serviceUtil.approveConstraints(eventNo);
		servicesFlow.navigateToEventDashboard(eventNo);
	}
}
