package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import testBase.BasePage;

public class ChangeRequestPage extends BasePage {

	public ChangeRequestPage(WebDriver driver) {
		super(driver);
	}

	private final By drpOrder = By.xpath("//span[text()='-Select Order-']");
	private final By lstOrder = By.xpath("//li[@role='option']");

	public void selectOrder() {
		elementUtil.click(drpOrder);
		driver.findElements(lstOrder).stream().findFirst().ifPresent(WebElement::click);
	}

	private final By txtSentComments = By.xpath("//textarea[@name='sentComm']");

	public void enterSentComments(String comments) {
		elementUtil.typeText(txtSentComments, comments);
	}

	private final By btnAddOrEditItems = By.xpath("//span[text()=' Add/Edit Items']");

	public void clickAddOrEditItems() {
		elementUtil.click(btnAddOrEditItems);
	}

	private final By inputQty = By
			.xpath("(//div[@aria-hidden='false'])[1]//td[count(//p-tabpanel[contains(@header,'Current') "
					+ "or contains(@header,'Request')]//th[contains(.,' Qty ')]/preceding-sibling::th)+1]//input");

	// (//td[count(//p-tabpanel[@header='Current Menu']//th[.=' Qty
	// ']/preceding-sibling::th)+1])[1]//input
	public void enterQty(String qty) {
		elementUtil.typeText(inputQty, qty);
	}

	private final By btncurrentTabSave = By.xpath("//button[normalize-space()='Save']");

	public void saveCurrentTab() {
		elementUtil.click(btncurrentTabSave);
		waitutil.waitForSwalPopup();
	}

	private final By btnCurrentTaClose = By.xpath(
			"//p-tabpanel[contains(@header,'Current') " + "or contains(@header,'Request')]//button[text()=' Close ']");

	public void closeCurrentTab() {
		waitutil.waitForOverlay();
		waitutil.waitForSwalPopup();
		elementUtil.click(btnCurrentTaClose);
	}

	private final By btnChangeRequestSend = By.xpath("//button[normalize-space()='Send']");

	public void sendChangeRequest() {
		elementUtil.click(btnChangeRequestSend);
		waitutil.waitForSwalPopup();
	}

	private final By txtStatus = By.xpath(
			"((//table[contains(@class,'visit')])[1]//td[count(//th[normalize-space()='Status']/preceding-sibling::th)+1])[last()]");

	public String getChangeRequestStatus() {
		waitutil.waitForOverlay();
		waitutil.waitForSwalPopup();
		return elementUtil.getText(txtStatus);
	}

	private final By btnChangeRequestClose = By.xpath("(//button[text ()=' Close '])[1]");

	public void closeChangeRequest() {
		elementUtil.click(btnChangeRequestClose);
	}
}
