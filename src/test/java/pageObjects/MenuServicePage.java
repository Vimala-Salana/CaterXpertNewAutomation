package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import testBase.BasePage;
import utilities.LoggerManager;

public class MenuServicePage extends BasePage {

	public MenuServicePage(WebDriver driver) {
		super(driver);

	}

	// @FindBy(xpath = "//span[normalize-space(text())='Event Services - Menu']")
	// WebElement menuServicehdr;
	private final By hdrMenuService = By.xpath("//span[contains(text(),'Event Services')]");

	public String getmenuServiceHdr() {
		waitutil.waitForOverlay();
		return elementUtil.getText(hdrMenuService);
	}

	private final By btnSearchAndAdd = By.xpath("//button[text()=' Search & Add ']");
	private final By iconFilter = By.xpath("//span[@ptooltip='Filter']");
	private final By btnFilterGo = By.xpath("//button[normalize-space(text())='Go']");

	public void clickSearchAndAddbtn() {
		/*
		 * waitutil.waitForOverlay();
		 * wait.until(ExpectedConditions.elementToBeClickable(btnsearchAndAdd));
		 * ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
		 * btnsearchAndAdd);
		 * wait.until(ExpectedConditions.elementToBeClickable(filterIcon)).click();
		 * wait.until(ExpectedConditions.elementToBeClickable(filterGo));
		 * filterGo.click();
		 */
		elementUtil.click(btnSearchAndAdd);
		elementUtil.click(iconFilter);
		elementUtil.click(btnFilterGo);

	}

	By noRecordsTxt = By.xpath("//p[normalize-space()='No records found']");

	public boolean isNoRecordsFoundDisplayed() {
		return driver.findElements(noRecordsTxt).stream().anyMatch(WebElement::isDisplayed);
	}

	// @FindBy(xpath = "//p-checkbox[@ptooltip='Select Item']") List <WebElement>
	// itemsCheckBox;

	private final By chkItems = By.xpath("//p-tabpanel[@header='Item']//p-checkbox[@ptooltip='Select Item']");

	// private final By itemsCheckBoxLoc = By.xpath("//p-checkbox[@ptooltip='Select
	// Item']");
	private final By menuNames = By.xpath("//p-tabpanel[@header='Item']//span[contains(text(),'Select')]");
	private final By list = By.xpath("//li[@role='option']");
	private final By checkboxLoc = By
			.xpath("//p-checkbox[@ptooltip='Select Item']//div[contains(@class,'p-checkbox-box')]");

	private final By searchAndAddSave = By.xpath("(//app-search-add//button[text()=' Save '])[1]");
	private final By searchAndAddClose = By.xpath("(//app-search-add//button[text()=' Close '])[1]");

	public void addMenuItemsQty() {

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(chkItems));
		List<WebElement> elements = driver.findElements(chkItems);

		for (int i = 0; i < elements.size(); i++) {

			try {
				WebElement el = driver.findElements(checkboxLoc).get(i);

				// wait.until(ExpectedConditions.elementToBeClickable(el));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);

			} catch (StaleElementReferenceException e) {
				i--; // retry same index
			}
		}

		while (true) {

			List<WebElement> menuName = driver.findElements(menuNames);

			if (menuName.size() == 0) {
				break;
			}

			// Wait and click the first dropdown using JS
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(menuNames)));
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView({block:'center'});",
			// dropdown);
			// wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
			elementUtil.click(menuNames);

			List<WebElement> options = driver.findElements(list);
			wait.until(ExpectedConditions.visibilityOfAllElements(options));
			wait.until(ExpectedConditions.elementToBeClickable(options.get(0)));
			options.get(0).click(); // click the first option

		}
		LoggerManager.info("Added Menu Items");

		clickListSave();
		waitutil.waitForSwalPopup();
		clickListClose();

	}

	public void addMenuItems() {
		clickSearchAndAddbtn();
		addMenuItemsQty();
	}

	public void clickListSave() {
		elementUtil.click(searchAndAddSave);
		waitutil.waitForSwalPopup();
	}

	public void clickListClose() {
		waitutil.waitForSwalPopup();
		elementUtil.click(searchAndAddClose);
	}

	private final By btnFinalize = By.xpath("//button[text()=' Finalize ']");

	public void clickFinalize() {
		elementUtil.click(btnFinalize);
	}

	public boolean menuServiceConstraints() {
		return serviceUtil.Constraints();
	}

	public void fillMenuInfo() {
		serviceUtil.Info();

	}

	@FindBy(xpath = "(//button[text()=' Close '])[1]")
	WebElement menuClose;

	public void menuServiceClose() {
		waitutil.waitForOverlay();
		menuClose.click();
	}

	private final By iconDelete = By.xpath("(//span[normalize-space()='delete'])[1]");

	public void clickDeleteIcon() {
		elementUtil.click(iconDelete);
	}

	private final By btnYes = By.xpath("//button[text()='Yes']");

	public void clickAlertYes() {
		elementUtil.click(btnYes);
	}

	public void checkDeletedItem() {

	}

	private final By txtQuantity = By.xpath("(//input[@name='quantity'])[1]");

	public void editQuantity() {
		String prevQty = elementUtil.getValue(txtQuantity);
		System.out.println(prevQty);
		double newQty = Double.parseDouble(prevQty.trim()) + 10;
		System.out.println(newQty);
		elementUtil.typeText(txtQuantity, String.valueOf(newQty));
	}

	public void approveMenuserviceConstraints(String eventNo) {
		serviceUtil.approveConstraints(eventNo);
	}

}
