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

import testBase.BaseClass;
import utilities.WaitUtils;


public class KitchenNavigationPage{


	WebDriver driver;
	WebDriverWait wait;
	WaitUtils waitutil;
	public KitchenNavigationPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(50));
	}

	@FindBy(xpath = "//p[normalize-space(text()='Unacknowledged Change Requests')]") List<WebElement> changeRequestPopUp;
	@FindBy(xpath = "//button[normalize-space(text())='Close']") WebElement changeRequestPopUpClose;

	public void handleChangeRequestPopUp() throws InterruptedException
	{
		waitutil.waitForOverlay();
		if(!changeRequestPopUp.isEmpty())
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(changeRequestPopUpClose));
			//changeRequestPopUpClose.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", changeRequestPopUpClose);
		}
	}

	@FindBy(xpath = "//span[text()=' dining ']") WebElement baseNavigation;
	public void navigateTokitchen()
	{
		baseNavigation.click();
		WebElement frameright = driver.findElement(By.xpath("//frame[@name='right']"));
		driver.switchTo().frame(frameright);
		driver.findElement(By.xpath("//a[normalize-space(text())='Kitchen New']")).click();
		driver.switchTo().defaultContent();
		waitutil.waitForOverlay();
	}

	@FindBy(xpath = "//thead[.//th[text()=' Menu ']]//following-sibling::tbody//span[@class='cursor-pointer']") List<WebElement> kitchenMenu;


	public void clickKitchenMenu(List<String> serviceStatus)
	{
		waitutil.waitForOverlay();
		//System.out.println(kitchenMenu.size());
	    for(int i=0;i<kitchenMenu.size();i++)
	    {
	    wait.until(ExpectedConditions.visibilityOfAllElements(kitchenMenu)); 	
	    System.out.println(kitchenMenu.get(i).getText());
	    String text = kitchenMenu.get(i).getText();
	    
	    if(serviceStatus.contains(text)) {
	        wait.until(ExpectedConditions.elementToBeClickable(kitchenMenu.get(i)));
	        kitchenMenu.get(i).click();
	        break;
	    }
	}
}
	 @FindBy(xpath = "//button[normalize-space(text())='Accept']") WebElement acceptBtn;
		public void acceptMenuService()
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(acceptBtn));
			acceptBtn.click();
		}
		
		@FindBy(xpath = "//span[normalize-space(text())='add_circle']") WebElement plusIcon;
		
		public void clickPlusIcon()
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(plusIcon));
			plusIcon.click();
		}
		
		@FindBy(xpath = "//span[text()='Quantification']") WebElement lnkQuantification;
		public void clickQuantificationLink()
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(lnkQuantification));
			lnkQuantification.click();	
		}
		
		@FindBy(xpath = "//span[text()='Quantify All']") WebElement lnkquantifyAll;
		public void clickQuantifyAll()
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(lnkquantifyAll));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", lnkquantifyAll);
			//lnkquantifyAll.click();	
			waitutil.waitForOverlay();
		}
		
		
		@FindBy(xpath = "//span[normalize-space(text())='Gather All']") WebElement lnkgatherAll;
		public void clickgatherAll()
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(lnkgatherAll));
			lnkgatherAll.click();
			waitutil.waitForOverlay();
		}
		
		@FindBy(xpath = "//span[contains(.,'Menu Screen')]") WebElement lnkMenuService;
		public void clickMenuServicelnk()
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(lnkMenuService));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", lnkMenuService);
		}
		
		@FindBy(xpath = "//button[contains(.,'Bill')]") WebElement menuBillbtn;
		@FindBy(xpath = "//button[contains(@class,'swal2-confirm')]") WebElement BillPopupYes;
		public void clickMenuServiceBillbtn()
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(menuBillbtn));
			menuBillbtn.click();
			
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(BillPopupYes));
			BillPopupYes.click();
		}
		@FindBy(xpath = "//button[contains(.,'Close')]") WebElement menuServiceClose;
		public void clickmenuServiceClose()
		{
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(menuServiceClose));
			menuServiceClose.click();	
		}
		
		@FindBy(xpath = "//td[count(//th[contains(.,'Quantified')]/preceding-sibling::th)+1]") WebElement quantificationStatus;
		@FindBy(xpath = "//td[count(//th[contains(.,'Gathered')]/preceding-sibling::th)+1]") WebElement gatheredStatus;
		public void getQuantificationStatus()
		{
			waitutil.waitForOverlay();
			System.out.println("Qunatified : "+quantificationStatus.getText().trim());
			System.out.println("Gathered : "+gatheredStatus.getText().trim());
			
		}
		
		
		
}
