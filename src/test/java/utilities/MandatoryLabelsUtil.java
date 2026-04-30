package utilities;

import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
		
		wait.until(new ExpectedCondition<Boolean>() {
		    @Override
		    public Boolean apply(WebDriver d) {
		        List<WebElement> labels = d.findElements(mandatoryLabelsLocator);

		        if (labels.isEmpty()) return false;

		        String first = labels.get(0).getText().replace("*", "").trim();

		        return first.equalsIgnoreCase("Business Unit");
		    }
		});

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

			if (!driver.findElements(multiSelect).isEmpty()) 
			{
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(multiSelect));
				WebElement option = driver.findElement(By.xpath("(//ul[contains(@class,'p-multiselect-items')]//li)[1]"));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
				//System.out.println("clicked multi-select BU");
				waitutil.waitForOverlay();
			}

			else {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(singleSelect));
				WebElement option = driver.findElement(By.xpath("(//ul[contains(@class,'p-dropdown-items')]//li)[2]"));

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
				//System.out.println("clicked single select BU");
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

		


		int labelsize = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mandatoryLabelsLocator)).size();

		System.out.println(" Total mandatory fields found: " + labelsize);

		for (int i = 1; i < labelsize; i++) 
		{
			List<WebElement> mandatoryLabels = driver.findElements(mandatoryLabelsLocator);
			//System.out.println(mandatoryLabels.size());
			WebElement label = wait.until(ExpectedConditions.elementToBeClickable(mandatoryLabels.get(i)));
			if(label.isDisplayed() && label.isEnabled())
			{
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
						// 1. Capture the value and handle potential nulls
				        String currentValue = input.getAttribute("value");
				   
				        // 2. Expand the check to ignore default zero values or placeholders
				        if(currentValue.trim().isEmpty() )
				        {
				            wait.until(ExpectedConditions.elementToBeClickable(input)).click();
				            // 4. Use CTRL+A + BACKSPACE instead of .clear() for better event triggering
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
						list.get(0).click();
						dropdown.click();

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

								WebElement option = driver.findElement(By.xpath("(//ul[contains(@class,'p-dropdown-items')]//li)[1]"));

								((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
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
						String text = labelText +" Date";
						System.out.println(text+" "+fieldData.get(text));
						LocalDate localDate = LocalDate.now().plusDays(Long.parseLong(fieldData.get(text)));
						String date = localDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
						System.out.println(date);
						String dateParts[] = date.split("/");
						int monthNumber = Integer.parseInt(dateParts[0]);
						String month = new DateFormatSymbols().getMonths()[monthNumber - 1];  // Converts month number to name
						String day = dateParts[1];
						String year = dateParts[2];

						DatePicker dp = new DatePicker();
						dp.selectDate(driver, month, day, year);

						WebElement time = label.findElement(By.xpath(MandatoryFieldsXpaths.TIME_FIELD));
						time.click();
						List<WebElement> drpoptions = driver.findElements(By.xpath(MandatoryFieldsXpaths.TIME_OPTIONS));

						for(WebElement option:drpoptions)
						{
							if(option.getText().equals(excelValue))
							{
								Thread.sleep(1000);
								option.click();
								break;
							}
						}
					}
				}
			}
			
		}	
		driver.findElement(By.tagName("body")).click();
	}
}
