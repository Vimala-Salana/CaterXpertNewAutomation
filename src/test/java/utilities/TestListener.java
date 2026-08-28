package utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

public class TestListener implements ITestListener, IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ReportManager.fail(result.getMethod().getMethodName() + " Failed");

		ReportManager.attachException(result.getThrowable());

		ReportManager.attachExecutionLogs();
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ReportManager.pass(result.getMethod().getMethodName() + " Passed");

		ReportManager.attachExecutionLogs();
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ReportManager.info(result.getMethod().getMethodName() + " Skipped");

		ReportManager.attachException(result.getThrowable());

		ReportManager.attachExecutionLogs();
	}
}
