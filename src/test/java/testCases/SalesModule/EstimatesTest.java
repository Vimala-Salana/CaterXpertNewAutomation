package testCases.SalesModule;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import factory.DriverFactory;
import pageObjects.EstimatesPage;
import pageObjects.EventDashboardPage;
import testBase.BaseClass;

public class EstimatesTest extends BaseClass{
	
	EstimatesPage estimates;	
	 @BeforeClass
	    public void initPages() 
	 {
		  estimates = new EstimatesPage(DriverFactory.getDriver());
	    }
	@Test (priority =1)
	public void estimates()
	{
		EventDashboardPage dashboard = new EventDashboardPage(DriverFactory.getDriver());
		List<String> service = List.of("Estimates");
		List<String> iconlabel = List.of("Estimates Lite","Estimates");
		dashboard.clickServiceLabelIcon(service, null, iconlabel);
		
		//System.out.println(estimates.getEstimateshdr());

		if(estimates.getEstimateshdr().trim().equals("Estimate Sections"))
		{
			estimates.estimates();
			estimates.clickTotalEstimates();
			estimates.selectTotalEstimateOptions();
			
			Assert.assertEquals(estimates.getEstimatesTotals(), estimates.getActualTotal(),"Actual and Calculated Totals are not Same");
			estimates.saveTotalEstimates();
		}
		else
		{
			estimates.clickEstimateLiteSave();
			estimates.clickEstimateLiteClose();
		}
	}

}
