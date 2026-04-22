package utilities;

import java.time.Duration;
import java.time.Month;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DatePicker {

	//selects date from calendar
	public void selectDate(WebDriver driver, String month, String date, String year)
	{
		while(true)
		{
			WebElement Currentyear = driver.findElement(By.xpath("//button[@class='current']"));
			WebElement Currentmonth = driver.findElement(By.xpath("//div[@class='bs-datepicker-head']//button[2]/span"));

			if(Currentyear.getText().equals(year) && Currentmonth.getText().equals(month))

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