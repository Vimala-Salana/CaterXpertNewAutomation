package utilities;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

public class ElementInteractionUtil {
	
	private static final Logger logger = LogManager.getLogger(ElementInteractionUtil.class);

    public WebDriver driver;
    private WebDriverWait wait;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public ElementInteractionUtil(WebDriver driver) {
        
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    public boolean click(WebElement webElement) {
        boolean status = false;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            webElement.click();
            logger.info("Clicked on element: " + webElement);
            status = true;
        } catch (NoSuchElementException | TimeoutException e) {
            logger.error("Unable to click on element: " + webElement, e);
        }
        return status;
    }

    public boolean sendKeys(WebElement webElement, String text) {
        boolean status = false;
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            webElement.clear();
            webElement.sendKeys(text);
            logger.info("Entered text into element: " + webElement);
            status = true;
        } catch (Exception e) {
            logger.error("Unable to send text to element: " + webElement, e);
        }
        return status;
    }
}