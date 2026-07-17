package pageObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	By estimateSectionList = By.xpath("//div[@class='d-flex mt-1 ng-star-inserted']//span[2]");
	By suggestedPriceBy = By.xpath("(//input[@name='totalActCost'])[1]");
	@FindBy(xpath = "(//label[text()=' Menu Number '])[1]") List<WebElement> menuNumber;
	@FindBy(xpath = "(//input[@name='estimatedTotal'])[1]") WebElement subTotal;
	@FindBy(xpath = "(//*[self::span or self::button][normalize-space()='Save'])[1]") WebElement estimatesSave;
	@FindBy(xpath = "(//label[text()=' Total ' or text()=' Section Total ']//following::input)[1]") WebElement serviceTotaltxt;

	@FindBy(xpath = "//th[text()=' Personnel ']") List<WebElement> personnelEstimate;

	String serviceTotal;
	BigDecimal discount;
	BigDecimal totalServiceAmount = BigDecimal.ZERO;
	public void estimates() 
	{
		int estimateSize = driver.findElements(estimateSectionList).size();
		//System.out.println("Estimates size - "+estimateSize);
		//for(WebElement estimateSection : estimateSections)
		for(int i=0;i<estimateSize;i++)
		{
			waitutil.waitForOverlay();

			wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(estimateSectionList).get(i)));
			waitutil.waitForOverlay();
			String serviceName = driver.findElements(estimateSectionList).get(i).getText();
			System.out.println(serviceName);
			driver.findElements(estimateSectionList).get(i).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(suggestedPriceBy));

			waitutil.waitForOverlay();

			String suggestedprice = driver.findElement(suggestedPriceBy).getAttribute("value");
			double price = Double.parseDouble(suggestedprice);
			String subTotalAmt= null;
			//System.out.println(price);
			if(!menuNumber.isEmpty())
			{
				System.out.println("Menu Estimate Found");
				subTotalAmt = (price > 0) ? suggestedprice : "100";

			}
			else if(!personnelEstimate.isEmpty())
			{
				System.out.println("Personnel Estimate Found");

				subTotalAmt = (price > 0) ? suggestedprice : "100";
			}
			else if(price>0)
			{
				subTotalAmt = suggestedprice; 	//for warehouse estimates

			}

			if(subTotalAmt != null)  
			{
				//System.out.println(subTotalAmt);
				subTotal.clear();
				subTotal.sendKeys(subTotalAmt);
				serviceTotaltxt.click();
				serviceTotal = serviceTotaltxt.getAttribute("value");	

				//BigDecimal finalAmount = new BigDecimal(subTotalAmt);
				BigDecimal serviceAmount = new BigDecimal(serviceTotal);

				System.out.println("Service Total of "+serviceName +" is "+ serviceAmount.setScale(2, RoundingMode.HALF_UP));
				//discount = finalAmount.subtract(serviceAmount).setScale(2, RoundingMode.HALF_UP);   //discount=finalAmount-ServiceAmount

				//System.out.println("Discount of "+serviceName +" is "+discount);
				totalServiceAmount = totalServiceAmount.add(serviceAmount);
				estimatesSave.click();
				waitutil.waitForOverlay();
			}
		}
		System.out.println("All Services Total Amount - "+totalServiceAmount);
	}
	@FindBy (xpath = "//button[normalize-space(text())='Total Estimate']") WebElement totalEstimatesBtn;
	public void clickTotalEstimates()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(totalEstimatesBtn));
		totalEstimatesBtn.click();
		waitutil.waitForOverlay();
	}

	By estimateServicesList = By.xpath("//tr[position()>1]/td[2]//span[contains(text(),'Select')]");
	@FindBy (xpath = "//ul[contains(@role,'listbox')]//li[1]") WebElement options;

	public void selectTotalEstimateOptions()
	{
		waitutil.waitForOverlay();
		List<WebElement> estimateServices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(estimateServicesList));

		if(!estimateServices.isEmpty())
		{

			for(WebElement service : estimateServices)
			{
				waitutil.waitForOverlay();
				service.click();
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", options);
				waitutil.waitForOverlay();
			}
		}
	}
	
	@FindBy(xpath = "//label[text()=' Tax ']//following::input[@name ='tax'][1]") WebElement estimatesTax;

	public double getEstimatesTotals()
	{
		String taxTotal = estimatesTax.getAttribute("value");
		System.out.println("Total tax amount is - "+taxTotal);
		
		Double calcualtedTotal = Double.parseDouble(taxTotal) + totalServiceAmount.doubleValue();
		
		calcualtedTotal = BigDecimal.valueOf(calcualtedTotal).setScale(2, RoundingMode.HALF_UP).doubleValue();
		System.out.println("Calculated Total is - "+calcualtedTotal);
	
		return calcualtedTotal;
		
		
	}
	
	@FindBy(xpath = "//label[text()=' Total Price ']//following::input[@name ='total'][1]") WebElement estimatesTotal;
	public double getActualTotal()
	{
		String actualTotal = estimatesTotal.getAttribute("value");
		System.out.println("Actual Total is - "+actualTotal);
		return Double.parseDouble(actualTotal);
	}

	@FindBy(xpath = "//span[text()='Save']") WebElement totalEstimatesSaveBtn;

	public void saveTotalEstimates()
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.elementToBeClickable(totalEstimatesSaveBtn));
		totalEstimatesSaveBtn.click();
		System.out.println("Saved Total Estimates");
	}



}
