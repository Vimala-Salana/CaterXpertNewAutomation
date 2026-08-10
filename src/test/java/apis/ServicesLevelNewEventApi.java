package apis;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import utilities.ConfigReader;

public class ServicesLevelNewEventApi {
	public ConfigReader config = new ConfigReader();
	
	@Test
	public String menuNewEventId()
	{

		//https://catapps1.aquilasoftware.com/CaterXpertSales2026_0704/sales/
		//getSalesEventsList?loginId=-1&catererId=caterxpertcat&lowerBound=1&upperBound=20&deptId=2
		long start = System.currentTimeMillis();
		 Response response = given()
		.pathParam("patch","CaterXpertSales2026_0704")
		.pathParam("module","sales")
		.pathParam("screen","getSalesEventsList")
		.queryParams("loginId",-1,"catererId","tpgchitest","lowerBound",1,"upperBound",200,"deptId",2)
		.when()
			.get(config.getProperty("test.url")+"/{patch}/{module}/{screen}");
		 long apiTime = System.currentTimeMillis(); 
		 
		 
		 String cisNumber = null;

		 List<Map<String, Object>> events =
		         response.jsonPath().getList("$");

		 for (Map<String, Object> event : events) {

		     String cis = (String) event.get("cisnumber");

		     if (cis == null || cis.matches(".*\\s+[MT]\\d*$")) {
		         continue;
		     }

		     List<?> statuses =
		             (List<?>) event.get("serviceStatusValues");

		     if (statuses != null &&
		         statuses.stream()
		                 .allMatch(s -> s != null &&
		                         s.toString().trim().equals("New"))) {

		         cisNumber = cis;
		         break;
		     }
		 }
		 
		System.out.println(cisNumber);
		
		long jsonTime = System.currentTimeMillis();

		System.out.println("API time     : " + (apiTime - start) + " ms");
		System.out.println("JsonPath time: " + (jsonTime - apiTime) + " ms");
		return cisNumber;
	
	}

}
