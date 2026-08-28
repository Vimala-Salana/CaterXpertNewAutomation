package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	private int retryCount = 0;

	private static final int MAX_RETRIES = 1;

	@Override
	public boolean retry(ITestResult result) {

		System.out.println(">>> RETRY CHECK: " + result.getMethod().getMethodName() + " | retryCount = " + retryCount);

		if (retryCount < MAX_RETRIES) {

			retryCount++;

			System.out.println(">>> RETRYING: " + result.getMethod().getMethodName() + " | Retry #" + retryCount);

			return true;
		}

		System.out.println(">>> NO MORE RETRIES: " + result.getMethod().getMethodName());

		return false;
	}
}