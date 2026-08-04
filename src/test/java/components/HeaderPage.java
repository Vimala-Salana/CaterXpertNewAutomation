package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import testBase.BasePage;

public class HeaderPage extends BasePage{

	public HeaderPage(WebDriver driver)
	{  
		super(driver);
	}

	private final By iconBaseNavigation = By.xpath("//span[text()=' dining ']");
	public void baseNavigation()
	{
		elementUtil.click(iconBaseNavigation);
	}

	@FindBy(xpath = "//nav//div[contains(@class,'d-flex')]//span[contains(.,'menu')]") WebElement hambergerMenu;
	private final By iconHamburgerMenu = By.xpath("//nav//div[contains(@class,'d-flex')]//span[contains(.,'menu')]");
	public void clickhambergerMenu() {

		/*
		 * wait.until(ExpectedConditions.elementToBeClickable(hambergerMenu));
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(
		 * "div.overlay"))); //((JavascriptExecutor)
		 * driver).executeScript("arguments[0].click();", menu); hambergerMenu.click();
		 * waitutil.waitForOverlay();
		 */
		elementUtil.click(iconHamburgerMenu);
	}



}
