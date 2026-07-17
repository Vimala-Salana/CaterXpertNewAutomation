package pageObjects;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.WaitUtils;

public class BeverageservicePage {
	public WebDriver driver;
	String filepath;
	String sheetname;
	WaitUtils waitutil;
	WebDriverWait wait;
	public BeverageservicePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	}

	@FindBy(xpath = "//div[contains(@class,'d-flex me-2')]//div[contains(@class,'p-checkbox-box')]") List<WebElement> outSourcedOrRequired;
	@FindBy(xpath = "//button[normalize-space(text())='Save']") WebElement serviceSave;
	public void uncheckIfOutsourcedOrNotRequired()
	{
		waitutil.waitForOverlay();

		for (WebElement checkbox : outSourcedOrRequired) 
		{

			WebElement input = checkbox.findElement(By.xpath("./ancestor::p-checkbox//input"));

			if (checkbox.isDisplayed() && "true".equalsIgnoreCase(input.getAttribute("aria-checked"))) 
			{
				WebElement box = wait.until(ExpectedConditions.elementToBeClickable(checkbox));
				box.click();
				serviceSave.click();
				waitutil.waitForOverlay();
			}
		}
	}

	@FindBy(xpath = "//p-tabpanel[@header='Item']/div[@role='tabpanel' and not(@hidden)]//following-sibling::p") List<WebElement> noRecords;
	@FindBy(xpath = "//span[@ptooltip='Filter']") WebElement filterIcon;
	@FindBy(xpath = "//div[@role='region']//div[@class = 'p-checkbox-box p-highlight']") WebElement showMappedcheckBox;
	@FindBy(xpath = "//button[normalize-space(text())='Go']") WebElement filterGo;
	public void showMappedItems()
	{
		waitutil.waitForOverlay();
		if(!noRecords.isEmpty() && noRecords.get(0).getText().equalsIgnoreCase("No records found"))
		{
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

	//@FindBy(xpath = "//input[contains(@id,'qty')]") List<WebElement> quantityfields;
	By quantityfields = By.xpath("//input[contains(@id,'qt')]");

	public void enterQuantity()
	{
		int size = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(quantityfields)).size();
		//System.out.println(size);
		for(int i=0;i<size;i++)
		{
			List<WebElement> qty = driver.findElements(quantityfields);
			WebElement itemQty = qty.get(i);
			itemQty.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE, "10");
			//itemQty.sendKeys("10");
		}
	}

	@FindBy(xpath = "//button[text()='Ok']") List<WebElement> okBtn;
	public void clickOkInInventoryPopup()
	{
		if(!okBtn.isEmpty())
		{
			wait.until(ExpectedConditions.elementToBeClickable(okBtn.get(0)));
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].click();",okBtn.get(0));

		} else {

			System.out.println("Inventory alert not displayed");
		}
	}

	public Map<String, String> getItemDetails(WebElement row,List<WebElement> headers) {

		List<WebElement> cells = row.findElements(By.xpath("./td[position()>1]"));

		Map<String, String> rowData = new LinkedHashMap<>();

		for (int i = 0; i < cells.size(); i++) {

			String header = headers.get(i).getText().trim();

			WebElement cell = cells.get(i);

			String value;

			List<WebElement> input =
					cell.findElements(By.tagName("input"));

			if (!input.isEmpty()) {
				value = input.get(0).getAttribute("value");
			} else {
				List<WebElement> span =
						cell.findElements(By.tagName("span"));

				value = !span.isEmpty()
						? span.get(0).getText().trim()
								: cell.getText().trim();
			}

			rowData.put(header, value);
		}

		return rowData;
	}

	public void validateItems() {

		waitutil.waitForOverlay();
		//div[@role='tabpanel'and @aria-hidden='false']//thead[@class='thead-dark']//th[position()>1]
		String itemsTableXpath = "//div[@class='service-header']//following::thead[1][@class='thead-dark']";
		List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(itemsTableXpath+"//th[position()>1]")));

		List<WebElement> rows = driver.findElements( By.xpath(itemsTableXpath+"//following-sibling::tbody//tr[count(td)>1]"));

		System.out.println("No of items: " + rows.size());
		boolean hasAvailable = false;
		boolean hasReserved = false;
		for (WebElement h : headers) {
			String text = h.getText().trim();
			if (text.equalsIgnoreCase("Available Qty")) hasAvailable = true;
			if (text.equalsIgnoreCase("Reserved Qty")) hasReserved = true;
		}
		if(hasAvailable = true)
			System.out.println("Available Qty and Reserved Qty are visible");

		for (int i = 0; i < rows.size(); i++) {

			WebElement row = rows.get(i);

			// 1. READ DATA
			Map<String, String> rowData = getItemDetails(row, headers);

			// 2. PRINT DATA
			System.out.println("================================");
			//rowData.forEach((header, value) ->
			//  System.out.println(header + " : " + value));
			System.out.println("ROW DATA: " + rowData);
			String itemName = rowData.get("Item");
			int expectedReservedQty = 0;
			if(hasAvailable && rowData.get("Available Qty") != null)
			{
				int availableQty = Integer.parseInt(rowData.get("Available Qty"));
				int qty = Integer.parseInt(rowData.get("Qty"));
				expectedReservedQty = Math.min(qty, availableQty);
				int expectedAvailbleQty = availableQty - expectedReservedQty;

				System.out.println("Expected Reserved Qty: " + expectedReservedQty);
				System.out.println("Expected Available Qty : "+expectedAvailbleQty);
			}

			// 4. CLICK RESERVE
			clickReserveIfPresent();

			waitutil.waitForOverlay();

			// 5. RE-READ AFTER ACTION
			rows = driver.findElements(
					By.xpath("//tbody//tr[count(td)>1]"));

			row = rows.get(i);

			Map<String, String> updatedRowData =
					getItemDetails(row, headers);
			int actualReservedQty = 0;
			if(hasAvailable && rowData.get("Reserved Qty") != null)
			{
				actualReservedQty =
						Integer.parseInt(updatedRowData.get("Reserved Qty"));

				System.out.println("Actual Reserved Qty: " + actualReservedQty);
			}

			// 6. COMPARE
			Assert.assertEquals(
					actualReservedQty,
					expectedReservedQty,
					"Mismatch for item: " + itemName);
		}

	}

	@FindBy(xpath = "//button[text()=' Reserve ']") List<WebElement> reserveBtn;
	public boolean clickReserveIfPresent()
	{
		if(!reserveBtn.isEmpty())
		{
			wait.until(ExpectedConditions.elementToBeClickable(reserveBtn.get(0)));
			reserveBtn.get(0).click();
			return true;
		}
		return false;
	}

	@FindBy(xpath = "//button[text()=' Finalize ']") WebElement btnFinalize;

	public void clickFinalize()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(btnFinalize));
		btnFinalize.click();
		waitutil.waitForOverlay();
	}

	@FindBy(xpath = "//div[@role='tabpanel' and (@aria-hidden='false')]//button[.=' Close ']") WebElement staffclosebtn;

	public void clickBeverageServiceClose()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(staffclosebtn));
		staffclosebtn.click();
	}


}
