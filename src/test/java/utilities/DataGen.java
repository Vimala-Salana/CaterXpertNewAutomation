package utilities;
import java.security.SecureRandom;
import java.util.Locale;

/** Utility class for generating random and fake test data. **/

import org.testng.annotations.Test;

import net.datafaker.Faker;
public class DataGen {

	

	private static final String ALPHA_NUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Locale DEFAULT_LOCALE = Locale.US;
	
	 /**
     * Each thread gets its own Faker instance.
     * This is useful when TestNG runs tests in parallel.
     */

	private static final ThreadLocal<Faker> FAKER =   ThreadLocal.withInitial(() -> new Faker(DEFAULT_LOCALE));
	
	//Private constructor prevents object creation.
	private DataGen() {
     
	}
	
	// Returns the Faker instance associated with the current thread.
	public static Faker getFaker() {
		return FAKER.get();
	}

	// Replaces the Faker instance for the current thread.
	public static void setFaker(Faker newFaker) {
		FAKER.set(newFaker);
	}

	// Sets the locale for the current thread's Faker instance.
	public static void setLocale(String localeName) {
		FAKER.set(new Faker(new Locale(localeName)));
	}

	//Generates a random alphanumeric string.
	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(ALPHA_NUMERIC .charAt(secureRandom.nextInt(ALPHA_NUMERIC.length())));
		}
		return sb.toString();
	}

	//Generates a random HexaDecimal Token.
	public static String randomStringHexToken(int byteLength) {
		byte[] bytes = new byte[byteLength];
		secureRandom.nextBytes(bytes);
		StringBuilder result = new StringBuilder(byteLength * 2);
		  for (byte value : bytes) {
	            result.append(String.format("%02x", value & 0xFF));
	        }
	        return result.toString();
	}

	//Generates a random integer between from and to, inclusive.
	public static int randomNumberIntFromTo(int from, int to) {
		return (int) secureRandom.nextInt(to - from + 1) + from;
	}

	public static String randomFirstName() {
		return getFaker().name().firstName();
	}

	public static String randomLastName() {
		return getFaker().name().lastName();
	}
	
	public static String randomEmail() {
		 return getFaker().internet().emailAddress();
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
		System.out.println("Random Number: " + randomNumberIntFromTo(1,10));
		System.out.println("Random String: " + randomString(5));

	}
}


