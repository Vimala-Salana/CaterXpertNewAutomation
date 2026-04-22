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

import testBase.BaseClass;
import utilities.ServiceUtil;
import utilities.WaitUtils;

public class MenuServicePage extends BaseClass{

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

	public void clickSearchAndAddbtn()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(btnsearchAndAdd)).click();
		waitutil.waitForOverlay();
	}

	@FindBy(xpath = "//p-checkbox[@ptooltip='Select Item']") List <WebElement> itemsCheckBox;
	//@FindBy(xpath = "//span[contains(normalize-space(),'Select')]") List <WebElement> menuName;
	//@FindBy(xpath = "//li[@role='option']") List<WebElement> menuNamelst;
	
	By names = By.xpath("//span[contains(normalize-space(),'Select')]");
	By list = By.xpath("//li[@role='option']");

	public void selectMenuItems() throws InterruptedException
	{	
		Thread.sleep(100);
		wait.until(ExpectedConditions.visibilityOfAllElements(itemsCheckBox));
		 for(int i=0;i<itemsCheckBox.size();i++)
		{
			 //System.out.println("before checkbox click");
			 waitutil.waitForOverlay();
			 wait.until(ExpectedConditions.elementToBeClickable(itemsCheckBox.get(i)));
			 itemsCheckBox.get(i).click();
		}

		while (true) {

			List<WebElement> menuName = driver.findElements(names);

			if (menuName.size() == 0) {
				break;
			}

			// Wait for and click the first dropdown using JS
			WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(names));
			js.executeScript("arguments[0].click();", dropdown);

			List<WebElement> options = driver.findElements(list);
			wait.until(ExpectedConditions.elementToBeClickable(options.get(0)));
			options.get(0).click(); // click the first option
			Thread.sleep(100);
			
		}	
		System.out.println("Added Menu Items");
		
	}

	@FindBy(xpath = "(//app-search-add//button[text()=' Save '])[1]") WebElement SearchAndAddSave;
	@FindBy(xpath = "(//app-search-add//button[text()=' Close '])[1]") WebElement SearchAndAddClose;

	public void clickListSaveandClose() throws InterruptedException
	{
		wait.until(ExpectedConditions.elementToBeClickable(SearchAndAddSave)).click();

		/*
		 * wait.until(driver -> { try { return driver.findElements(By.cssSelector(
		 * "div.overlay, div.swal2-container, .swal2-popup, .swal2-backdrop-show, .p-toast"
		 * )) .stream() .noneMatch(e -> e.isDisplayed()); } catch
		 * (StaleElementReferenceException e) { return true; } });
		 */
		waitutil.waitForOverlay();

		By closeLocator = By.xpath("(//app-search-add//button[text()=' Close '])[1]");
		WebElement closebtn = driver.findElement(closeLocator);
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(closebtn));
		closebtn.click();
		// SearchAndAddClose.click();
	}

	@FindBy(xpath = "//button[text()=' Finalize ']") WebElement finalizebtn;
	public void clickFinalize()
	{
		wait.until(ExpectedConditions.elementToBeClickable(finalizebtn));
		finalizebtn.click();
		waitutil.waitForOverlay();
	}

	public boolean  menuServiceConstraints()
	{
		return serviceUtil.Constraints();
	}
	
	public void menuInfo()
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
