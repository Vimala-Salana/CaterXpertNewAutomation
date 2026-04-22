package pageObjects;


import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ExcelUtility;
import utilities.MandatoryLabelsUtil;
import utilities.ServiceUtil;
import utilities.WaitUtils;

public class CreateEventPage {

	WebDriver driver;
	String filepath;
	String sheetname;
	WebDriverWait wait;
	WaitUtils waitutil;
	ServiceUtil serviceutil;
	public String eventNo;
	public CreateEventPage(WebDriver driver,String filepath,String sheetname)
	{
		this.driver =driver;
		this.filepath = filepath;
		this.sheetname = sheetname;
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		serviceutil = new ServiceUtil(driver);
	}
	
	public CreateEventPage(WebDriver driver)
	{
		this.driver =driver;
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		serviceutil = new ServiceUtil(driver);
	}
	
	@FindBy(xpath = "//span[normalize-space(text())='Create Event']") WebElement hdrCreateEvent;
	
	public String getCreateEventhdr()
	{
		return hdrCreateEvent.getText();
	}
	
	public void fillEventMandatoryfields() throws Exception
	{
		waitutil.waitForOverlay();
		ExcelUtility excelUtil = new ExcelUtility(filepath);
		Map<String, String> data = excelUtil.getMandatoryFieldData(filepath, sheetname);
		MandatoryLabelsUtil.fillMandatoryFields(driver, data);
	}
	
	@FindBy(xpath ="//button[normalize-space()='Create']") WebElement btnCreate;
	public void clickCreatebtn()
	{
		wait.until(ExpectedConditions.elementToBeClickable(btnCreate));
		btnCreate.click();
		waitutil.waitForOverlay();
	}
	
	@FindBy(xpath = "(//button[text()=' Close '])[2]") WebElement btnClose;
	public void clickClosebtn()
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.elementToBeClickable(btnClose)).click();
	}
	
	public boolean eventConstraints()
	{
		waitutil.waitForOverlay();
		return serviceutil.Constraints();
	}
	
	@FindBy(xpath ="//label[text()=' Event # ']//following-sibling::label[2]") WebElement eventNumlocator;
	public String getEventNo()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.visibilityOf(eventNumlocator));
		eventNo = eventNumlocator.getText();
		return eventNo;
	}
	
	public void ApproveEventConstraints(boolean constraintsExists, String eventNo) throws InterruptedException
	{
			serviceutil.approveConstraints(constraintsExists,eventNo);
			EventListingPage eventlist = new EventListingPage(driver);
			eventlist.EventDashboardNavigation(eventNo);
	}
	
	
}
