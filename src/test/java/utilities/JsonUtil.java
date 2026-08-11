package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private final Map<String, String> data;

	public JsonUtil(String filePath) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			data = objectMapper.readValue(
					new File(filePath),
					new TypeReference<Map<String, String>>() {});
		}
		catch (IOException e) {

			throw new RuntimeException("Unable to read JSON test data: " + filePath,e);
		}
	}

	public String getValue(String key) {

		return data.get(key);
	}

	public Map<String, String> getData() {

		return data;
	}
}