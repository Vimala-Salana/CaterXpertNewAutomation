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
	By estimateSectionList = By.xpath("//div[@class=\"d-flex mt-1 ng-star-inserted\"]/span[2]");
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
			System.out.println(estimate.getText());
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(estimate));
			estimate.click();
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.visibilityOf(suggestedPrice));
			String suggestedprice = suggestedPrice.getAttribute("value");
			double price = Double.parseDouble(suggestedprice);
			//System.out.println(price);
			if(!menuNumber.isEmpty())
			{
				System.out.println("Found Menu Estimate");
				if(price>0)
				{
					waitutil.waitForOverlay();
					 subTotal.sendKeys(suggestedprice);
					System.out.println(suggestedprice);
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
	
	public void validation()throws InterruptedException {
		System.out.println("waitinh");
		Thread.sleep(10000);
		System.out.println("waitinh");
		List<WebElement> header = driver.findElements(By.xpath("((//thead[@class=\"thead-dark\"])[4]//th)[position()>1]"));
		int noOfItems = driver.findElements(By.xpath("(//div[contains(@id,'p-tabpanel')]//table//tbody//tr[3])")).size();
		System.out.println("No of items size  -  "+noOfItems);
		for(int i = 0 ; i < noOfItems ; i++) {
			List<WebElement> itemDetails = driver.findElements(By.xpath("((//div[contains(@id,'p-tabpanel')]//table//tbody//tr[3])["+(i+1)+"]//td)[position()>1]"));
			System.out.println("((//div[contains(@id,'p-tabpanel')]//table//tbody//tr[3])[\"+(i+1)+\"]//td)[position()>1]");
			System.out.println("itemDetails size  -  "+itemDetails.size());
			int j = 0;
			for(WebElement element : itemDetails) {
				
				boolean childPresent = element.findElements(By.xpath("./*")).size() > 0;
				if(childPresent) {
					try {
						String value = element.findElement(By.xpath("./input")).getAttribute("value");
						System.out.println(header.get(j).getText()+" - "+value);
					}
					catch(Exception e) {
						String value = element.findElement(By.xpath("./span")).getText();
						System.out.println(header.get(j).getText()+" - "+value);
					}
				}
				else {
					System.out.println(header.get(j).getText()+" - "+element.getText());
				}
				j++;
			}
		}
	}


}
