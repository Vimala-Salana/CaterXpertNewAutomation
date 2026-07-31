package utilities;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;

import io.qameta.allure.Allure;


public final class LoggerManager {


	private static final Logger logger = LoggerFactory.getLogger(LoggerManager.class);

	    private static final ThreadLocal<StringBuilder> testLogs = ThreadLocal.withInitial(StringBuilder::new);

	    private static void append(String level, String message) {

	        testLogs.get()
	                .append("[")
	                .append(level)
	                .append("] ")
	                .append(message)
	                .append(System.lineSeparator());
	    }

    public static void info(String message) 
    {

        logger.info(message);
        append("INFO", message);

    }


    public static void debug(String message)
    {

        logger.debug(message);
        append("DEBUG", message);

    }


    public static void warn(String message)
    {

        logger.warn(message);
        append("WARN", message);

    }

    public static void error(String message) 
    {

        logger.error(message);
        append("ERROR", message);

    }


    public static void error(String message,Throwable throwable) 
    {
        logger.error(message,throwable);
        append("ERROR",message + " | " + throwable.getClass().getSimpleName() + " : " + throwable.getMessage());

    }
    
    public static String getLogs() {

        return testLogs.get().toString();
    }
    
    public static void clearLogs() {

        testLogs.remove();
    }
    
    public static void logActionSuccess(By locator, long startTime, int attempt, String clickType) {

        long duration = Duration.ofNanos( System.nanoTime() - startTime).toMillis();

        String message = String.format(
                "%s succeeded | Locator=%s | Attempt=%d | Duration=%d ms",
                clickType,
                locator,
                attempt,
                duration);
        
        logger.info(message);

        append("INFO", message);

        Allure.step(message);
    }

}
