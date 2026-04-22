package utilities;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.UUID;

import org.testng.annotations.Test;

import net.datafaker.Faker;
public class DataGenerator {

	private DataGenerator() {
		super();
	}

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom random = new SecureRandom();

	private static final ThreadLocal<Faker> faker = ThreadLocal.withInitial(() -> new Faker(new Locale("en-US")));

	public static Faker getFaker() {
		return faker.get();
	}

	public static void setFaker(Faker newFaker) {
		faker.set(newFaker);
	}

	public static void setLocale(String localeName) {
		faker.set(new Faker(new Locale(localeName)));
	}

	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(AB.charAt(random.nextInt(AB.length())));
		}
		return sb.toString();
	}

	public static String randomStringHexToken(int byteLength) {
		byte[] token = new byte[byteLength];
		random.nextBytes(token);
		return new BigInteger(1, token).toString(16);
	}

	public static String randomStringUUID() {
		return UUID.randomUUID().toString();
	}

	public static int randomNumberIntFromTo(int from, int to) {
		return (int) Math.floor(Math.random() * (to - from + 1) + from);
	}

	public static String randomFirstName() {
		return getFaker().name().firstName();
	}

	public static String randomLastName() {
		return getFaker().name().lastName();
	}

	public static String randomPhoneNumber() {
		return getFaker().phoneNumber().phoneNumber();
	}

	public static String randomCountry() {
		return getFaker().address().country();
	}

	public static String randomZipCode() {
		return getFaker().address().zipCode();
	}

	public static String randomAddress() {
		return getFaker().address().fullAddress();
	}

	public static String randomCity() {
		return getFaker().address().cityName();
	}

	public static String randomState() {
		return getFaker().address().state();
	}

	public static String randomUSCounty() {
		return getFaker().address().stateAbbr();    // e.g., "CA"
	}

	public static String randomStreetName() {
		return getFaker().address().streetName();
	}

	@Test
	public void testDataGeneration() {
		System.out.println("First Name: " + randomFirstName());
		System.out.println("Last Name: " + randomLastName());
		System.out.println("Phone Number: " + randomPhoneNumber());
		System.out.println("Address: " + randomAddress());
		//  System.out.println("Country: " + randomCountry());
		System.out.println("Zip Code: " + randomZipCode());
		System.out.println("State: " + randomState());
		System.out.println("County: " + randomUSCounty());
		System.out.println("City: " + randomCity());
		System.out.println("Street: " + randomStreetName());
	}
}


