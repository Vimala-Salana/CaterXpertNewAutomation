package utilities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constants.MandatoryFieldsXpaths;

public class DatePicker {

	//selects date from calendar
	public void selectDate(WebDriver driver, int dateOffset)
	{
		LocalDate date = LocalDate.now().plusDays(dateOffset); 

		String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

		String day = String.valueOf(date.getDayOfMonth());

		String year = String.valueOf(date.getYear());

		while(true)
		{
			WebElement currentYear = driver.findElement(By.xpath("//button[@class='current']"));
			WebElement currentMonth = driver.findElement(By.xpath("//div[@class='bs-datepicker-head']//button[2]/span"));

			System.out.println(currentYear.getText() +"        "+currentMonth.getText());

			if(currentYear.getText().equals(year) && currentMonth.getText().equals(month))

				break;
			driver.findElement(By.xpath("//button[@class='next']")).click();
		}

		List<WebElement> allDates = driver.findElements(By.xpath("//td[@role='gridcell']//span[not(contains(@class, 'is-other-month'))]"));

		for(WebElement dt : allDates)
		{
			if(dt.getText().equals(day))
			{
				dt.click();
				break;
			}

		}
	}

	public void selectTime(WebDriver driver, String time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		 String[] timeParts = time.split(" ");
		String timeValue = timeParts[0];    // 10:00
		String amPm = timeParts[1];    // AM

		if (amPm.equalsIgnoreCase("AM")) 
		{
			WebElement amRadio = driver.findElement(By.xpath("//p-radiobutton[@value='AM']"));

			if (!amRadio.isSelected()) 
			{
				amRadio.click();
			}

		} 
		else if (amPm.equalsIgnoreCase("PM")) 
		{

			WebElement pmRadio = driver.findElement(By.xpath("//p-radiobutton[@value='PM']"));

			if (!pmRadio.isSelected()) 
			{
				pmRadio.click();
			}
		}

		List<WebElement> drpoptions = driver.findElements(By.xpath(MandatoryFieldsXpaths.TIME_OPTIONS));
		for (WebElement option : drpoptions) 
		{

			if (option.getText().trim().equals(timeValue)) 
			{
				wait.until(ExpectedConditions.elementToBeClickable(option));
				option.click();
				break;
			}
		}
	}
}