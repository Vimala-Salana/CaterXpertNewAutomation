package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	Properties properties = new Properties();

	public String getProperty(String key)
	{
		try (FileInputStream fis = new FileInputStream("src/test/resources/Config.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			System.out.println("Error loading configuration file: " + e.getMessage());
		}

		return properties.getProperty(key);
	}
}


