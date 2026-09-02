package utilities;

import org.testng.IAnnotationTransformer;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, IAnnotationTransformer, IExecutionListener {

	/*
	 * @Override public void transform(ITestAnnotation annotation, Class testClass,
	 * Constructor testConstructor, Method testMethod) {
	 * 
	 * annotation.setRetryAnalyzer(RetryAnalyzer.class); }
	 */

	@Override
	public void onTestFailure(ITestResult result) {

		EmailReportManager.recordFailure();

		ReportManager.fail(result.getMethod().getMethodName() + " Failed");

		ReportManager.attachException(result.getThrowable());

		ReportManager.attachExecutionLogs();
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		EmailReportManager.recordSuccess();

		ReportManager.pass(result.getMethod().getMethodName() + " Passed");

		ReportManager.attachExecutionLogs();
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		EmailReportManager.recordSkipped();

		ReportManager.info(result.getMethod().getMethodName() + " Skipped");

		ReportManager.attachException(result.getThrowable());

		ReportManager.attachExecutionLogs();
	}

	@Override
	public void onExecutionStart() {
		EmailReportManager.startExecution();
	}

	@Override
	public void onExecutionFinish() {
		EmailReportManager.sendReport();
	}

}
