package net.minecraftborge.loader;

public class ModloadingException extends Exception {
	public ModloadingException() {
		super();
	}

	public ModloadingException(String message) {
		super(message);
	}

	public ModloadingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModloadingException(Throwable cause) {
		super(cause);
	}
}
