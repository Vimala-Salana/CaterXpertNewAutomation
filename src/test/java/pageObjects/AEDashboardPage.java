package pageObjects;

import java.time.Duration;

import org.openqa.selenium.Alert;
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

public class AEDashboardPage{

	public WebDriver driver;
	public ConfigReader config = new ConfigReader();
	WebDriverWait wait; 
	WaitUtils waitutil;
	public AEDashboardPage(WebDriver driver)
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
	
	@FindBy(xpath = "//span[normalize-space(text())='menu']") WebElement hambergerMenu;
	public void clickhambergerMenu() {
	
		waitutil.waitForOverlay();
		WebElement menu =  wait.until(ExpectedConditions.elementToBeClickable(hambergerMenu));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);
	    waitutil.waitForOverlay();
	    
	}
	
	@FindBy(xpath = "//span[text()='Create Event']") WebElement lnkCreateEvent;

	public void clickCreateEvent() 
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		WebElement createEventLnk =  wait.until(ExpectedConditions.visibilityOf(lnkCreateEvent));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createEventLnk);
		//wait.until(ExpectedConditions.elementToBeClickable(lnkCreateEvent)).click();
	     waitutil.waitForOverlay();
	}
	
	public void clickCustomerOrPotentialCustomer()
	{
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		//WebElement createCustomerLnk =  wait.until(ExpectedConditions.visibilityOf(txtCustomerOrPotentialCustomer));
		WebElement createCustomerLnk =  wait.until(ExpectedConditions.visibilityOf(txtCustomer));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createCustomerLnk);
   		//wait.until(ExpectedConditions.elementToBeClickable(txtCustomerOrPotentialCustomer)).click();
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
	
	
	@FindBy(xpath = "//span[text()=' Event Listing ']") public WebElement hdrEventListing; // to validate event listing header
	@FindBy(xpath = "//span[text()='Event Listing']") WebElement lnkEventListing;
	
	public void clickEventListinglnk()
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.elementToBeClickable(lnkEventListing));
		waitutil.waitForOverlay();
		lnkEventListing.click();
	}
	
	@FindBy(xpath = "//span[text()='Approvals']") WebElement lnkapprovals;
	public void clickApprovals()
	{
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		wait.until(ExpectedConditions.elementToBeClickable(lnkapprovals));
		//lnkapprovals.click();
		 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lnkapprovals);
		waitutil.waitForOverlay();
	}
}
