package testCases.SalesModule;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class LoginTest extends BaseClass{

	@Test(priority = 1)
	public void login() throws InterruptedException
	{
		LoginPage login = new LoginPage(DriverFactory.getDriver());
		login.enterCaterId();
		login.enterUserID();
		login.enterPassword();
		login.clickLogin();
		Thread.sleep(2000);
		 WebDriver driver = DriverFactory.getDriver();
		WebElement frameHdr = driver.findElement(By.xpath("//frame[@name='header']"));
		driver.switchTo().frame(frameHdr);
		driver.findElement(By.xpath("//img[@title='Home']")).click();
		driver.switchTo().defaultContent();
		WebElement frameright = driver.findElement(By.xpath("//frame[@name='right']"));
		driver.switchTo().frame(frameright);
		driver.findElement(By.xpath("//a[normalize-space(text())='Sales New']")).click();
		driver.switchTo().defaultContent();
	}
	
}
