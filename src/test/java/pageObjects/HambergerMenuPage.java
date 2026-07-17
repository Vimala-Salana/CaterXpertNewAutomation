package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ConfigReader;
import utilities.WaitUtils;

public class HambergerMenuPage{

	public WebDriver driver;
	public ConfigReader config = new ConfigReader();
	WebDriverWait wait; 
	WaitUtils waitutil;
	public HambergerMenuPage(WebDriver driver)
	{  
		this.driver = driver;
		PageFactory.initElements(driver,this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	}
	
	@FindBy(xpath = "//span[normalize-space(text())='AE Dashboard']") WebElement hdrAEDashboard;

	@FindBy(xpath = "//span[text()='Open Inquiries']") WebElement lnkOpenInquiries;
	@FindBy(xpath = "//tr[1]/td/div/i[2]") WebElement btnCreateEvent;
	@FindBy(xpath = "//button[text()='Yes']") WebElement alertYes;
	
	@FindBy(xpath = "//span[normalize-space(text())='Customer/Potential Customer']") WebElement txtCustomerOrPotentialCustomer; //CRM
	@FindBy(xpath = "//span[(normalize-space(text())='Customer')]") WebElement txtCustomer;
	@FindBy(xpath = "//span[text()='Contact']") WebElement txtContcat;
	@FindBy(xpath = "//span[text()='Create Inquiry']") WebElement txtCreateInquiry;
	

	public String getAEDashboardHeader()
	{
		wait.until(ExpectedConditions.visibilityOf(hdrAEDashboard));
		return hdrAEDashboard.getText();
	}
	
	@FindBy(xpath = "//nav//div[contains(@class,'d-flex')]//span[contains(.,'menu')]") WebElement hambergerMenu;
	public void clickhambergerMenu() {
			
		 wait.until(ExpectedConditions.elementToBeClickable(hambergerMenu));
	    //((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);
		hambergerMenu.click();
	    waitutil.waitForOverlay();
	    
	}
	
	@FindBy(xpath = "//span[text()='Create Event']") WebElement lnkCreateEvent;

	public void clickCreateEvent() 
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		WebElement createEventLnk =  wait.until(ExpectedConditions.visibilityOf(lnkCreateEvent));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createEventLnk);
		//wait.until(ExpectedConditions.elementToBeClickable(lnkCreateEvent)).click();
	}
	
	public void clickCustomerOrPotentialCustomerdrp()
	{
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		//WebElement createCustomerLnk =  wait.until(ExpectedConditions.visibilityOf(txtCustomerOrPotentialCustomer));
		WebElement createCustomerLnk =  wait.until(ExpectedConditions.visibilityOf(txtCustomer));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createCustomerLnk);
   		//wait.until(ExpectedConditions.elementToBeClickable(txtCustomerOrPotentialCustomer)).click();
	}
	
	@FindBy(xpath = "//a[@href='#/sales/customerListing' and (@aria-expanded='false')]") WebElement lnkCustomer;
	public void clickCustomerlnk()
	{
		 wait.until(ExpectedConditions.visibilityOf(lnkCustomer));
		 lnkCustomer.click();
	}
	public void clickContcat()
	{
		
		txtContcat.click();
	}
	
	public void clickOpenInquiries()
	{
		wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".p-progress-spinner"))); 
		lnkOpenInquiries.click();
	}
	
	public void clickCreateInquiry()
	{
		
		txtCreateInquiry.click();
	}
	
	
	//@FindBy(xpath = "//span[text()=' Event Listing ']") WebElement hdrEventListing; // to validate event listing header
	By eventListingHeader = By.xpath("//span[normalize-space()='Event Listing']");
	@FindBy(xpath = "//span[text()='Event Listing']") WebElement lnkEventListing;
	
	public boolean isEventListingPresent() {

	    waitutil.waitForOverlay();
	   // wait.until(ExpectedConditions.visibilityOfElementLocated(eventListingHeader));
	    return !driver.findElements(eventListingHeader).isEmpty();
	}
	
	public void clickEventListinglnk()
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.elementToBeClickable(lnkEventListing));
		waitutil.waitForOverlay();
		lnkEventListing.click();
		
	}
	
	
	@FindBy(xpath = "//a[@href='#/sales/approval-inbox']") WebElement lnkapprovals;
	@FindBy(xpath = "//span[text()='Sales Admin']") WebElement drpSalesAdmin;
	
	public void clickApprovals()
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		wait.until(ExpectedConditions.elementToBeClickable(drpSalesAdmin));
		drpSalesAdmin.click();
		wait.until(ExpectedConditions.elementToBeClickable(lnkapprovals));
		//lnkapprovals.click();
		 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lnkapprovals);
		waitutil.waitForOverlay();
	}
}
