package apis;

import static io.restassured.RestAssured.*;

import java.io.ObjectInputFilter.Config;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import factory.DriverFactory;
import io.restassured.response.Response;
import pageObjects.BasetoSalesNavigationPage;
import utilities.ConfigReader;
public class EventListApi {
	
	public ConfigReader config = new ConfigReader();
	
	public String getAllNewServicesEventId(String loginId)
	{
		String url = config.getUrl();
		String caterId = config.getCaterId();
		
		//https://catapps1.aquilasoftware.com/CaterXpertSales2026_0704/sales/
		//getSalesEventsList?loginId=-1&catererId=caterxpertcat&lowerBound=1&upperBound=20&deptId=2
		Response response = given()
				.pathParam("patch","CaterXpertSales2026_0704")
				.pathParam("module","sales")
				.pathParam("screen","getSalesEventsList")
				.queryParams("loginId",loginId,"catererId",caterId,"lowerBound",1,"upperBound",200,"deptId",2)
				.when()
				.get(url+"/{patch}/{module}/{screen}");

		List<Map<String, Object>> events = response.jsonPath().getList("$");

		String cisNumber = null;

		for (Map<String, Object> event : events) {

			// Get service statuses
			List<?> serviceStatuses = (List<?>) event.get("serviceStatusValues");

			cisNumber = (String) event.get("cisnumber");

			// Find requested service with New status
			if (serviceStatuses.stream().anyMatch(status->status.toString().trim().equalsIgnoreCase("New")) 
					&& !cisNumber.trim().matches(".*\\s+[MT]\\d*$"))
			{
				System.out.println(cisNumber); 	 	
				return cisNumber;
			}

		}
		return null;
	}
}

