package au.com.mason.expensemanager.util;

public class EnvUtil {

	public static String getEnv(String propertyName) {
		return System.getenv().get(propertyName);
	}
}
