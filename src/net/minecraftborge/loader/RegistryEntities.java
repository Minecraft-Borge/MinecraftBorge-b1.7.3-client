package net.minecraftborge.loader;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;

import java.util.Collection;

public class RegistryEntities implements IRegistry<Class<? extends Entity>> {
	RegistryEntities() {

	}

	public final EntityListRegistryWrapper wrapper = EntityList.REGISTRY_WRAPPER;

	@Override
	public Class<? extends Entity> getValue(String key) {
		return this.wrapper.nameToClassMap.get(key);
	}
	@Override
	public String getKey(Class<? extends Entity> value) {
		return this.wrapper.classToNameMap.get(value);
	}

	@Override
	public Collection<String> getKeys() {
		return this.wrapper.nameToClassMap.keySet();
	}
	@Override
	public Collection<Class<? extends Entity>> getValues() {
		return this.wrapper.nameToClassMap.values();
	}

	@Override
	public void register(String key, Class<? extends Entity> value) {
		for (int i = 1; i < EntityList.MAX_ENTITY_ID; i++) {
			if (this.wrapper.IDtoClassMap.containsKey(i)) continue;

			this.wrapper.registrator.register(value, key, i);
			return;
		}
		throw new IllegalStateException("Entity ID limit reached!");
	}
}
