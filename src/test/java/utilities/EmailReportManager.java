package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public final class EmailReportManager {

	private static final String TEMPLATE = "src/test/resources/email-report.html";

	private static final String SMTP_HOST = "smtp.office365.com";

	private static final String SMTP_PORT = "587";

	private static final String FROM_EMAIL = System.getenv("SMTP_USERNAME");

	private static final String PASSWORD = System.getenv("SMTP_PASSWORD");

	private static final String TO_EMAIL = "Vimala.Salana@hospiquesoftware.com";

	private static int passed;
	private static int failed;
	private static int skipped;

	private static long startTime;

	private EmailReportManager() {
	}

	public static void startExecution() {
		startTime = System.currentTimeMillis();
	}

	public static void recordSuccess() {
		passed++;
	}

	public static void recordFailure() {
		failed++;
	}

	public static void recordSkipped() {
		skipped++;
	}

	public static void sendReport() {

		try {
			sendEmail(generateHtml());

			System.out.println("Execution report email sent successfully.");

		} catch (Exception e) {
			System.err.println("Failed to send execution report email.");
			e.printStackTrace();
		}
	}

	private static String generateHtml() throws IOException {

		String html = Files.readString(Path.of(TEMPLATE));

		int total = passed + failed + skipped;

		double passRate = total == 0 ? 0 : (passed * 100.0) / total;

		long duration = System.currentTimeMillis() - startTime;

		String durationText = formatDuration(duration);

		String date = new SimpleDateFormat("dd MMM yyyy").format(new Date());

		return html.replace("{{DATE}}", date).replace("{{TOTAL}}", String.valueOf(total))
				.replace("{{PASSED}}", String.valueOf(passed)).replace("{{FAILED}}", String.valueOf(failed))
				.replace("{{SKIPPED}}", String.valueOf(skipped))
				.replace("{{PASS_RATE}}", String.format("%.2f%%", passRate)).replace("{{DURATION}}", durationText)
				.replace("{{APPLICATION}}", "CaterXpertSales").replace("{{ENVIRONMENT}}", "QA")
				.replace("{{BROWSER}}", "Chrome").replace("{{BRANCH}}", "0802 Patch")
				.replace("{{EXECUTION}}", "Selenium + TestNG")
				.replace("{{ALLURE_REPORT_URL}}", "http://your-server/allure-report")
				.replace("{{CI_BUILD_URL}}", "http://your-jenkins-server/job/automation");
	}

	private static String formatDuration(long milliseconds) {

		Duration duration = Duration.ofMillis(milliseconds);

		long hours = duration.toHours();

		long minutes = duration.toMinutesPart();

		return hours > 0 ? hours + "h " + minutes + "m" : minutes + "m";
	}

	private static void sendEmail(String html) throws Exception {

		Properties properties = new Properties();

		properties.put("mail.smtp.host", SMTP_HOST);

		properties.put("mail.smtp.port", SMTP_PORT);

		properties.put("mail.smtp.auth", "true");

		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
			}
		});

		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(FROM_EMAIL));

		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO_EMAIL));

		message.setSubject("Automation Execution Report");

		message.setContent(html, "text/html; charset=UTF-8");

		Transport.send(message);
	}
}