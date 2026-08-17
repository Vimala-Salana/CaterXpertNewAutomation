package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import testBase.BasePage;

public class ContactListPage extends BasePage{

	public WebDriver driver;

	public ContactListPage(WebDriver driver)
	{
		super(driver);
	}
	
	//@FindBy(xpath = "//span[normalize-space(text())='Contact List']") WebElement hdrContactList;
	private final By hdrContactList = By.xpath("//span[normalize-space(text())='Contact List']");
	//@FindBy(xpath = "//button[text()=' New Contact ']") WebElement btnNewContact;
	private final By btnNewContact = By.xpath("//button[text()=' New Contact ']");
	
	public String getContactListHeader()
	{
		return elementUtil.getText(hdrContactList);
	}
	public void clickNewContact()
	{
		elementUtil.click(btnNewContact);
	}
	
	private final By txtSearchCustomer = By.xpath("//input[contains(@placeholder,'Contact Search')]");

	public void searchContactName(String contactName)
	{
		elementUtil.typeText(txtSearchCustomer, contactName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.overlay")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
	}
	
	private final By lstcontactName = By.xpath("(//table[contains(@class,'visit-table')]//td[3]//span)[1]");
	public String getContactNamefromlst()
	{
		return elementUtil.getText(lstcontactName);
	}
}
