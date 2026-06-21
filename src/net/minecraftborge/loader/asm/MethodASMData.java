package net.minecraftborge.loader.asm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MethodASMData {
	public final String name;
	public final String owner;
	public final String descriptor;
	public final String[] throwables;
	public final Map<String, Map<String, Object>> annotations;

	public MethodASMData(String name, String owner, String descriptor, String[] throwables) {
		this.name = name;
		this.owner = owner;
		this.descriptor = descriptor;
		this.throwables = throwables;
		this.annotations = new HashMap<>();
	}

	@Override
	public String toString() {
		return this.owner + "#" + this.name + this.descriptor
				+ (this.throwables != null && this.throwables.length > 0 ? " throws " + Arrays.toString(this.throwables) : "");
	}
}
