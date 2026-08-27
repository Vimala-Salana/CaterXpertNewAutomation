package utilities;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import factory.DriverFactory;
import io.qameta.allure.Allure;

public final class ReportManager {

	public static void info(String message) {

		Allure.step(message);

		LoggerManager.info(message);

	}

	public static void pass(String message) {

		Allure.step("PASS: " + message);

		LoggerManager.info(message);

	}

	public static void fail(String message) {

		Allure.step("FAIL: " + message);

		LoggerManager.error(message);

		attachScreenshot("Failure Screenshot");

	}

	public static void attachExecutionLogs() {

		String logs = LoggerManager.getLogs();

		if (!logs.isBlank()) {

			Allure.addAttachment("Execution Logs", "text/plain", logs);
		}

	}

	public static void attachException(Throwable throwable) {

		if (throwable != null) {

			Allure.addAttachment("Exception", "text/plain", throwable.toString(), ".txt");

			// LoggerManager.error("Exception occurred", throwable);
			LoggerManager.clearLogs();
		}
	}

	private static void attachScreenshot(String name) {

		try {

			byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);

			Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");

			LoggerManager.info("Screenshot attached: " + name);

		} catch (Exception exception) {

			LoggerManager.error("Unable to capture screenshot", exception);
		}
	}

	public static void attachText(String name, String content) {

		Allure.addAttachment(name, content);

	}

}