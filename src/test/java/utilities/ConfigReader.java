package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	Properties properties = new Properties();

	public String getProperty(String key) {

	    try {
	        InputStream is = getClass()
	                .getClassLoader()
	                .getResourceAsStream("Config.properties");

	        if (is == null) {
	            throw new RuntimeException("Config.properties file not found");
	        }

	        properties.load(is);

	    } catch (IOException e) {
	        throw new RuntimeException("Unable to read Config.properties file");
	    }

	    return properties.getProperty(key);
	}
}


