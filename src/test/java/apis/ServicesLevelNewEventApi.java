package apis;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import utilities.ConfigReader;

public class ServicesLevelNewEventApi {

	public ConfigReader config = new ConfigReader();

	public String newServiceEventId(String loginId, List<String> service) {

		String url = config.getUrl();
		String caterId = config.getCaterId();
		
		Response response = given()
				.pathParam("patch", "CaterXpertSales2026_0704")
				.pathParam("module", "sales")
				.pathParam("screen", "getSalesEventsList")
				.queryParams("loginId", loginId ,"catererId", caterId,"lowerBound", 1,
						"upperBound", 200,"deptId", 2)
				.when()
				.get(url + "/{patch}/{module}/{screen}");

		response.then().statusCode(200);

		List<Map<String, Object>> events = response.jsonPath().getList("$");

		String cisNumber = null;
		for (Map<String, Object> event : events) {

			// Get service names
			List<?> serviceNames = (List<?>) event.get("serviceColumnNames");

			// Get service statuses
			List<?> serviceStatuses = (List<?>) event.get("serviceStatusValues");

			cisNumber = (String) event.get("cisnumber");

			for (int i = 0; i < serviceNames.size(); i++) {

				String serviceName = serviceNames.get(i).toString().trim();
				String status = serviceStatuses.get(i).toString().trim();

				// Find requested service with New status
				if (service.stream().anyMatch(s -> s.trim().equalsIgnoreCase(serviceName.trim()))
						&& "New".equalsIgnoreCase(status)
						&& !cisNumber.trim().matches(".*\\s+[MT]\\d*$"))
				{
					System.out.println(cisNumber);
					return cisNumber;
				}
			}

		}
		return null;
	}
}
