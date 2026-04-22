package pageObjects;

import java.io.IOException;
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

import utilities.ExcelUtility;

public class CreateInquiryPage {

	public WebDriver driver;

	public CreateInquiryPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath = "//span[normalize-space(text())='Create Inquiry']") WebElement hdrCreateInquiry;
	@FindBy(xpath = "//p-dropdown[contains(@placeholder,'Potential Customer')]/div/div[2]/span") WebElement drpCustomerOrPotentialCustomer;
	//@FindBy(className = "contactFirstName") WebElement txtContactFirstName;
	@FindBy(xpath = "//span[text()='-Select Contact-']") WebElement drpContact;
	@FindBy(xpath = "//span[text()='Next']") WebElement btnNext;
	@FindBy(xpath = "(//span[text()='Save'])[1]") WebElement btnSave;
	
	public String getCreateInquiryhdr()
	{
		return hdrCreateInquiry.getText();
	}
	
	public void createInquiry(String exceldata, WebDriver driver) throws InterruptedException
	{
		Thread.sleep(2000);
		drpCustomerOrPotentialCustomer.click();
		Thread.sleep(5000);
		List<WebElement> UlList = drpCustomerOrPotentialCustomer.findElements(By.xpath("//ul[@id='pr_id_5_list']//li"));
		
		for(int i = 0; i < UlList.size(); i++)
		{
			if(UlList.get(i).getText().trim().equalsIgnoreCase(exceldata.trim()))
			{
				UlList.get(i).click();
				break;
			}
		}
	}
	public void clickContactdrp(String exceldata, WebDriver driver) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='-Select Contact-']")));
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(drpContact));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".p-progress-spinner"))); 
		drpContact.click();
		Thread.sleep(2000);
		List<WebElement> UList = driver.findElements(By.xpath("//ul[@id='pr_id_2_list']//li"));
		
		for(int i = 0; i < UList.size(); i++)
		{
			if(UList.get(i).getText().trim().equalsIgnoreCase(exceldata.trim()))
			{
				UList.get(i).click();
				break;
			}
		}
	//	btnNext.click();
		btnSave.click();
	}
}
