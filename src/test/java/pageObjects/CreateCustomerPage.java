package pageObjects;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import utilities.WaitUtils;

public class CreateCustomerPage extends BasePage
{

	public CreateCustomerPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy (xpath = "//span[normalize-space(text())='Create Customer'] ") WebElement hdrCreateCustomer;
	public String getCreateCustomerhdr()
	{
		return hdrCreateCustomer.getText();
	}

	public void fillCustomerMandatoryfields(Map<String, String> data) throws Exception {
		 System.out.println("CreateCustomerPage driver = " + driver); System.out.println("CreateCustomerPage driver = " + driver);
		  MandatoryLabelsUtil.fillMandatoryFields(driver, data);
	}

	private final By btnCreate = By.xpath("//button[normalize-space(text())='Create']");
	public void clickCreatebtn()
	{
		elementUtil.click(btnCreate);
	}
	
	private final By contactClose = By.xpath("//div[@aria-hidden='false']//button[normalize-space(text())='Close']");
	public void clickContactClose()
	{
		elementUtil.click(contactClose);
	}

	//@FindBy(xpath = "//input[contains(@placeholder,'Search Customer')]") WebElement txtSearchCustomer;
	private final By txtSearchCustomer = By.xpath("//input[contains(@placeholder,'Search Customer')]");
	public void enterCustomerNameinSearch(String customerName)
	{
		elementUtil.typeText(txtSearchCustomer, customerName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		// txtSearchCustomer.sendKeys(customerName,Keys.ENTER);
		
	}
	
	private final By lstcustomerName = By.xpath("(//tbody[@class='ng-star-inserted']//tr//td//a)[1]");
	public String getCustomerNamefromlst()
	{
		return elementUtil.getText(lstcustomerName);
	}
	public void clickCustomerNamefromlst()
	{
		wait.until(ExpectedConditions.elementToBeClickable(lstcustomerName)).click();
	
	}
	
	@FindBy(xpath ="//span[text()='Contacts']") WebElement tabContacts;
	public void clickContactstab() throws InterruptedException
	{
		Thread.sleep(1000);
		//wait.until(ExpectedConditions.elementToBeClickable(tabContacts)).click();
		tabContacts.click();
	}


}
