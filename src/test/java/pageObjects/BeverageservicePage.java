package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BeverageservicePage {
	public WebDriver driver;
	String filepath;
	String sheetname;
	WebDriverWait wait;
	public BeverageservicePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	}

	public void validation()throws InterruptedException {
		System.out.println("waitinh");
		Thread.sleep(10000);	
		System.out.println("waitinh");
		List<WebElement> header = driver.findElements(By.xpath("((//thead[@class='thead-dark'])[4]//th)[position()>1]"));
		int noOfItems = driver.findElements(By.xpath("(//div[contains(@id,'p-tabpanel')]//table//tbody//tr[3])")).size();
		System.out.println("No of items size  -  "+noOfItems);
		for(int i = 0 ; i < noOfItems ; i++) {
			List<WebElement> itemDetails = driver.findElements(By.xpath("((//div[contains(@id,'p-tabpanel')]//table//tbody//tr[3])["+(i+1)+"]//td)[position()>1]"));
			System.out.println("itemDetails size  -  "+itemDetails.size());
			int j = 0;
			for(WebElement element : itemDetails) {
				boolean childPresent = element.findElements(By.xpath("./*")).size() > 0;
				if(childPresent) {
					try {
						String value = element.findElement(By.xpath(".//input")).getAttribute("value");
						System.out.println(header.get(j).getText()+" - "+value);
					}
					catch(Exception e) {
						String value = element.findElement(By.xpath(".//span")).getText();
						System.out.println(header.get(j).getText()+" - "+value);
					}
				}
				else {
					System.out.println(header.get(j).getText()+" - "+element.getText());
				}
				j++;
			}
		}
	}


}
