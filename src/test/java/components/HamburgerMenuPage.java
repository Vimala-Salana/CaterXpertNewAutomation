package components;

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

import testBase.BasePage;
import utilities.ConfigReader;
import utilities.WaitUtils;

public class HamburgerMenuPage extends BasePage{

	private HeaderPage headerPage;
	public HamburgerMenuPage(WebDriver driver)
	{  
		super(driver);
		headerPage = new HeaderPage(driver);
	}
	
	private By hdrAEDashboard = By.xpath("//span[normalize-space(text())='AE Dashboard']");
	private By lnkOpenInquiries = By.xpath("//span[text()='Open Inquiries']");
	private By btnCreateEvent = By.xpath("//tr[1]/td/div/i[2]");
	private By alertYes = By.xpath("//button[text()='Yes']");
	private By txtCustomerOrPotentialCustomer = By.xpath("//span[normalize-space(text())='Customer/Potential Customer']");
	private By txtCustomer = By.xpath("//span[normalize-space(text())='Customer']");
	private By txtContcat = By.xpath("//span[text()='Contact']");
	private By txtCreateInquiry = By.xpath("//span[text()='Create Inquiry']");
	

	public String getAEDashboardHeader()
	{
		return elementUtil.getText(hdrAEDashboard);
	}

	
	//@FindBy(xpath = "//span[text()='Create Event']") WebElement lnkCreateEvent;
	private final By lnkCreateEvent = By.xpath("//span[text()='Create Event']");

	public void clickCreateEvent() 
	{
		elementUtil.click(lnkCreateEvent);
	}
	
	public void clickCustomerOrPotentialCustomerdrp()
	{
		
		elementUtil.click(txtCustomerOrPotentialCustomer);
		
	}
	
	private final By lnkCustomer = By.xpath("//a[@href='#/sales/customerListing' and (@aria-expanded='false')]");
	public void clickCustomerlnk()
	{
		 elementUtil.click(lnkCustomer);
	}
	public void clickContcat()
	{
		
		elementUtil.click(txtContcat);
	}
	
	public void clickOpenInquiries()
	{
		elementUtil.click(lnkOpenInquiries);
	}
	
	public void clickCreateInquiry()
	{
		
		elementUtil.click(txtCreateInquiry);
	}
	
	
	//@FindBy(xpath = "//span[text()=' Event Listing ']") WebElement hdrEventListing; // to validate event listing header
	private final By hdrEventListing = By.xpath("//span[normalize-space()='Event Listing']");
	private final By lnkEventListing = By.xpath("//span[text()='Event Listing']");
	
	
	public boolean isEventListingPresent() {

	    waitutil.waitForOverlay();
	    return !driver.findElements(hdrEventListing).isEmpty();
	}
	
	public void navigatetoEventListing()
	{
		if(!isEventListingPresent())
		{
			headerPage.clickhambergerMenu();
			elementUtil.click(lnkEventListing);
		}
		else {
			System.out.println("Already in Event Listing Screen");
		}
	}
	
	
	private final By drpSalesAdmin = By.xpath("//span[text()='Sales Admin']");
	private final By lnkapprovals = By.xpath("//a[@href='#/sales/approval-inbox']");
	
	public void clickApprovals()
	{
		elementUtil.click(drpSalesAdmin);
		elementUtil.click(lnkapprovals);
	}
}
