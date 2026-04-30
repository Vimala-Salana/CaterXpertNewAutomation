package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactListPage {

	public WebDriver driver;

	public ContactListPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(xpath = "//span[normalize-space(text())='Contact List']") WebElement hdrContactList;
	@FindBy(xpath = "//button[text()=' New Contact ']") WebElement btnNewContact;
	
	public String getContactListHeader()
	{
		return hdrContactList.getText();
	}
	public void clickNewContact()
	{
		btnNewContact.click();
	}
}
