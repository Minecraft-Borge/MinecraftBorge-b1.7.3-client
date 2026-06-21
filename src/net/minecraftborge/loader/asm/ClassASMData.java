package net.minecraftborge.loader.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassASMData {
	public final String className;
	public final Map<String, Map<String, Object>> annotations;
	public final Map<String, List<MethodASMData>> annotatedMethods;
	public final List<MethodASMData> methods;

	public ClassASMData(String className) {
		this.className = className;
		this.annotations = new HashMap<>();
		this.annotatedMethods = new HashMap<>();
		this.methods = new ArrayList<>();
	}

	@Override
	public String toString() {
		return this.className + "[" + this.annotations.size() + " annotations, " + this.methods.size() + " methods]";
	}
}
