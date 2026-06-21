package net.minecraftborge.loader;

public final class Namespace {
	private Namespace() {}

	public static boolean validate(String namespace) {
		if (namespace.isEmpty()) return false;
		for (char c : namespace.toCharArray()) {
			if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c == '/' || c >= '0' && c <= '9') continue;
			return false;
		}
		return true;
	}

	public static String extractModId(String namespace) {
		String[] split = namespace.split("/");
		return split.length == 1 ? null : split[0];
	}
}
