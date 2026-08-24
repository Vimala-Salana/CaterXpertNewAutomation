package pageObjects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import testBase.BasePage;
import utilities.LoggerManager;
import utilities.ReportManager;

public class BeverageServicePage extends BasePage {
	private ServicesPage servicesPage;

	public BeverageServicePage(WebDriver driver) {
		super(driver);
		servicesPage = new ServicesPage(driver);
	}

	@FindBy(xpath = "//p-tabpanel[@header='Item']/div[@role='tabpanel' and not(@hidden)]//following-sibling::p")
	List<WebElement> noRecords;
	@FindBy(xpath = "//span[@ptooltip='Filter']")
	WebElement filterIcon;
	@FindBy(xpath = "//div[@role='region']//div[@class = 'p-checkbox-box p-highlight']")
	WebElement showMappedcheckBox;
	@FindBy(xpath = "//button[normalize-space(text())='Go']")
	WebElement filterGo;

	public void showMappedItems() {
		waitutil.waitForOverlay();
		if (!noRecords.isEmpty() && noRecords.get(0).getText().equalsIgnoreCase("No records found")) {
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(filterIcon));
			filterIcon.click();
			wait.until(ExpectedConditions.elementToBeClickable(showMappedcheckBox));
			showMappedcheckBox.click();
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(filterGo));
			filterGo.click();
			waitutil.waitForOverlay();
		}
	}

	// @FindBy(xpath = "//input[contains(@id,'qty')]") List<WebElement>
	// quantityfields;
	By quantityfields = By.xpath("//input[contains(@id,'qt')]");

	public void addBeverageItems() {

		servicesPage.clickSearchAndAddbtn();
		servicesPage.uncheckIfOutsourcedOrNotRequired();
		showMappedItems();
		/*
		 * int size =
		 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(quantityfields
		 * )).size(); //System.out.println(size); for(int i=0;i<size;i++) {
		 * List<WebElement> qty = driver.findElements(quantityfields); WebElement
		 * itemQty = qty.get(0); itemQty.sendKeys(Keys.chord(Keys.CONTROL, "a"),
		 * Keys.DELETE, "10"); //itemQty.sendKeys("10"); }
		 */

		List<WebElement> qty = driver.findElements(quantityfields);

		if (!qty.isEmpty()) {

			qty.get(0).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, "10");

			servicesPage.clickListSave();

			closeInventoryPopupIfPresent();

			waitutil.waitForSwalPopup();

			servicesPage.clickListClose();
		} else
			System.out.println("No Items Exist");
	}

	private final By btnInventoryOk = By.xpath("//button[text()='Ok']");

	public void closeInventoryPopupIfPresent() {

		elementUtil.clickIfPresent(btnInventoryOk);
		waitutil.waitForSwalPopup();
	}

	public Map<String, String> getItemDetails(WebElement row, List<WebElement> headers) {

		List<WebElement> cells = row.findElements(By.xpath("./td[position()>1]"));

		Map<String, String> rowData = new LinkedHashMap<>();

		for (int i = 0; i < cells.size(); i++) {

			String header = headers.get(i).getText().trim();

			WebElement cell = cells.get(i);

			String value;

			List<WebElement> input = cell.findElements(By.tagName("input"));

			if (!input.isEmpty()) {
				value = input.get(0).getAttribute("value");
			} else {
				List<WebElement> span = cell.findElements(By.tagName("span"));

				value = !span.isEmpty() ? span.get(0).getText().trim() : cell.getText().trim();
			}

			rowData.put(header, value);
		}

		return rowData;
	}

	public void validateItems() {

		waitutil.waitForOverlay();
		// div[@role='tabpanel'and
		// @aria-hidden='false']//thead[@class='thead-dark']//th[position()>1]
		String itemsTableXpath = "//div[@class='service-header']//following::thead[1][@class='thead-dark']";
		List<WebElement> headers = wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(itemsTableXpath + "//th[position()>1]")));

		List<WebElement> rows = driver
				.findElements(By.xpath(itemsTableXpath + "//following-sibling::tbody//tr[count(td)>1]"));

		System.out.println("No of items: " + rows.size());
		boolean hasAvailable = false;
		for (WebElement h : headers) {
			String text = h.getText().trim();
			if (text.equalsIgnoreCase("Available Qty")) {
				hasAvailable = true;
			}
		}
		if (hasAvailable)
			ReportManager.info("Available Qty and Reserved Qty are visible");
		else {
			ReportManager.info("Available Qty and Reserved Qty are not visible");
		}

		for (int i = 0; i < rows.size(); i++) {

			WebElement row = rows.get(i);

			// 1. READ DATA
			Map<String, String> rowData = getItemDetails(row, headers);

			// 2. PRINT DATA
			System.out.println("================================");
			// rowData.forEach((header, value) ->
			// System.out.println(header + " : " + value));
			System.out.println("ROW DATA: " + rowData);
			String itemName = rowData.get("Item");
			int expectedReservedQty = 0;
			if (hasAvailable && rowData.get("Available Qty") != null) {
				int availableQty = Integer.parseInt(rowData.get("Available Qty"));
				int qty = Integer.parseInt(rowData.get("Qty"));
				expectedReservedQty = Math.min(qty, availableQty);
				int expectedAvailbleQty = availableQty - expectedReservedQty;

				System.out.println("Expected Reserved Qty: " + expectedReservedQty);
				System.out.println("Expected Available Qty : " + expectedAvailbleQty);
			}

			// 4. CLICK RESERVE
			clickReserveIfPresent();

			waitutil.waitForOverlay();

			// 5. RE-READ AFTER ACTION
			rows = driver.findElements(By.xpath("//tbody//tr[count(td)>1]"));

			row = rows.get(i);

			Map<String, String> updatedRowData = getItemDetails(row, headers);
			int actualReservedQty = 0;
			if (hasAvailable && rowData.get("Reserved Qty") != null) {
				actualReservedQty = Integer.parseInt(updatedRowData.get("Reserved Qty"));

				System.out.println("Actual Reserved Qty: " + actualReservedQty);
			}

			// 6. COMPARE
			Assert.assertEquals(actualReservedQty, expectedReservedQty, "Mismatch for item: " + itemName);
		}

	}

	private final By btnReserve = By.xpath("//button[text()=' Reserve ']");

	public void clickReserveIfPresent() {
		elementUtil.clickIfPresent(btnReserve);
		closeInventoryPopupIfPresent();
		waitutil.waitForSwalPopup();

	}

	@FindBy(xpath = "//button[text()=' Finalize ']")
	WebElement btnFinalize;

	public void clickFinalize() {
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(btnFinalize));
		btnFinalize.click();
		waitutil.waitForOverlay();
	}

	@FindBy(xpath = "//div[@role='tabpanel' and (@aria-hidden='false')]//button[.=' Close ']")
	WebElement staffclosebtn;

	public void clickBeverageServiceClose() {
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(staffclosebtn));
		staffclosebtn.click();
	}

	private final By txtItemName = By.xpath("//textarea[@name='itemName']");

	public String getBeverageItemName() {
		return elementUtil.getValue(txtItemName);
	}

	private final By txtQuantity = By.xpath(
			"//td[count(//div[@aria-hidden='false']//th[text()=' Qty ']/preceding-sibling::th)+1]//input[@type='text']");

	public void editQuantity() {

		String prevQty = elementUtil.getValue(txtQuantity);
		Double newQty = Double.parseDouble(prevQty.trim()) + 10;
		elementUtil.typeText(txtQuantity, newQty.toString());
		LoggerManager.info("Item Quantity edited from " + prevQty + " to " + newQty);
	}

	private final By chkselect = By.xpath("//p-checkbox[@ptooltip='Select Item']");
	private final By btnDelete = By.xpath("//div[@aria-hidden='false']//button[normalize-space()='Delete']");

	public void deleteItem() {
		elementUtil.click(chkselect);
		elementUtil.click(btnDelete);
	}
}
