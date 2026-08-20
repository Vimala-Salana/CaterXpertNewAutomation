package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import testBase.BasePage;
import utilities.LoggerManager;

public class EventDashboardPage extends BasePage {

	WebDriver driver;
	List<String> finalizeStatus;

	public EventDashboardPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[contains(@class,'event-dashboard')]//div[@class='header-fs']")
	public List<WebElement> headersList;

	public List<String> readAllHeaders() {
		// System.out.println("Reading all headers");
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.visibilityOfAllElements(headersList));
		List<String> headers = new ArrayList<String>();
		for (WebElement list : headersList) {

			headers.add(list.getText());
		}
		return headers;

	}

	public boolean clickServiceLabelIcon(List<String> service, List<String> status, List<String> iconLabel) {
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.visibilityOfAllElements(headersList));
		// Looping through all the headers
		for (WebElement header : headersList) {
			String name = header.getText();
			boolean statusMatch = (status == null || status.stream().anyMatch(name::contains));
			if (service.stream().anyMatch(name::contains) && statusMatch) {
				LoggerManager.info("Service Status : " + name); // Matching header
				WebElement labelname = header
						.findElement(By.xpath("../following-sibling::div//*[contains(@class,'service-label')]"));
				String labelText = labelname.getText();
				if (iconLabel.stream().anyMatch(labelText::equalsIgnoreCase)) {
					// System.out.println(labelname.getText());
					WebElement icon = labelname.findElement(By.xpath("..//i")); // icon
					wait.until(ExpectedConditions.elementToBeClickable(icon)).click();
					// LoggerManager.info("Icon Name" + icon.getText());
					return true;
				}
			}
		}
		return false;
	}

	@FindBy(xpath = "//label[text()='Event #']/following::span[2]")
	WebElement txteventNo;

	public String getEventNo() {
		wait.until(ExpectedConditions.visibilityOf(txteventNo));
		return txteventNo.getText();
	}

	private final By hdrEventDashboard = By.xpath("//span[normalize-space()='Event Dashboard']");

	public void validateServiceStatus(List<String> serviceName) {
		waitutil.waitForOverlay();
		wait.until(ExpectedConditions.visibilityOfElementLocated(hdrEventDashboard));

		finalizeStatus = List.of("Sent", "Ack", "Accept", "Bill");

		for (WebElement header : headersList) {

			if (serviceName.stream().anyMatch(header.getText()::contains)) {

				String headerText = header.getText();

				LoggerManager.info(headerText);

				Assert.assertTrue(finalizeStatus.stream().anyMatch(headerText::contains),
						"Finalized Status does not Match" + headerText);
			}
		}

	}

}
