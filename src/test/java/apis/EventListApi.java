package apis;

import static io.restassured.RestAssured.*;

import java.io.ObjectInputFilter.Config;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import utilities.ConfigReader;
public class EventListApi {
	public ConfigReader config = new ConfigReader();
	@Test
	public String getEventId()
	{
		//https://catapps1.aquilasoftware.com/CaterXpertSales2026_0704/sales/
		//getSalesEventsList?loginId=-1&catererId=caterxpertcat&lowerBound=1&upperBound=20&deptId=2
		long start = System.currentTimeMillis();
		 Response response = given()
		.pathParam("patch","CaterXpertSales2026_0704")
		.pathParam("module","sales")
		.pathParam("screen","getSalesEventsList")
		.queryParams("loginId",-1,"catererId","caterxpertcat","lowerBound",1,"upperBound",20,"deptId",2)
		.when()
			.get("https://catapps1.aquilasoftware.com"+"/{patch}/{module}/{screen}");
		 long apiTime = System.currentTimeMillis(); 
		String eventId  = response.jsonPath().getString
					("find { it.serviceStatusValues.every { status -> status.trim() == 'New' } "
							+ "&& !(it.cisnumber.trim() ==~ /.*\\s+[MT]\\d*$/) }.cisnumber");
		eventId = eventId.split("\\s")[0];
		
		System.out.println(eventId);
		
		long jsonTime = System.currentTimeMillis();

		System.out.println("API time     : " + (apiTime - start) + " ms");
		System.out.println("JsonPath time: " + (jsonTime - apiTime) + " ms");
		return eventId;
	}
}
