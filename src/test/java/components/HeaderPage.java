package components;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.WaitUtils;

public class HeaderPage {
	private WebDriver driver;
	WebDriverWait wait; 
	WaitUtils waitutil;

	public HeaderPage(WebDriver driver)
	{  
		this.driver = driver;
		PageFactory.initElements(driver,this);
		waitutil = new WaitUtils(driver);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	}

	@FindBy(xpath = "//span[text()=' dining ']") WebElement baseNavigation;
	public void baseNavigation()
	{
		baseNavigation.click();
	}

	@FindBy(xpath = "//nav//div[contains(@class,'d-flex')]//span[contains(.,'menu')]") WebElement hambergerMenu;
	public void clickhambergerMenu() {

		wait.until(ExpectedConditions.elementToBeClickable(hambergerMenu));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);
		hambergerMenu.click();
		waitutil.waitForOverlay();

	}



}
