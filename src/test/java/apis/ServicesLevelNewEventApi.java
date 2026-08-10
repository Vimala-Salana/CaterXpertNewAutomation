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
	public String newServiceEventId(List<String> service) {

		Response response = given()
				.pathParam("patch", "CaterXpertSales2026_0704")
				.pathParam("module", "sales")
				.pathParam("screen", "getSalesEventsList")
				.queryParams("loginId", -1,"catererId", "tpgchitest","lowerBound", 1,
						"upperBound", 200,"deptId", 2)
				.when()
				.get(config.getProperty("test.url") + "/{patch}/{module}/{screen}");

		response.then().statusCode(200);

		List<Map<String, Object>> events = response.jsonPath().getList("$");

		String cisNumber = null;
		for (Map<String, Object> event : events) {

			// Get service names
			List<String> serviceNames = (List<String>) event.get("serviceColumnNames");

			// Get service statuses
			List<String> serviceStatuses = (List<String>) event.get("serviceStatusValues");

			cisNumber = (String) event.get("cisnumber");

			for (int i = 0; i < serviceNames.size(); i++) {

				String serviceName = serviceNames.get(i).trim();
				String status = serviceStatuses.get(i).trim();

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
