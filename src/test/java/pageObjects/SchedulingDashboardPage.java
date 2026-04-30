package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SchedulingDashboardPage {

	WebDriver driver;
	WebDriverWait wait;
	public SchedulingDashboardPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	}
	
	@FindBy(xpath = "//img[@ptooltip='Scheduling Module']") WebElement iconScheduling;
	
	public void clickSchedulingIcon()
	{
		iconScheduling.click();
	}
	
	@FindBy(xpath = "//span[text()=' Schedule Dashboard ']") WebElement hdrSchedulingDashboard;
	
	public String getSchedulingHeader()
	{
		return hdrSchedulingDashboard.getText();
	}
	
	
}
