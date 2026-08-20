package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import testBase.BasePage;

public class MenuViewPage extends BasePage {

	public MenuViewPage(WebDriver driver) {
		super(driver);
	}

	private final By txtMenuOption = By.xpath("(//tr[@class='eventstatus-row'])[2]//td[1]");

	public String getMenuOption() {
		return elementUtil.getText(txtMenuOption);
	}

	private final By txtCourse = By.xpath("(//tr[@class='eventstatus-row'])[3]//td[1]");

	public String getCourse() {
		return elementUtil.getText(txtCourse);
	}

	private final By txtMenuItem = By.xpath("(//tr[@class='eventstatus-row'])[4]//td[2]//textarea");

	public String getMenuItem() {
		return elementUtil.getAttribute(txtMenuItem, "value");
	}

}
