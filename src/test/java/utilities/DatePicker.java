package utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DatePicker {

	//selects date from calendar
	public void selectDate(WebDriver driver, String month, String date, String year)
	{
		while(true)
		{
			WebElement currentYear = driver.findElement(By.xpath("//button[@class='current']"));
			WebElement currentMonth = driver.findElement(By.xpath("//div[@class='bs-datepicker-head']//button[2]/span"));

			if(currentYear.getText().equals(year) && currentMonth.getText().equals(month))

				break;
			driver.findElement(By.xpath("//button[@class='next']")).click();
		}

		List<WebElement> allDates = driver.findElements(By.xpath("//td[@role='gridcell']//span[not(contains(@class, 'is-other-month'))]"));

		for(WebElement dt : allDates)
		{
			if(dt.getText().equals(date))
			{
				dt.click();
				break;
			}

		}
	}
}