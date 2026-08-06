package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import testBase.BasePage;

public class ServicesPage extends BasePage{
	
	public ServicesPage(WebDriver driver)
	{
		super(driver);
	}
	
	private final By btnSearchAndAdd = By.xpath("//button[text()=' Search & Add ']");
	private final By iconFilter = By.xpath("//span[@ptooltip='Filter']");
	private final By btnFilterGo = By.xpath("//button[normalize-space(text())='Go']");
	
	
	private final By hdrService = By.xpath("//span[contains(text(),'Event Services')]");
	public String getServiceHdr()
	{
		waitutil.waitForOverlay();
		return elementUtil.getText(hdrService);
	}

	public void clickSearchAndAddbtn()
	{
		elementUtil.click(btnSearchAndAdd);
	}
	
	public void clickFilterIcon()
	{
		elementUtil.click(iconFilter);
	}

	public void clickFilterGo()
	{
		elementUtil.click(btnFilterGo);
	}
	
	private final By searchAndAddSave = By.xpath("(//app-search-add//button[text()=' Save '])[1]");
	private final By searchAndAddClose = By.xpath("(//app-search-add//button[text()=' Close '])[1]");
	
	public void clickListSaveAndClose()
	{
		elementUtil.click(searchAndAddSave);
		elementUtil.click(searchAndAddClose);
	}

	private final By btnFinalize = By.xpath("//button[text()=' Finalize ']");
	public void clickFinalize()
	{
		elementUtil.click(btnFinalize);
	}

	public boolean hasServiceConstraints()
	{
		return serviceUtil.Constraints();
	}

	public void fillInfo()
	{
		serviceUtil.Info();

	}

	private final By btnServiceClose = By.xpath(
			"//div[@aria-hidden='false']//div[@class='p-tabview-panels']//button[normalize-space()='Close']");
	public void clickServiceClose()
	{
		elementUtil.click(btnServiceClose);
	}

	private final By btnServiceSave = By.xpath(
			"//div[@aria-hidden='false']//div[@class='p-tabview-panels']//button[normalize-space()='Save']");
	public void clickServiceSave()
	{
		elementUtil.click(btnServiceSave);
	}
	
	public void approveServiceConstraints(String eventNo)
	{
		serviceUtil.approveConstraints(eventNo);
		EventListingPage eventlist = new EventListingPage(driver);
		eventlist.EventDashboardNavigation(eventNo);
	}

}
