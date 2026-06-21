package net.minecraftborge.loader.asm;

import java.util.*;

public class ASMDataTable {
	private final Map<String, List<ClassASMData>> table = new HashMap<>();
	private final Map<String, ClassASMData> classData = new HashMap<>();

	public void addClassData(ClassASMData data) {
		this.classData.put(data.className, data);
	}
	public ClassASMData getClassData(String name) {
		return this.classData.get(name);
	}

	public void add(String annotation, ClassASMData data) {
		this.table.computeIfAbsent(annotation, k -> new ArrayList<>()).add(data);
	}
	public List<ClassASMData> getAnnotated(String annotation) {
		return this.table.getOrDefault(annotation, Collections.emptyList());
	}

	public void printDebug() {
		System.out.println("====ASM TABLE====");
		System.out.println();

		for (ClassASMData classASMData : this.classData.values()) {
			System.out.println(" " + classASMData.className);
			if (!classASMData.annotations.isEmpty()) {
				System.out.println("  Annotations:");
				for (Map.Entry<String, Map<String, Object>> annotation : classASMData.annotations.entrySet()) {
					System.out.println("   " + annotation.getKey());
					for (Map.Entry<String, Object> prop : annotation.getValue().entrySet()) {
						System.out.println("    " + prop.getKey() + " : " + prop.getValue());
					}
				}
			}
			if (!classASMData.methods.isEmpty()) {
				System.out.println("  Methods:");
				for (MethodASMData methodASMData : classASMData.methods) {
					System.out.println("   " + methodASMData.name + " - " + methodASMData.descriptor);
					for (Map.Entry<String, Map<String, Object>> annotation : methodASMData.annotations.entrySet()) {
						System.out.println("    " + annotation.getKey());
						for (Map.Entry<String, Object> prop : annotation.getValue().entrySet()) {
							System.out.println("     " + prop.getKey() + " : " + prop.getValue());
						}
					}
				}
			}
		}

		System.out.println();
	}
}
