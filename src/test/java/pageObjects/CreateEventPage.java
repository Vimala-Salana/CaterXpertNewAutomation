package pageObjects;


import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.BasePage;
import utilities.ElementInteractionUtil;
import utilities.ExcelUtility;
import utilities.MandatoryLabelsUtil;
import utilities.ServiceUtil;
import utilities.WaitUtils;
import workFlows.ServicesWorkFlows;

public class CreateEventPage extends BasePage{
	
	String filepath;
	String sheetname;
	ServiceUtil serviceutil;
	public String eventNo;
	
	public CreateEventPage(WebDriver driver, String filepath,String sheetname)
	{
		super(driver);
		this.filepath = filepath;
		this.sheetname = sheetname;
		PageFactory.initElements(driver, this);
		serviceutil = new ServiceUtil(driver);
	}
	
	public CreateEventPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		serviceutil = new ServiceUtil(driver);
	}
	
	@FindBy(xpath = "//span[normalize-space(text())='Create Event']") WebElement hdrCreateEvent;
	
	public String getCreateEventhdr()
	{
		waitutil.waitForOverlay();
		return hdrCreateEvent.getText();
	}
	
	public void fillEventMandatoryfields(Map<String, String> data)
	{
		MandatoryLabelsUtil.fillMandatoryFields(driver, data);
	}
	
	private final By btnCreate = By.xpath("//button[normalize-space()='Create']");
	public void clickCreatebtn()
	{
		elementUtil.click(btnCreate);
	}
	
	private final By txtTaxExpiryPopUp = By.xpath("The Customer Tax Exempt Certificate is Expired. Do you want to continue?");
	private final By alertYes = By.xpath("//button[text()='Yes']");
	public void clickYesInTaxExpiryPopupifExists()
	{
		List<WebElement> taxExpiry = driver.findElements((txtTaxExpiryPopUp));
		if(!taxExpiry.isEmpty())
		{
			elementUtil.click(alertYes);
		}
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
	
	//@FindBy(xpath ="//label[text()=' Event # ']//following-sibling::label[2]") WebElement eventNumlocator;
	private final By eventNumlocator = By.xpath("//label[text()=' Event # ']//following-sibling::label[2]");
	public String getEventNo()
	{
		//waitutil.waitForOverlay();
		eventNo = elementUtil.getText(eventNumlocator);
		return eventNo;
	}
	
	public void ApproveEventConstraints(String eventNo)
	{
			serviceutil.approveConstraints(eventNo);
			ServicesWorkFlows servicesFlow = new ServicesWorkFlows(driver);
			servicesFlow.navigateToEventDashboard(eventNo);
	}
	
	
}
