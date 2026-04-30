package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ExcelUtility;
import utilities.MandatoryLabelsUtil;

public class CreateContactPage {

	public WebDriver driver;
	String filepath;
	String sheetname;
	WebDriverWait wait;
	public CreateContactPage(WebDriver driver, String filepath, String sheetname)
	{
		this.driver = driver;
		this.filepath = filepath;
		this.sheetname = sheetname;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@FindBy(xpath = "//span[normalize-space(text())='Create Contact']") WebElement hdrCreateContcat;

	public String getContacthdr()
	{
		return hdrCreateContcat.getText();
	}

	public void fillContactMandatoryfileds() throws Exception
	{
		ExcelUtility excelUtil = new ExcelUtility(filepath);
		Map<String, String> data = excelUtil.getMandatoryFieldData(filepath, sheetname);
		MandatoryLabelsUtil.fillMandatoryFields(driver, data);
	}

	@FindBy(xpath = "//button[text()='Create ']") WebElement btnCreate;
	public void clickCreatebtn()
	{
		btnCreate.click();
	}

	@FindBy(xpath = "//span[text()=' event ']") WebElement iconNewEvent;

	public void clickNewEventIcon() 
	{
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
