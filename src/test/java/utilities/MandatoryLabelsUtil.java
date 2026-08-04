package utilities;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constants.MandatoryFieldsXpaths;
import testBase.BaseClass;

public class MandatoryLabelsUtil extends BaseClass{
	public static WebDriver driver;
	public static WaitUtils waitutil;
	public static void fillMandatoryFields(WebDriver driver,Map<String, String> fieldData) throws Exception 
	{
		By mandatoryLabelsLocator  = By.xpath(MandatoryFieldsXpaths.MANDATORY_LABEL);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		waitutil = new WaitUtils(driver);
		waitutil.waitForOverlay();
		ElementInteractionUtil elementutil = new ElementInteractionUtil(driver);

		/*
		 * wait.until(new ExpectedCondition<Boolean>() {
		 * 
		 * @Override public Boolean apply(WebDriver d) { List<WebElement> labels =
		 * d.findElements(mandatoryLabelsLocator);
		 * 
		 * if (labels.isEmpty()) return false;
		 * 
		 * String first = labels.get(0).getText().replace("*", "").trim();
		 * 
		 * return first.equalsIgnoreCase("Business Unit"); } });
		 */

		//Selecting first option from Business Unit
		By businessUnitlocator = By.xpath(MandatoryFieldsXpaths.BUSINESS_UNIT);

		waitutil.waitForOverlay();
		List<WebElement> buisnessUnit = driver.findElements(businessUnitlocator);
		if(!buisnessUnit.isEmpty())
		{
			WebElement bu = wait.until(ExpectedConditions.elementToBeClickable(businessUnitlocator));	
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", bu);
			waitutil.waitForOverlay();

			By multiSelect = By.xpath(MandatoryFieldsXpaths.MULTISELECT_LIST);
			By singleSelect = By.xpath(MandatoryFieldsXpaths.DROPDOWN_LIST);
			By multiselectClose = By.xpath("//button[contains(@class,'p-multiselect-close')]//span");

			if (!driver.findElements(multiSelect).isEmpty()) 
			{
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(multiSelect));
				WebElement option = driver.findElement(By.xpath("(//ul[contains(@class,'p-multiselect-items')]//li)[1]"));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
				wait.until(ExpectedConditions.elementToBeClickable(multiselectClose)).click();
				System.out.println("clicked multi-select BU");
				waitutil.waitForOverlay();
			}

			else {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(singleSelect));
				WebElement option = driver.findElement(By.xpath("(//ul[contains(@class,'p-dropdown-items')]//li)[1]"));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
				System.out.println("clicked single select BU");
				waitutil.waitForOverlay();
			}
		}

		/*
		 * List<WebElement> options =
		 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
		 * MandatoryFieldsXpaths.MULTISELECT_LIST))); if (!options.isEmpty()) {
		 * 
		 * WebElement option = driver.findElement(By.xpath(
		 * "(//ul[contains(@class,'p-dropdown-items')]//li)[1]"));
		 * 
		 * ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
		 * System.out.println("clicked BU"); }
		 */
		waitutil.waitForOverlay();




		int labelsize = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(mandatoryLabelsLocator)).size();

		System.out.println(" Total mandatory fields found: " + labelsize);

		for (int i = 0; i < labelsize; i++) 
		{
			List<WebElement> mandatoryLabels = driver.findElements(mandatoryLabelsLocator);
			//System.out.println(mandatoryLabels.size());
			WebElement label = mandatoryLabels.get(i);
			if(label.isDisplayed() && label.isEnabled())
			{
				wait.until(ExpectedConditions.visibilityOf(mandatoryLabels.get(i)));
				String labelText = label.getText().replace("*", "").trim();

				System.out.println(labelText);
				String excelValue = fieldData.get(labelText);
				System.out.println(excelValue);

				if(label.getText().contains("Business Unit"))
				{
					System.out.println("BU Skipped");
				}

				else if(!label.findElements(By.xpath(MandatoryFieldsXpaths.TEXT_INPUT)).isEmpty() && excelValue!=null) 
				{
					WebElement input = label.findElement(By.xpath(MandatoryFieldsXpaths.TEXT_INPUT));
					String tag = input.getTagName().toLowerCase();
					if ((tag.equals("input") || tag.equals("textarea")) && input.isEnabled())
					{
						String currentValue = input.getAttribute("value");

						if(currentValue.trim().isEmpty() )
						{
							wait.until(ExpectedConditions.elementToBeClickable(input)).click();
							// CTRL+A + BACKSPACE instead of .clear() for better event triggering
							input.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
							input.sendKeys(excelValue);
						}
						else 
						{
							System.out.println("Skipped " + labelText + " because it already contains: " + currentValue);
						}
					}
				}
				else if(!label.findElements(By.xpath(MandatoryFieldsXpaths.DROPDOWN)).isEmpty())
				{

					WebElement dropdown = label.findElement(By.xpath(MandatoryFieldsXpaths.DROPDOWN));
					String classAttr = dropdown.getAttribute("class");
					if(classAttr.equalsIgnoreCase("p-multiselect-trigger"))
					{
						((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
						List<WebElement> list = driver.findElements(By.xpath(MandatoryFieldsXpaths.MULTISELECT_LIST));
						wait.until(ExpectedConditions.elementToBeClickable(list.get(0)));
						list.get(0).click();
						dropdown.click();
						waitutil.waitForOverlay();

					}
					else if(classAttr.contains("p-dropdown-trigger-icon") && dropdown.isEnabled())
					{
						WebElement drpexcelValue = label.findElement(By.xpath(MandatoryFieldsXpaths.DROPDOWN_PLACEHOLDER));

						if( drpexcelValue.getText().contains("Select") || drpexcelValue.getAttribute("placeholder")!=null && drpexcelValue.getAttribute("placeholder").contains("Select"))
						{
							wait = new WebDriverWait(driver, Duration.ofSeconds(30));
							waitutil.waitForOverlay();
							wait.until(ExpectedConditions.elementToBeClickable(dropdown));
							((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
							List<WebElement> drpoptions = driver.findElements(By.xpath(MandatoryFieldsXpaths.DROPDOWN_LIST));
							wait.until(ExpectedConditions.elementToBeClickable(drpoptions.get(0)));
							if (!drpoptions.isEmpty()) {

								//WebElement option = driver.findElement(By.xpath("(//ul[contains(@class,'p-dropdown-items')]//li)[1]"));
								By optFirst = By.xpath("(//ul[contains(@class,'p-dropdown-items')]//li)[1]");
								elementutil.click(optFirst);
								
								waitutil.waitForOverlay();
								//wait.until(ExpectedConditions.elementToBeClickable(firstOption)).click();
								// wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(firstOption))).click();
							}
						}

					}

				}
				else if(!label.findElements(By.xpath(MandatoryFieldsXpaths.DATE_INPUT)).isEmpty() && fieldData.containsKey(labelText))
				{
					WebElement input = label.findElement(By.xpath(MandatoryFieldsXpaths.DATE_INPUT));
					String tag = input.getTagName().toLowerCase();
					if ((tag.equals("input") && input.isEnabled()))
					{
						input.click();

						String[] parts = excelValue.split(",");
						int dateOffset = Integer.parseInt(parts[0].trim());
						String time = parts.length > 1 ? parts[1].trim() : null;

						DatePicker dp = new DatePicker();
						dp.selectDate(driver, dateOffset);

						if(!label.findElements(By.xpath(MandatoryFieldsXpaths.TIME_FIELD)).isEmpty() && time != null && !time.isEmpty())
						{
							WebElement timeIcon = label.findElement(By.xpath(MandatoryFieldsXpaths.TIME_FIELD));
							timeIcon.click();
							
							dp.selectTime(driver, time);
						}
					}
				}
			}

		}	
		//driver.findElement(By.tagName("body")).click();
	}
}
