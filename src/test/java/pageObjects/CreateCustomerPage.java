package pageObjects;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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

import utilities.ExcelUtility;
import utilities.MandatoryLabelsUtil;

public class CreateCustomerPage 
{

	public WebDriver driver;
	String filepath;
	String sheetname;
	WebDriverWait wait;
	public CreateCustomerPage(WebDriver driver, String filepath, String sheetname)
	{
		this.driver = driver;
		this.filepath = filepath;
		this.sheetname = sheetname;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@FindBy (xpath = "//span[normalize-space(text())='Create Customer'] ") WebElement hdrCreateCustomer;
	public String getCreateCustomerhdr()
	{
		return hdrCreateCustomer.getText();
	}

	public void fillCustomerMandatoryfields() throws Exception {
		ExcelUtility excelUtil = new ExcelUtility(filepath);
		Map<String, String> data = excelUtil.getMandatoryFieldData(filepath, sheetname);
		MandatoryLabelsUtil.fillMandatoryFields(driver, data);
	}

	@FindBy (xpath = "//button[normalize-space(text())='Create']") WebElement btnCreate;
	public void clickCreatebtn()
	{
		btnCreate.click();
	}

	@FindBy(xpath = "//input[contains(@placeholder,'Search Customer')]") WebElement txtSearchCustomer;
	public void enterCustomerNameinSearch(String customerName) throws InterruptedException, AWTException
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("spinner")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		 wait.until(ExpectedConditions.elementToBeClickable(txtSearchCustomer));
		 txtSearchCustomer.sendKeys(customerName,Keys.ENTER);
		
	}
	
	@FindBy(xpath = "(//tbody[@class='ng-star-inserted']//tr//td//a)[1]") WebElement lstcustomerName;
	public String getCustomerNamefromlst()
	{
		return lstcustomerName.getText();
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
