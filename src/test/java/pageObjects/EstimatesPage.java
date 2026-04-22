package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.WaitUtils;

public class EstimatesPage {

	WebDriver driver;
	WebDriverWait wait;
	WaitUtils waitutil;
	public EstimatesPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(50));
	}

	@FindBy(xpath = "//span[text()=' Estimate Sections ']") WebElement estimatesFullHdr;
	public String getEstimateshdr()
	{
		waitutil.waitForOverlay();
		return estimatesFullHdr.getText();
	}

	@FindBy(xpath = "//button[text()=' Save ']") WebElement liteSavebtn;
	public void clickEstimateLiteSave()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(liteSavebtn));
		liteSavebtn.click();
	}

	@FindBy(xpath = "//button[text()=' Close ']") WebElement liteClosebtn;
	public void clickEstimateLiteClose()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(liteClosebtn));
		liteClosebtn.click();
	}


	//@FindBy(xpath = "//span[text()=' description ']//following-sibling::span[1]") List<WebElement> estimateSectionsList;
	By estimateSectionList = By.xpath("//span[text()=' description ']//following-sibling::span[1]");
	@FindBy(xpath = "(//input[@name='totalActCost'])[1]") WebElement suggestedPrice;
	@FindBy(xpath = "(//label[text()=' Menu Number '])[1]") List<WebElement> menuNumber;
	@FindBy(xpath = "(//input[@name='estimatedTotal'])[1]") WebElement subTotal;
	@FindBy(xpath = "(//span[text()='Save'])[1]") WebElement estimatesSave;

	@FindBy(xpath = "//th[text()=' Personnel ']") List<WebElement> personnelEstimate;
	public void estimates() throws InterruptedException
	{
		int estimateSize = driver.findElements(estimateSectionList).size();

		//for(WebElement estimateSection : estimateSections)
		for(int i=0;i<estimateSize;i++)
		{
			waitutil.waitForOverlay();
			List<WebElement> estimates = driver.findElements(estimateSectionList);
			WebElement estimate = estimates.get(i);
			wait.until(ExpectedConditions.elementToBeClickable(estimate));
			estimate.click();
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.visibilityOf(suggestedPrice));
			String suggestedprice = suggestedPrice.getAttribute("value");
			double price = Double.parseDouble(suggestedprice);
			if(!menuNumber.isEmpty())
			{
				System.out.println("Found Menu Estimate");
				if(price>0)
				{
					waitutil.waitForOverlay();
					subTotal.sendKeys(suggestedprice);
					estimatesSave.click();
					waitutil.waitForOverlay();
				}
				else
				{
					waitutil.waitForOverlay();
					subTotal.sendKeys("100");
					estimatesSave.click();

				}
			}
			else if(!personnelEstimate.isEmpty())
			{
				System.out.println("Found Personnel Estimates");
				if(price>0)
				{
					waitutil.waitForOverlay();
					subTotal.sendKeys(suggestedprice);
					estimatesSave.click();
				}
				else
				{
					waitutil.waitForOverlay();
					subTotal.sendKeys("100");
					estimatesSave.click();
				}
			}

		}
	}


}
