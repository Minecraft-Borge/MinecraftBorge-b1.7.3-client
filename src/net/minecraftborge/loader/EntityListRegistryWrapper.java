package net.minecraftborge.loader;

import net.minecraft.src.Entity;

import java.util.Map;

public class EntityListRegistryWrapper {
	public final Map<String, Class<? extends Entity>> nameToClassMap;
	public final Map<Class<? extends Entity>, String> classToNameMap;
	public final Map<Integer, Class<? extends Entity>> IDtoClassMap;
	public final Map<Class<? extends Entity>, Integer> classToIDMap;
	public final Registrator registrator;

	public EntityListRegistryWrapper(
			Map<String, Class<? extends Entity>> nameToClassMap,
			Map<Class<? extends Entity>, String> classToNameMap,
			Map<Integer, Class<? extends Entity>> IDtoClassMap,
			Map<Class<? extends Entity>, Integer> classToIDMap,
			Registrator registrator
	) {
		this.nameToClassMap = nameToClassMap;
		this.classToNameMap = classToNameMap;
		this.IDtoClassMap = IDtoClassMap;
		this.classToIDMap = classToIDMap;
		this.registrator = registrator;
	}

	@FunctionalInterface
	public interface Registrator {
		void register(Class<? extends Entity> clazz, String name, int id);
	}
}
