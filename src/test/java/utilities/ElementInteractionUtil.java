package utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementInteractionUtil {

	private final WebDriver driver;
	private final WebDriverWait wait;
	private final WaitUtils waitUtils;
	private final JavascriptExecutor js;
	private static final Duration TIMEOUT = Duration.ofSeconds(10);
	private static final int MAX_CLICK_ATTEMPTS = 3;

	public ElementInteractionUtil(WebDriver driver) {

		this.driver = driver;
		this.wait = new WebDriverWait(driver, TIMEOUT);
		waitUtils = new WaitUtils(driver);
		js = (JavascriptExecutor) driver;
	}


	public void click(By locator) {

		long startTime = System.nanoTime();

		Exception lastException = null;

		for (int attempt = 1; attempt <= MAX_CLICK_ATTEMPTS; attempt++) {

			try {

				try {
					// Native Click Retry
					nativeClick(locator);

					LoggerManager.logActionSuccess(locator, startTime,  "Native Click", attempt);		
					return;

				} catch(ElementNotInteractableException  exception) {
					LoggerManager.warn("Attempt "+ attempt+ "/"+ MAX_CLICK_ATTEMPTS+ " | Native click failed for "+ locator+ ". Reason: "
							+ exception.getMessage()
							+ ". Trying Actions click.");
				}

				try {
					actionsClick(locator);

					LoggerManager.logActionSuccess(locator, startTime,  "Actions Click", attempt);

					return;

				} catch(ElementNotInteractableException  exception) {

					LoggerManager.warn("Attempt "+ attempt+ "/"+ MAX_CLICK_ATTEMPTS+ " | Actions click failed for "+ locator+ ". Reason: "
							+ exception.getMessage()
							+ ". Trying JavaScript click.");
				}
				javascriptClick(locator);
				LoggerManager.logActionSuccess(locator, startTime,  "Java Script Click", attempt);			
				return;
				
			}
			catch (ElementClickInterceptedException e) {
			    lastException = e;
			    LoggerManager.warn("Click intercepted. Retrying...");
			}
			catch(StaleElementReferenceException | NoSuchElementException | TimeoutException exception){                                                                    
				lastException = exception;	
				LoggerManager.logActionFailure(locator, startTime, "Click", exception,attempt);

			}
			catch (Exception exception) {

				lastException = exception;
				break;
			}
		}

		long duration = Duration.ofNanos(System.nanoTime() - startTime).toMillis();

		String message = String.format("Unable to click [%s]. Duration: %d ms.  Reason: %s", locator, duration, 
				lastException != null ? lastException.getMessage() : "Unknown");

		LoggerManager.error(message, lastException);
		ReportManager.fail(message);

		throw new RuntimeException(message, lastException);
	}

	//Reusable method for Normal Click
	public void nativeClick(By locator)
	{
		waitUtils.waitForOverlay();
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);

		element.click();
	}

	//Reusable method for Actions Click
	public void actionsClick(By locator) {

		waitUtils.waitForOverlay();
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));	
		new Actions(driver)
		.moveToElement(element)
		.click()
		.perform();


	}

	//Reusable method for JavaScript Click
	public void javascriptClick(By locator) {

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		js.executeScript("arguments[0].scrollIntoView({block:'center'});",element);

		js.executeScript("arguments[0].click();",element);


	}

	//Click Element only if present - Use it for dynamic Elements/Icons
	public boolean clickIfPresent(By locator) {

		if (driver.findElements(locator).isEmpty()) {

			LoggerManager.info("Optional element not present: " + locator);

			return false;
		}

		click(locator);
		return true;
	}

	//Reusable method for SendKeys
	public void typeText(By locator, CharSequence... keys) {
		waitUtils.waitForOverlay();
		long startTime = System.nanoTime();
		waitUtils.waitForOverlay();

		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			//clear
			element.clear();
			element.sendKeys(keys); //Enter value
			LoggerManager.logActionSuccess(locator, startTime, "Type Text", null);
		} catch(Exception e) {
			LoggerManager.logActionFailure(locator, startTime, "SendKeys", e, null);
		}

	}

	//Reusable method for getText
	public String getText(By locator) {
		long startTime = System.nanoTime();
		try {
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			LoggerManager.logActionSuccess(locator, startTime, "Get Text", null);
			return element.getText();
		} catch(Exception e) {
			LoggerManager.logActionFailure(locator, startTime, "Get Text", e, null);
			throw e;
		}
	}

	//Reusable method for getAttribute
	public String getAttribute(By locator, String attributeName) {
	    long startTime = System.nanoTime();
	    try {
	    	WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	        LoggerManager.logActionSuccess(locator, startTime, "Get Attrubute", null);
			return element.getAttribute(attributeName);
	    } catch(Exception e) {
			LoggerManager.logActionFailure(locator, startTime, "Get Attribute", e, null);
			throw e;
	    }
		
	}

	//Reusable method for getAttribute of Value

	public String getValue(By locator) {

		return getAttribute(locator,"value");
	}

}