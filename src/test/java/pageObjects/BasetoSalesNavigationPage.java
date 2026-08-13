package pageObjects;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testBase.BasePage;

public class BasetoSalesNavigationPage extends BasePage{

	public BasetoSalesNavigationPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath = "//frame[@name='header']") WebElement frameHdr;
	@FindBy(xpath = "//img[@title='Home']") List<WebElement> homeIcon;
	@FindBy(xpath = "//frame[@name='right']") WebElement frameright;
	//@FindBy(xpath = "//a[normalize-space(text())='Sales New']") WebElement lnkSalesNew;
	
	
	private final By frmHeader  = By.xpath("//frame[@name='header']");

	private final By iconHome = By.xpath("//img[@title='Home']");

	private final By frmRight = By.xpath("//frame[@name='right']");

	private final By lnkSalesNew = By.xpath("//a[normalize-space(text())='Sales New']");
	
	
	
	public By userId = By.name("userid");
	public String getLoggedInUserId()
	{
		String value = elementUtil.getValue(userId);
		System.out.println("UserId : "+value);
		return value;
	}
	

	public String salesNewNavigation()
	{
		wait.until(ExpectedConditions.visibilityOf(frameHdr));
		driver.switchTo().frame(frameHdr);
		elementUtil.clickIfPresent(iconHome);
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frameright);
		
		String loginId = getLoggedInUserId();
		
		elementUtil.click(lnkSalesNew);
		driver.switchTo().defaultContent();
		
		Cookie jsessionRoot = null;
		Cookie jsessionApp = null;

		Set<Cookie> cookies = driver.manage().getCookies();

		for (Cookie cookie : cookies) {

		    if ("JSESSIONID".equals(cookie.getName())) {

		        if ("/".equals(cookie.getPath())) {
		            jsessionRoot = cookie;
		        }

		        if ("/CaterXpert2026_0802".equals(cookie.getPath())) {
		            jsessionApp = cookie;
		        }
		    }
		}

		System.out.println("Root JSESSIONID found: "
		        + (jsessionRoot != null));

		System.out.println("Application JSESSIONID found: "
		        + (jsessionApp != null));
		driver.quit();
		WebDriver driver2 = new ChromeDriver();

		driver2.manage().window().maximize();
		driver2.get("https://testapps.aquilasoftware.com");
		driver2.manage().addCookie(jsessionRoot);
		driver2.manage().addCookie(jsessionApp);driver2.manage().addCookie(jsessionRoot);
		driver2.manage().addCookie(jsessionApp);System.out.println("Both JSESSIONID cookies restored.");
		driver2.get("https://testapps.aquilasoftware.com/CaterXpertSales2026_0802/resources/CaterXpertSales/CaterXpertSales.html#/sales/event-listing");
       
		return loginId;
	}
	
	private final By lnkBaseNavigation = By.xpath("//span[text()=' dining ']");
	private final By lnkKitchenNew = By.xpath("//a[normalize-space(text())='Kitchen New']");
	
	public void navigateTokitchen()
	{
		elementUtil.click(lnkBaseNavigation);
		driver.switchTo().frame(frameright);
		elementUtil.click(lnkKitchenNew);
		driver.switchTo().defaultContent();
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.overlay")));

	}
	
	
}
