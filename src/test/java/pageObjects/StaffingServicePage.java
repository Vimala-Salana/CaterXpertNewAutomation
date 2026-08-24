package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
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

	@FindBy(xpath = "//td[count(//th[text()='Qty']/preceding-sibling::th)+1]//input[@type='text']")
	List<WebElement> staffQty;

	public void addStaffPositions() {
		waitutil.waitForOverlay();
		for (int i = 0; i < Math.min(staffQty.size(), 5); i++) {
			wait.until(ExpectedConditions.elementToBeClickable(staffQty.get(i)));
			staffQty.get(i).clear();
			staffQty.get(i).sendKeys("1");
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

	private final By txtAddRows = By.id("StaffRows");
	private final By btnAddRows = By.xpath("//button[normalize-space()='Add Rows']");

	public void addRows(String noOfRows) {
		elementUtil.typeText(txtAddRows, noOfRows);
		elementUtil.click(btnAddRows);
	}

	private final By drpPosition = By.xpath("//span[text()='-Select-']");
	private final By lstPosition = By.xpath("(//li[@role='option'])");

	public void addNewposition() {
		List<WebElement> positions = driver.findElements(drpPosition);
		for (int i = 0; i < positions.size(); i++) {
			positions.get(i).click();
			List<WebElement> positionslist = driver.findElements(lstPosition);
			positionslist.get(i).click();
		}
		staffQty.get(staffQty.size() - 1).sendKeys("1");

		/*
		 * if (driver.findElements(lstPosition).size() > 5) { positions.get(5).click();
		 * LoggerManager.info("Selected 6th Position"); } else {
		 * positions.get(0).click(); LoggerManager.
		 * info("Less than 6 positions available, So Selected 1st Position"); }
		 */
	}
}
