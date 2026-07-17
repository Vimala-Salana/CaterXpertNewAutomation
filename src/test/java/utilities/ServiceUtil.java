package utilities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import constants.MandatoryFieldsXpaths;

public class ServiceUtil {

	WebDriver driver;
	WebDriverWait wait;
	WaitUtils waitutil;
	public ServiceUtil(WebDriver driver)
	{
		this.driver = driver;
		waitutil = new WaitUtils(driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}


	String dropdowns = "following-sibling::p-dropdown//span[contains(text(),'Select')]";
	String inputOrTextarea = "following-sibling::div//*[self::input or self::textarea] | following-sibling::input[not(@placeholder) and not(@type='hidden') and not(@type='radio')]";
	String radiobuttons = "following-sibling::div//div[contains(@class,'p-radiobutton-box')]";
	String date = "following-sibling::input[contains(@placeholder,'MM/DD/YYYY')]";
	String time = "following-sibling::div//span";
	String timeOptions = "//div[contains(@class,'AvenirLTStd-Medium')]";
	String checkBoxes = "following-sibling::div//div[@class='p-checkbox-box']";
	String multiselectDropdowns = "following-sibling::p-multiselect//div[@class='p-multiselect-trigger']";

	public void fillInfoMandatoryFields()
	{
		waitutil.waitForOverlay();
		By mandatoryLabelsLocator  = By.xpath(MandatoryFieldsXpaths.MANDATORY_LABEL);

		List<WebElement> mandatoryLabels = driver.findElements(mandatoryLabelsLocator);
		//int labelsize = wait.until(ExpectedConditions.visibilityOfAllElements(mandatoryLabels)).size();

		//System.out.println(" Total mandatory fields found: " + labelsize);
		
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mandatoryLabelsLocator));
		for(WebElement label : mandatoryLabels)
		{
			
			if(label.isDisplayed() && label.isEnabled())
			{
				//String labelText = label.getText().replace("*", "").trim();

				//System.out.println(labelText);
			}

			if(!label.findElements(By.xpath(dropdowns)).isEmpty())			
			{
				System.out.println("Single Select Dropdown");
				WebElement drpdown = label.findElement(By.xpath(dropdowns));
				wait.until(ExpectedConditions.elementToBeClickable(drpdown));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", drpdown);
				List<WebElement> drpoptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.xpath(MandatoryFieldsXpaths.DROPDOWN_LIST)));
				if (!drpoptions.isEmpty()) 
				{

					WebElement option = driver.findElement(By.xpath("(//ul[contains(@class,'p-dropdown-items')]//li)[1]"));

					((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
					//wait.until(ExpectedConditions.elementToBeClickable(firstOption)).click();
					// wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(firstOption))).click();
				}
			}
			
			else if(!label.findElements(By.xpath(multiselectDropdowns)).isEmpty())
			{
				System.out.println("Multi Select Dropdown");

				WebElement multidrpdown = label.findElement(By.xpath(multiselectDropdowns));
				wait.until(ExpectedConditions.elementToBeClickable(multidrpdown));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", multidrpdown);
				List<WebElement> drpoptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.xpath(MandatoryFieldsXpaths.MULTISELECT_LIST)));
				if (!drpoptions.isEmpty()) 
				{

					WebElement option = driver.findElement(By.xpath("(//ul[contains(@class,'p-multiselect-items')]//li)[1]"));

					((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
					//wait.until(ExpectedConditions.elementToBeClickable(firstOption)).click();
					// wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(firstOption))).click();
				}
			}

			else if(!label.findElements(By.xpath(radiobuttons)).isEmpty())

			{
				List<WebElement> radioOptions = label.findElements(By.xpath(radiobuttons));
				radioOptions.get(0).click(); //YES
			}

			else if(!label.findElements(By.xpath(checkBoxes)).isEmpty())
			{
				WebElement checkbox = label.findElement(By.xpath(checkBoxes));
				checkbox.click();
			}

			else if(!label.findElements(By.xpath(inputOrTextarea)).isEmpty())
			{
				waitutil.waitForOverlay();
				WebElement textbox = label.findElement(By.xpath(inputOrTextarea));
				textbox.sendKeys(Keys.CONTROL + "a");
				textbox.sendKeys(Keys.DELETE);
				textbox.sendKeys("123");
			}


			else if(!label.findElements(By.xpath(date)).isEmpty())
			{
				WebElement datepicker = label.findElement(By.xpath(date));

				LocalDate date = LocalDate.now();
				DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("MMddyyyy");

				datepicker.sendKeys(date.format(formatter));

				WebElement timeicon = label.findElement(By.xpath(time));
				timeicon.click();

				List<WebElement> drpoptions = driver.findElements(By.xpath(timeOptions));

				for(WebElement option:drpoptions)
				{
					if(option.getText().equals("01:00"))
					{
						wait.until(ExpectedConditions.elementToBeClickable(option));
						option.click();
						break;
					}
				}

			}

		}
	}

	public void Info()
	{
		//List<WebElement> infoHdr = driver.findElements(By.xpath("(//p[text()=' Info '])[1]|" ));
		List<WebElement> infoHdr = driver.findElements(By.xpath("//p[contains(normalize-space(),'Info')]" ));
		if(!infoHdr.isEmpty())
		{
			fillInfoMandatoryFields();
			WebElement infoSave = driver.findElement(By.xpath("//button[text()='Save ']"));
			wait.until(ExpectedConditions.elementToBeClickable(infoSave)).click();
			waitutil.waitForOverlay();
		}
		else
		{
			System.out.println("Info is not displayed");
		}
	}


	//Constraints

	public boolean Constraints()
	{
		waitutil.waitForOverlay();

		By constraintslocator = By.xpath("//p[contains(text(),'Constraints')]");

		try
		{
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
			shortWait.until(ExpectedConditions.visibilityOfElementLocated(constraintslocator));
			//wait.until(ExpectedConditions.visibilityOfElementLocated(constraintslocator));

			List<WebElement> constraintNames = driver.findElements(By.xpath("//label[text()=' Constraint ']//following-sibling::div"));

			constraintNames.forEach(constraint -> System.out.println("Constraint Name : " + constraint.getText()));

			WebElement constraintSave = driver.findElement(By.xpath("//span[text()='Save']"));

			wait.until(ExpectedConditions.elementToBeClickable(constraintSave));
			constraintSave.click();

			waitutil.waitForOverlay();

			return true;
		}
		catch(TimeoutException e)
		{
			System.out.println("No Constraints are displayed");
			return false;
		}
	}

	//Approvals

	By pendingConstraints = By.xpath("//span[text()=' Pending ']");
	By approvalSave = By.xpath("//button[text()=' Save ']");
	By approvalClose = By.xpath("//button[text()=' Close ']");
	public void approveConstraints(boolean constraintExists, String eventNo)
	{ 
		if(constraintExists) 		//constraintExists==true
		{
			waitutil.waitForOverlay();

			By noRecords = By.xpath("//p[contains(text(),'records found')]");

			wait.until(ExpectedConditions.or(ExpectedConditions.invisibilityOfElementLocated(noRecords),
					(ExpectedConditions.visibilityOfElementLocated(pendingConstraints))));	

			driver.findElement(By.xpath("//input[@type='search']")).sendKeys(eventNo,Keys.ENTER);
			waitutil.waitForOverlay();
			//Thread.sleep(5000);
			int size = driver.findElements(pendingConstraints).size();
			System.out.println("Number of Pending Constraints : "+size);
			for(int i=0;i<size;i++)
				//for(WebElement pendConst : pendingConstraints)
			{
				waitutil.waitForOverlay();

				WebElement pending = wait.until(ExpectedConditions.elementToBeClickable(pendingConstraints));
				pending.click();
				waitutil.waitForOverlay();

				wait.until(ExpectedConditions.elementToBeClickable(approvalSave)).click();

				waitutil.waitForOverlay();

			}
			waitutil.waitForOverlay();
			wait.until(ExpectedConditions.elementToBeClickable(approvalClose)).click();

		}
	}
}
