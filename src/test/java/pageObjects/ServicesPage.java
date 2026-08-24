package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import testBase.BasePage;

public class ServicesPage extends BasePage {

	public ServicesPage(WebDriver driver) {
		super(driver);
	}

	private final By btnSearchAndAdd = By.xpath("//button[text()=' Search & Add ']");
	private final By iconFilter = By.xpath("//span[@ptooltip='Filter']");
	private final By btnFilterGo = By.xpath("//button[normalize-space(text())='Go']");

	private final By hdrService = By.xpath("//span[contains(text(),'Event Services')]");

	public String getServiceHdr() {
		waitutil.waitForOverlay();
		return elementUtil.getText(hdrService);
	}

	public void clickSearchAndAddbtn() {
		elementUtil.click(btnSearchAndAdd);
	}

	@FindBy(xpath = "//div[contains(@class,'d-flex me-2')]//div[contains(@class,'p-checkbox-box')]")
	List<WebElement> outSourcedOrRequired;

	public void uncheckIfOutsourcedOrNotRequired() {
		waitutil.waitForOverlay();

		for (WebElement checkbox : outSourcedOrRequired) {
			WebElement input = checkbox.findElement(By.xpath("./ancestor::p-checkbox//input"));

			if (checkbox.isDisplayed() && "true".equalsIgnoreCase(input.getAttribute("aria-checked"))) {
				WebElement box = wait.until(ExpectedConditions.elementToBeClickable(checkbox));
				box.click();
				clickServiceSave();
				waitutil.waitForOverlay();
			}
		}
	}

	public void clickFilterIcon() {
		elementUtil.click(iconFilter);
	}

	public void clickFilterGo() {
		elementUtil.click(btnFilterGo);
	}

	private final By searchAndAddSave = By.xpath("(//app-search-add//button[text()=' Save '])[1]");
	private final By searchAndAddClose = By.xpath("(//app-search-add//button[text()=' Close '])[1]");

	public void clickListSave() {
		elementUtil.click(searchAndAddSave);
	}

	public void clickListClose() {
		waitutil.waitForSwalPopup();
		elementUtil.click(searchAndAddClose);
	}

	private final By btnFinalize = By.xpath("//button[text()=' Finalize ']");

	public void clickFinalize() {
		waitutil.waitForOverlay();
		waitutil.waitForSwalPopup();
		elementUtil.click(btnFinalize);
		waitutil.waitForSwalPopup();
	}

	public boolean hasServiceConstraints() {
		return serviceUtil.Constraints();
	}

	public void fillInfo() {
		serviceUtil.Info();

	}

	private final By btnServiceClose = By.xpath("//div[@aria-hidden='false']//button[normalize-space()='Close']");

	public void clickServiceClose() {
		waitutil.waitForSwalPopup();
		elementUtil.click(btnServiceClose);
	}

	private final By btnServiceSave = By.xpath("//div[@aria-hidden='false']//button[normalize-space()='Save']");

	public void clickServiceSave() {
		elementUtil.click(btnServiceSave);
		waitutil.waitForSwalPopup();
		fillInfo();
		waitutil.waitForSwalPopup();
	}

	public void approveServiceConstraints(String eventNo) {
		serviceUtil.approveConstraints(eventNo);
	}

	public boolean isItemPresent(String itemName) {

		waitutil.waitForSwalPopup();
		List<WebElement> item = driver.findElements(By.xpath("//textarea[@title='" + itemName + "']"));

		return !item.isEmpty();
	}

	private final By iconAddCircle = By.xpath("//span[normalize-space()='add_circle']");

	public void openMenuBar() {
		elementUtil.click(iconAddCircle);
	}

	private final By btnYes = By.xpath("//button[text()='Yes']");

	public void clickAlertYes() {
		elementUtil.click(btnYes);
		waitutil.waitForSwalPopup();
		waitutil.waitForOverlay();
	}

	private final By btnOk = By.xpath("//button[text()='Ok']");

	public void clickAlertOk() {
		elementUtil.click(btnOk);
		waitutil.waitForOverlay();
		waitutil.waitForSwalPopup();
	}

}
