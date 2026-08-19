package pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import testBase.BasePage;
import utilities.MandatoryLabelsUtil;

public class CreateContactPage extends BasePage{

	public CreateContactPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath = "//span[normalize-space(text())='Create Contact']") WebElement hdrCreateContcat;

	public String getContacthdr()
	{
		return hdrCreateContcat.getText();
	}

	public void fillContactMandatoryfileds(Map<String, String> data)
	{
		MandatoryLabelsUtil.fillMandatoryFields(driver, data);
	}

	private final By btnCreate  = By.xpath("//button[text()='Create ']");
	public void clickCreatebtn()
	{
		elementUtil.click(btnCreate);
		waitutil.waitForSwalPopup();
	}

	@FindBy(xpath = "//span[text()=' event ']") WebElement iconNewEvent;

	public void clickNewEventIcon() 
	{
		elementUtil.click(btnCreate);
		wait.until(ExpectedConditions.elementToBeClickable(iconNewEvent)).click();
	}

	@FindBy(xpath = "//tr[@class='ng-star-inserted']//td[2]") List<WebElement> contactsList;

	public List<String> getContactNamefromList()
	{
		List<String> names = new ArrayList<>();
		for(WebElement name : contactsList)
		{
			names.add(name.getText());
		}
		return names;
	}
	
	@FindBy(xpath = "//span[text()=' event ']") WebElement eventiconInContacts;
	public void eventNavFromContact()
	{
		wait.until(ExpectedConditions.elementToBeClickable(eventiconInContacts));
		eventiconInContacts.click();
		
	}
}
