package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.WaitUtils;

public class EventDashboardPage {

	WebDriver driver;
	WebDriverWait wait;
	WaitUtils waitutil;
	public EventDashboardPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(50));
	}

	@FindBy(xpath = "//div[contains(@class,'event-dashboard')]//div[@class='header-fs']")
	public List<WebElement> headersList;
	public List<String> readAllHeaders()
	{
		//System.out.println("Reading all headers");
	    wait.until(ExpectedConditions.visibilityOfAllElements(headersList));
		List<String> headers = new ArrayList<String>();
		for(WebElement list : headersList)
		{
			headers.add(list.getText());
		}
		return headers;

	}

	public boolean clickServiceLabelIcon(List<String> service, List<String> status, List<String> iconLabel) 
	{
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.visibilityOfAllElements(headersList));
		// Looping through all the headers
		for (WebElement header : headersList) 
		{
			String name = header.getText();
			boolean statusMatch = (status == null || status.stream().anyMatch(name::contains));
			if(service.stream().anyMatch(name::contains) && statusMatch)
			{
				System.out.println("Service Status : "+name); //Matching header
				WebElement labelname = header.findElement(By.xpath("../following-sibling::div//*[contains(@class,'service-label')]")); 
				String labelText = labelname.getText();
				if(iconLabel.stream().anyMatch(i -> labelText.equalsIgnoreCase(i)))
				{
					//System.out.println(labelname.getText());
					WebElement icon = labelname.findElement(By.xpath("..//i")); //icon
					wait.until(ExpectedConditions.elementToBeClickable(icon)).click();
					return true;
				}
			}
		}
		return false;
	}
	
	@FindBy(xpath = "//label[text()='Event #']/following::span[2]") WebElement txteventNo;

	public String getEventNo()
	{
		wait.until(ExpectedConditions.visibilityOf(txteventNo));
		return txteventNo.getText();
	}

}
