package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ServiceUtil;
import utilities.WaitUtils;

public class MenuServicePage {

	public WebDriver driver;
	public WaitUtils waitutil;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	ServiceUtil serviceUtil;

	public MenuServicePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		js = (JavascriptExecutor) driver;
		serviceUtil = new ServiceUtil(driver);
	}

	@FindBy(xpath = "//span[normalize-space(text())='Event Services - Menu']") WebElement menuServicehdr;
	public String getmenuServiceHdr()
	{
		wait.until(ExpectedConditions.visibilityOf(menuServicehdr));
		return menuServicehdr.getText();
	}

	@FindBy(xpath = "//button[text()=' Search & Add ']") WebElement btnsearchAndAdd;
	@FindBy(xpath = "//span[@ptooltip='Filter']") WebElement filterIcon;
	@FindBy(xpath = "//button[normalize-space(text())='Go']") WebElement filterGo;

	public void clickSearchAndAddbtn()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(btnsearchAndAdd));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnsearchAndAdd);
		wait.until(ExpectedConditions.elementToBeClickable(filterIcon)).click();
		wait.until(ExpectedConditions.elementToBeClickable(filterGo));
		filterGo.click();
	}
	
	By noRecordsTxt = By.xpath("//p[normalize-space()='No records found']");
	public boolean isNoRecordsFoundDisplayed() {
	    return driver.findElements(noRecordsTxt)
	            .stream()
	            .anyMatch(WebElement::isDisplayed);
	} 
	

	@FindBy(xpath = "//p-checkbox[@ptooltip='Select Item']") List <WebElement> itemsCheckBox;

	By itemsCheckBoxLoc = By.xpath("//p-checkbox[@ptooltip='Select Item']");
	By menuNames = By.xpath("//span[contains(normalize-space(),'Select')]");
	By list = By.xpath("//li[@role='option']");
	By checkboxLoc = By.xpath("//p-checkbox[@ptooltip='Select Item']//div[contains(@class,'p-checkbox-box')]");

	public void selectMenuItems() throws InterruptedException
	{	
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxLoc));
		List<WebElement> elements = driver.findElements(checkboxLoc);

		for (int i = 0; i < elements.size(); i++) {

			try {
				WebElement el = driver.findElements(checkboxLoc).get(i);

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
			WebElement dropdown = wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(menuNames)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", dropdown);
			wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();			
			
			List<WebElement> options = driver.findElements(list);
			wait.until(ExpectedConditions.elementToBeClickable(options.get(0)));
			options.get(0).click(); // click the first option
			Thread.sleep(100);

		}	
		System.out.println("Added Menu Items");

	}

	@FindBy(xpath = "(//app-search-add//button[text()=' Save '])[1]") WebElement searchAndAddSave;
	@FindBy(xpath = "(//app-search-add//button[text()=' Close '])[1]") WebElement searchAndAddClose;

	public void clickListSave()
	{
		wait.until(ExpectedConditions.elementToBeClickable(searchAndAddSave)).click();

		/*
		 * wait.until(driver -> { try { return driver.findElements(By.cssSelector(
		 * "div.overlay, div.swal2-container, .swal2-popup, .swal2-backdrop-show, .p-toast"
		 * )) .stream() .noneMatch(e -> e.isDisplayed()); } catch
		 * (StaleElementReferenceException e) { return true; } });
		 */		
	}
	
	public void clickListClose()
	{
		By closeLocator = By.xpath("(//app-search-add//button[text()=' Close '])[1]");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'swal2-container')]")));
		waitutil.waitForOverlay();
		WebElement listclose = wait.until(ExpectedConditions.visibilityOfElementLocated(closeLocator));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", listclose);
		waitutil.waitForOverlay();
		
	}
	
	@FindBy(xpath = "//button[text()=' Finalize ']") WebElement finalizebtn;
	public void clickFinalize()
	{
		wait.until(ExpectedConditions.elementToBeClickable(finalizebtn));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", finalizebtn);
		waitutil.waitForOverlay();
	}

	public boolean  menuServiceConstraints()
	{
		return serviceUtil.Constraints();
	}

	public void fillmenuInfo()
	{
		serviceUtil.Info();

	}

	@FindBy(xpath = "(//button[text()=' Close '])[1]") WebElement menuClose;
	public void menuServiceClose()
	{
		waitutil.waitForOverlay();
		menuClose.click();
	}

	public void approveMenuserviceConstraints(boolean constraintsExists, String eventNo) throws InterruptedException
	{
		serviceUtil.approveConstraints(constraintsExists, eventNo);
		EventListingPage eventlist = new EventListingPage(driver);
		eventlist.EventDashboardNavigation(eventNo);
	}


}
