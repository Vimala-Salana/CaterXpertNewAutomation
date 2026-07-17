package utilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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
		wait.until(d -> {
			try
			{
				List<WebElement> overlays =
						driver.findElements(By.cssSelector(
							    "div.overlay," +
							    	    "div.spinner," +
							    	    "div.ngx-spinner-overlay," +
							    	    ".swal2-container," +
							    	    ".swal2-title"
							    	   // "mat-dialog-container," +
							    	   // ".p-component-overlay," +
							    	   // ".cdk-overlay-backdrop"
							    	));
				
				/*
				 * .cdk-overlay-pane .mat-dialog-container .p-toast .swal2-popup
				 * remove the above if you get timeoutexception
				 */
				  for (WebElement e : overlays)
		            {
		                if (e.isDisplayed())
		                {
		                    return false;
		                }
		            }

		            return true;
		        }
		        catch (StaleElementReferenceException e)
		        {
		            return false;
		        }
		});

		wait.until(d ->
		driver.findElements(By.cssSelector(".ng-animating")).isEmpty());
	}
}
