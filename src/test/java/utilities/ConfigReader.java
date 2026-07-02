package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties = new Properties();

    public ConfigReader() {

        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("Config.properties")) {

            if (is == null) {
                throw new RuntimeException("Config.properties file not found");
            }

            properties.load(is);

        } catch (IOException e) {
            throw new RuntimeException("Unable to read Config.properties file", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}