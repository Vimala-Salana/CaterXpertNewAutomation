package testCases.SalesModule;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import testBase.BaseClass;

public class LoginTest extends BaseClass{

	@Test(priority = 1)
	public void login() throws InterruptedException
	{
		LoginPage login = new LoginPage(driver);
		login.enterCaterId();
		login.enterUserID();
		login.enterPassword();
		login.clickLogin();
		Thread.sleep(2000);
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
