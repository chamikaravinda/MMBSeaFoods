package util;

public class LicenseKeyFormatting {

	public String licenseKeyFormattingMy(String input, int k) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = input.length() - 1; i >= 0; i--)
			if (input.charAt(i) != '-') {
				if (stringBuilder.length() % (k + 1) == k) {
					stringBuilder.append('-');
				}
				stringBuilder.append(Character.toUpperCase(input.charAt(i)));
			}
		return stringBuilder.reverse().toString();
	}

}
