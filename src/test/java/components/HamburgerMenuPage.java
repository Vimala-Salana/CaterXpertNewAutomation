package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pageObjects.EventListingPage;
import testBase.BasePage;
import utilities.LoggerManager;

public class HamburgerMenuPage extends BasePage {

	private HeaderPage headerPage;
	private EventListingPage eventListPage;

	public HamburgerMenuPage(WebDriver driver) {
		super(driver);
		headerPage = new HeaderPage(driver);
		eventListPage = new EventListingPage(driver);
	}

	private By hdrAEDashboard = By.xpath("//span[normalize-space(text())='AE Dashboard']");
	private By lnkOpenInquiries = By.xpath("//span[text()='Open Inquiries']");
	private By txtCustomerOrPotentialCustomer = By
			.xpath("//span[normalize-space(text())='Customer/Potential Customer']");
	private By txtContcat = By.xpath("//span[text()='Contact']");
	private By txtCreateInquiry = By.xpath("//span[text()='Create Inquiry']");

	public String getAEDashboardHeader() {
		return elementUtil.getText(hdrAEDashboard);
	}

	private final By lnkCreateEvent = By.xpath("//span[text()='Create Event']");

	public void clickCreateEvent() {
		elementUtil.click(lnkCreateEvent);
	}

	public void clickCustomerOrPotentialCustomerdrp() {

		elementUtil.click(txtCustomerOrPotentialCustomer);

	}

	private final By lnkCustomer = By.xpath("//a[@href='#/sales/customerListing' and (@aria-expanded='false')]");

	public void clickCustomerlnk() {
		elementUtil.click(lnkCustomer);
	}

	public void clickContcat() {

		elementUtil.click(txtContcat);
	}

	public void clickOpenInquiries() {
		elementUtil.click(lnkOpenInquiries);
	}

	public void clickCreateInquiry() {

		elementUtil.click(txtCreateInquiry);
	}

	private final By hdrEventListing = By.xpath("//span[normalize-space()='Event Listing']");
	private final By lnkEventListing = By.xpath("//span[text()='Event Listing']");

	public boolean isEventListingPresent() {
		waitutil.waitForOverlay();
		return !driver.findElements(hdrEventListing).isEmpty();
	}

	public void navigatetoEventListing() {
		eventListPage.closeInventoryPopupIfPresent();
		if (!isEventListingPresent()) {
			headerPage.clickhambergerMenu();
			elementUtil.click(lnkEventListing);
		} else {
			LoggerManager.info("Already in Event Listing Screen");
		}
	}

	private final By drpSalesAdmin = By.xpath("//span[text()='Sales Admin']");
	private final By lnkapprovals = By.xpath("//a[@href='#/sales/approval-inbox']");

	public void clickApprovals() {
		elementUtil.click(drpSalesAdmin);
		elementUtil.click(lnkapprovals);
	}
}
