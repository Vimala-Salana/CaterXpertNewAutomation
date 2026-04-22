package pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.BaseClass;
import utilities.ExcelUtility;

public class Sample extends BaseClass{
	public WebDriver driver;

	public Sample(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}

	@FindBy(xpath = "//span[normalize-space(text())='Create Customer'] ") WebElement hdrCreateCustomer;
	@FindBy(xpath = "//span[text()='Contact']") WebElement lnkcontact;
	@FindBy(xpath = "//button[text()=' New Contact ']") WebElement btnNewContact;

	public String getCreateCustomerhdr()
	{
		return hdrCreateCustomer.getText();
	}

	@FindBy(xpath = "//label[.//span[text()='*']]") List<WebElement> mandatoryLabels;  //label

	public void fillMandatoryFields(String filePath, String sheetName) throws IOException, InterruptedException 
	{

		ExcelUtility excelUtil = new ExcelUtility(filepath);
		Map<String, String> fieldData = excelUtil.getMandatoryFieldData(filepath, sheetName);
		List<WebElement> mandatoryLabels = driver.findElements(By.xpath("//label[.//span[text()='*']]"));

		for (WebElement label : mandatoryLabels) {
			String labelText = label.getText().replace("*", "").trim();
			if (!fieldData.containsKey(labelText)) continue;

			String value = fieldData.get(labelText);

			try {
				// Try input or textarea
				WebElement input = label.findElement(By.xpath("../following-sibling::div//input | ../following-sibling::div//textarea"));

				System.out.println("input4");
				String tag = input.getTagName().toLowerCase();

				if ((tag.equals("input") || tag.equals("textarea")) && input.isEnabled())
				{
					System.out.println("printing label");
					input.sendKeys(value);
					System.out.println("printed label");
					continue;
				}
			} catch (NoSuchElementException e) {
				// Not input or textarea
			}

			try {
				// Try multiselect dropdown
				WebElement multiSelect = label.findElement(By.xpath("../following-sibling::div//div[contains(@class,'p-multiselect')]"));
				multiSelect.click();

				List<WebElement> options = driver.findElements(By.xpath("//ul[contains(@class,'p-multiselect-items')]//li"));
				for (WebElement option : options) {
					if (option.getText().equalsIgnoreCase(value)) {
						option.click();
						break;
					}
				}
				multiSelect.click();
				continue;
			} catch (NoSuchElementException e) {
				// Not a multiselect
			}

			try {
				// Try normal dropdown
				WebElement dropdown = label.findElement(By.xpath("../following-sibling::div//div[contains(@class,'p-dropdown')]"));
				dropdown.click();

				List<WebElement> options = driver.findElements(By.xpath("//ul[contains(@class,'p-dropdown-items')]//li"));
				for (WebElement option : options) {
					if (option.getText().equalsIgnoreCase(value)) {
						option.click();
						break;
					}
				}
			} catch (NoSuchElementException e) {
				// Not a dropdown
			}
		}
	}

}
