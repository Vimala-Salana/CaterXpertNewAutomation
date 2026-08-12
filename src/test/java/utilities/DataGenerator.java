package utilities;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.datafaker.Faker;

public class DataGenerator {

	private final Faker faker;
	private final ObjectMapper objectMapper;

	public DataGenerator() {
		faker = new Faker();
		objectMapper = new ObjectMapper();
	}

	/**
	 * Generates random values based on the field data types provided in the screen JSON file.
	 */
	public Map<String, String> generate(Map<String, String> fieldDataTypes, String testName) {

		Map<String, String> generatedFieldData = new LinkedHashMap<>();

		for (Map.Entry<String, String> entry : fieldDataTypes.entrySet()) {

			String fieldLabel = entry.getKey();
			String fieldDataType = entry.getValue();

			String generatedValue = generateValue(fieldDataType);

			generatedFieldData.put(fieldLabel, generatedValue);
		}

		// Save the exact values used during execution
		saveGeneratedData(testName, generatedFieldData);

		return generatedFieldData;
	}

	/**
	 * Generates a value based on the field data type.
	 */
	private String generateValue(String fieldValue) {
		
		 if (!fieldValue.startsWith("@")) {
		        return fieldValue;
		    }

		    String dataType = fieldValue.substring(1); //Removes @ from the fieldValue ex: @firstName becomes firstName

		switch (dataType) {

		case "firstName":
			return faker.name().firstName();

		case "lastName":
			return faker.name().lastName();

		case "fullName":
			return faker.name().fullName();
			
		case "title":
		    return faker.name().title();

		case "email":
			return faker.internet().emailAddress();

		case "phone":
			return faker.phoneNumber().cellPhone();

		case "company":
			return faker.company().name();

		case "fullAddress":
			return faker.address().fullAddress();
			
		case "buildingName":
		    return faker.address().streetName() + " " +
		           faker.options().option("Apartments", "Towers", "Plaza", "Heights", "Court");
		    
		case "street":
			return faker.address().streetName();
			
		case "suite":
			return "Suite " + faker.number().numberBetween(100, 999);

		case "city":
			return faker.address().city();
			
		case "zip+4":
			return faker.number().digits(4);

		case "state":
			return faker.address().state();

		case "country":
			return faker.address().country();

		case "zipCode":
			return faker.address().zipCode();
		
		case "twoDigitNumber":
			return faker.number().digits(2);
			
		case "oneDigitNumber":
			return faker.number().digit();
			
		case "accountingId":
			return faker.name().firstName() + faker.number().digits(4);

		case "sentence":
			return faker.lorem().sentence();

		case "eventName":
			return faker.lorem().sentence(3);

		/*
		 * MandatoryLabelsUtil expects a date offset.
		 * Example: "15" means today + 15 days.
		 */
		case "futureDate":
			return String.valueOf(faker.number().numberBetween(1, 30));
			
		case "pastDate":
		    return String.valueOf(-faker.number().numberBetween(1, 30));

		default:
			throw new IllegalArgumentException("Unsupported field Value: " + fieldValue);
		}
	}

	/**
	 * Saves the generated field values so we can see exactly what data was used after execution.
	 */
	private void saveGeneratedData(String testName, Map<String, String> generatedFieldData) {

		try {

			File outputDirectory = new File("test-output/execution-data");

			if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
				throw new IOException("Unable to create output directory: " + outputDirectory.getAbsolutePath());
			}

			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
			String fileName = testName + "_" + timestamp + ".json";
			File outputFile = new File(outputDirectory, fileName);

			objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, generatedFieldData);

			System.out.println("Generated test data saved to: " + outputFile.getAbsolutePath());

		} catch (IOException e) {
			throw new RuntimeException("Unable to save generated test data for: " + testName, e);
		}
	}
}