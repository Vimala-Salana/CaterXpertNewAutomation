package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private final ObjectMapper objectMapper;

    public JsonUtil() {
        objectMapper = new ObjectMapper();
    }

    public Map<String, String> getData(String filePath) {

        try {
            return objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<Map<String, String>>() {}
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read JSON test data: " + filePath, e);
        }
    }

    public String getValue(String filePath, String key) {
        return getData(filePath).get(key);
    }
}