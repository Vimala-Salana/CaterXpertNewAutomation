package utilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

	WebDriver driver;
	WebDriverWait wait;
	public WaitUtils(WebDriver driver)
	{
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
	}
	public void waitForOverlay()
	{
		wait.until(driver -> {
			try
			{
				List<WebElement> overlays =
						driver.findElements(By.cssSelector(
								
								
											"div.overlay, " +
								            "div.spinner, " +
								            "div.ngx-spinner-overlay, " +
								            ".swal2-backdrop-show, " +
								            ".swal2-popup, " +
								            ".swal2-container," +
								            ".p-toast," +
								            ".p-component-overlay"));
				
				/*
				 * .cdk-overlay-pane .mat-dialog-container .p-toast .swal2-popup
				 * remove the above if you get timeoutexception
				 */
				for (WebElement e : overlays)   // looping through all the overlays and checking whether they are present or not
				{
					if (e.isDisplayed())
					{
						return false;  			// if overaly's are present return false i.e wait for overlay to disappear
					}
				}

				return true;  	 				//if overaly's are not present then return true
			}
			catch (Exception e)					//for StaleElement and other Exceptions
			{
				return true;
			}
		});

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
