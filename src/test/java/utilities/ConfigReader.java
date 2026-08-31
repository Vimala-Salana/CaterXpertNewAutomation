package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class ConfigReader {

	private final Properties properties = new Properties();
	String env;

	public ConfigReader() {

		try (InputStream is = getClass().getClassLoader().getResourceAsStream("Config.properties")) {

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

	public String getEnvironment() {
		return System.getProperty("env", getProperty("default.env"));
	}

	public String getUrl() {
		String env = getEnvironment();
		return System.getProperty("url", getProperty(env + ".url"));
	}

	public String getCaterId() {
		env = getEnvironment();
		return System.getProperty("caterid", getProperty(env + ".caterid"));
	}

	public String getUserId() {
		env = getEnvironment();
		return System.getProperty("userid", getProperty(env + ".userid"));
	}

	public String getPassword() {
		env = getEnvironment();
		return System.getProperty("password", getProperty(env + ".password"));
	}

	public Duration getDuration(String key) {
		return Duration.ofSeconds(Long.parseLong(properties.getProperty(key)));
	}
}