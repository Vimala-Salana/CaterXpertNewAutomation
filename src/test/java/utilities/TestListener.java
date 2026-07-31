package utilities;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener{

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
