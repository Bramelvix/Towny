package util;

public final class StringUtils {
	private StringUtils() {
	}

	public static String capitalise(String name) {
		if (name == null || name.isEmpty()) {
			return name;
		}
		char[] chars = name.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

}
