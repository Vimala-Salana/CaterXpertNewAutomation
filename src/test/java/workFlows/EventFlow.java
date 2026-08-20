package workFlows;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import components.HamburgerMenuPage;
import components.HeaderPage;
import pageObjects.CreateEventPage;
import utilities.LoggerManager;

public class EventFlow {

	private HamburgerMenuPage hambergerMenuPage;
	private HeaderPage headerPage;
	CreateEventPage eventPage;

	public EventFlow(WebDriver driver) {
		hambergerMenuPage = new HamburgerMenuPage(driver);
		headerPage = new HeaderPage(driver);
		eventPage = new CreateEventPage(driver);
	}

	public void navigateToCreateEvent() {
		headerPage.clickhambergerMenu();
		hambergerMenuPage.clickCreateEvent();
	}

	public String createEvent(Map<String, String> data) {
		Assert.assertEquals(eventPage.getCreateEventhdr(), "Create Event");

		eventPage.fillEventMandatoryfields(data); // filling all mandatory fields
		eventPage.clickCreatebtn();
		eventPage.clickYesInTaxExpiryPopupifExists();

		boolean constraintExists = eventPage.eventConstraints();
		String eventNo = eventPage.getEventNo();
		eventPage.clickClosebtn();
		LoggerManager.info("Event No : " + eventNo);
		if (constraintExists) {
			headerPage.clickhambergerMenu();
			hambergerMenuPage.clickApprovals();
			eventPage.ApproveEventConstraints(eventNo);
		}
		return eventNo;
	}

	public String createEventfromEventPage(Map<String, String> data) {
		navigateToCreateEvent();
		return createEvent(data);
	}
}
