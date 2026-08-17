package apis;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;
import utilities.ConfigReader;

public class EventListApi {

	public ConfigReader config = new ConfigReader();

	public String getAllNewServicesEventId(String loginId) {
		String url = config.getUrl();
		String caterId = config.getCaterId();

		// https://catapps1.aquilasoftware.com/CaterXpertSales2026_0704/sales/
		// getSalesEventsList?loginId=-1&catererId=caterxpertcat&lowerBound=1&upperBound=20&deptId=2
		Response response = given().pathParam("patch", "CaterXpertSales2026_0802").pathParam("module", "sales")
				.pathParam("screen", "getSalesEventsList")
				.queryParams("loginId", loginId, "catererId", caterId, "lowerBound", 1, "upperBound", 200, "deptId", 2)
				.when().get(url + "/{patch}/{module}/{screen}");

		List<Map<String, Object>> events = response.jsonPath().getList("$");

		String cisNumber = null;

		List<String> matchingEvents = new ArrayList<>();

		for (Map<String, Object> event : events) {

			// Get service statuses
			List<?> serviceStatuses = (List<?>) event.get("serviceStatusValues");

			cisNumber = (String) event.get("cisnumber");

			if (serviceStatuses == null || cisNumber == null)
				continue;

			// Find requested service with New status
			if (serviceStatuses.stream().allMatch(status -> status.toString().trim().equalsIgnoreCase("New"))
					&& !cisNumber.trim().matches(".*\\s+[MT]\\d*$")) {
				System.out.println(cisNumber);
				matchingEvents.add(cisNumber);
			}

		}
		Collections.shuffle(matchingEvents);
		return matchingEvents.isEmpty() ? null : matchingEvents.get(0);
	}
}
