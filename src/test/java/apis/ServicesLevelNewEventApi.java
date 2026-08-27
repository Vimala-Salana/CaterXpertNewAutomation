package apis;

import static io.restassured.RestAssured.given;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.restassured.response.Response;
import utilities.ConfigReader;

public class ServicesLevelNewEventApi {

	public ConfigReader config = new ConfigReader();

	public String newServiceEventId(String loginId, List<String> service) {

		String url = config.getUrl();
		String caterId = config.getCaterId();

		LocalDate startDate = LocalDate.now();
		LocalDate endDate = startDate.plusDays(30);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		String startDateParam = startDate.format(formatter);
		String endDateParam = endDate.format(formatter);

		Response response = given().pathParam("patch", "CaterXpertSales2026_0802").pathParam("module", "sales")
				.pathParam("screen", "getSalesEventsList")
				.queryParams("loginId", loginId, "catererId", caterId, "startDate", startDateParam, "endDate",
						endDateParam, "lowerBound", 1, "upperBound", 50, "deptId", 2)
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
			int size = Math.min(serviceNames.size(), serviceStatuses.size());

			for (int i = 0; i < size; i++) {

				String serviceName = serviceNames.get(i).toString().trim();
				String status = serviceStatuses.get(i).toString().trim();

				// Find requested service with New status
				if (service.stream().anyMatch(s -> s.trim().equalsIgnoreCase(serviceName.trim()))
						&& "New".equalsIgnoreCase(status) && !cisNumber.trim().matches(".*\\s+[MT]\\d*$")) {
					matchingEvents.add(cisNumber);
				}
			}

		}
		Collections.shuffle(matchingEvents);
		return matchingEvents.isEmpty() ? null : matchingEvents.get(0);
	}
}
