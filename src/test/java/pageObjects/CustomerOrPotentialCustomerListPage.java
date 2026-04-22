package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomerOrPotentialCustomerListPage {

	public WebDriver driver;
	WebDriverWait wait;
	public CustomerOrPotentialCustomerListPage(WebDriver driver)
	{  
		this.driver = driver;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	}
	@FindBy(xpath="//span[normalize-space(text())='Customer/Potential Customer List']") WebElement hdrCustomerOrPotentialCustomerList;

	public String getCustomerOrPotentialCustomerListhdr()
	{
		return wait.until(ExpectedConditions.visibilityOf(hdrCustomerOrPotentialCustomerList)).getText();
	}

	@FindBy(xpath="//button[normalize-space(text())='New Customer']") WebElement btnNewCustomer;

	public void ClickNewCustomer()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ngx-spinner-overlay")));
		WebElement NewCustomerbtn =  wait.until(ExpectedConditions.visibilityOf(btnNewCustomer));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", NewCustomerbtn);
		//wait.until(ExpectedConditions.elementToBeClickable(btnNewCustomer)).click();
	}
	
	
}
