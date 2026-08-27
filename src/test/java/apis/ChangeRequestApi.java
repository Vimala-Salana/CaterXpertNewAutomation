package apis;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;
import utilities.ConfigReader;

public class ChangeRequestApi {

	public ConfigReader config = new ConfigReader();

	public String changeRequestEvent(String loginId, List<String> service, List<String> statuses) {

		String url = config.getUrl();
		String caterId = config.getCaterId();

		Response response = given().pathParam("patch", "CaterXpertSales2026_0802").pathParam("module", "sales")
				.pathParam("screen", "getSalesEventsList")
				.queryParams("loginId", loginId, "catererId", caterId, "lowerBound", 1, "upperBound", 100, "deptId", 2)
				.when().get(url + "/{patch}/{module}/{screen}");

		response.then().statusCode(200);

		List<Map<String, Object>> events = response.jsonPath().getList("$");

		String cisNumber = null;

		List<String> matchingEvents = new ArrayList<>();

		for (Map<String, Object> event : events) {

			// Get service names
			List<?> serviceNames = (List<?>) event.get("serviceColumnNames");

			// Get service statuses
			List<?> serviceStatuses = (List<?>) event.get("serviceStatusValues");

			cisNumber = (String) event.get("cisnumber");

			if (serviceStatuses == null || serviceNames == null || cisNumber == null)
				continue;

			for (int i = 0; i < serviceNames.size(); i++) {

				String serviceName = serviceNames.get(i).toString().trim();
				String status = serviceStatuses.get(i).toString().trim();

				// Find requested service with New status
				if (service.stream().anyMatch(s -> s.trim().equalsIgnoreCase(serviceName.trim()))
						&& statuses.contains(status.trim()) && !cisNumber.trim().matches(".*\\s+[MT]\\d*$")) {
					matchingEvents.add(cisNumber);
				}
			}

		}
		Collections.shuffle(matchingEvents);
		return matchingEvents.isEmpty() ? null : matchingEvents.get(0);
	}

}
