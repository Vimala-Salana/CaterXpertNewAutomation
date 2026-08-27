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

	private final By hdrMenuService = By.xpath("//span[contains(text(),'Event Services')]");

	public String getmenuServiceHdr() {
		waitutil.waitForOverlay();
		return elementUtil.getText(hdrMenuService);
	}

	private final By btnSearchAndAdd = By.xpath("//button[text()=' Search & Add ']");
	private final By iconFilter = By.xpath("//span[@ptooltip='Filter']");
	private final By btnFilterGo = By.xpath("//button[normalize-space(text())='Go']");

	public void clickSearchAndAddbtn() {

		elementUtil.click(btnSearchAndAdd);
		elementUtil.click(iconFilter);
		elementUtil.click(btnFilterGo);

	}

	By noRecordsTxt = By.xpath("//p[normalize-space()='No records found']");

	public boolean isNoRecordsFoundDisplayed() {
		return driver.findElements(noRecordsTxt).stream().anyMatch(WebElement::isDisplayed);
	}

	private final By chkItems = By.xpath("//p-tabpanel[@header='Item']//p-checkbox[@ptooltip='Select Item']");
	private final By menuNames = By.xpath("//p-tabpanel[@header='Item']//span[contains(text(),'Select')]");
	private final By list = By.xpath("//li[@role='option']");
	private final By checkboxLoc = By
			.xpath("//p-checkbox[@ptooltip='Select Item']//div[contains(@class,'p-checkbox-box')]");

	private final By searchAndAddSave = By.xpath("(//app-search-add//button[text()=' Save '])[1]");
	private final By searchAndAddClose = By.xpath("(//app-search-add//button[text()=' Close '])[1]");

	public void addMenuItemsQty() {

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(chkItems));
		List<WebElement> elements = driver.findElements(chkItems);

		for (int i = 0; i < Math.min(elements.size(), 1); i++) {

			try {
				WebElement el = driver.findElements(checkboxLoc).get(i);

				// wait.until(ExpectedConditions.elementToBeClickable(el));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);

			} catch (StaleElementReferenceException e) {
				i--; // retry same index
			}
		}

		int menuCount = Math.min(driver.findElements(menuNames).size(), 20);

		for (int i = 0; i < menuCount; i++) {

			wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(menuNames)));

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

	private final By drpMenuNumber = By.xpath("//p-dropdown[@name='menuNumId']");
	private final By drpCourseOption = By.xpath("//p-dropdown[@name='courseId']");
	private final By drpCategory = By.xpath("//p-dropdown[@name='categoryId']");
	private final By drpSubCategory = By.xpath("//p-dropdown[@name='subCatId']");
	private final By drpMenuItem = By.xpath("//p-dropdown[@name='menuItemId']");

	public void addMenuItemFromAdd() {
		elementUtil.click(drpMenuNumber);
		selectOptionFromList(0);
		elementUtil.click(drpCourseOption);
		selectOptionFromList(0);
		elementUtil.click(drpCategory);
		selectOptionFromList(1);
		elementUtil.click(drpSubCategory);
		selectOptionFromList(1);
		elementUtil.click(drpMenuItem);
		selectOptionFromList(0);
	}

	private final By drplist = By.xpath("//li[@role='option']");

	private void selectOptionFromList(int option) {
		List<WebElement> options = driver.findElements(drplist);
		options.get(option).click();
		waitutil.waitForOverlay();
	}

	public String getAddScreenMenuItem() {
		return elementUtil.getText(drpMenuItem);
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

	private final By itemNames = By.xpath("//td[count(//th[.='Item Name ']/preceding-sibling::th)+1]//textarea");

	public String getMenuItemNameFromServiceRequest() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(itemNames));
		return driver.findElement(itemNames).getAttribute("title");
	}

	private final By txtQuantity = By.xpath("(//input[@name='quantity'])[1]");

	public void editQuantity() {
		String prevQty = elementUtil.getValue(txtQuantity);
		double newQty = Double.parseDouble(prevQty.trim()) + 10;
		elementUtil.typeText(txtQuantity, String.valueOf(newQty));
		LoggerManager.info("Item Quantity edited from " + prevQty + " to " + newQty);

	}

	private final By drpMenuOption = By.xpath("//td[count(//th[.='Menu']/preceding-sibling::th)+1]//span[(@id)]");

	public String getMenuOption() {
		return elementUtil.getText(drpMenuOption);
	}

	private final By drpCourse = By.xpath("//td[count(//th[.='Course ']/preceding-sibling::th)+1]//span[@id]");

	public String getCourse() {
		return elementUtil.getText(drpCourse);
	}

	private final By lnkMenuView = By.xpath("//span[text()='Menu View']");

	public void navigateToMenuView() {
		elementUtil.click(lnkMenuView);
		waitutil.waitForOverlay();
	}

	public void approveMenuserviceConstraints(String eventNo) {
		serviceUtil.approveConstraints(eventNo);
	}

}
