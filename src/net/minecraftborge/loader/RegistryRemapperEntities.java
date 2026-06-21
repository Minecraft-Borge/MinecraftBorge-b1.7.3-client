package net.minecraftborge.loader;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;

import java.util.Locale;
import java.util.Map;

public class RegistryRemapperEntities implements RegistryMapper {
	public static final RegistryRemapperEntities IDENTITY = new RegistryRemapperEntities(new int[EntityList.MAX_ENTITY_ID], new int[EntityList.MAX_ENTITY_ID]);

	private final int[] remapper;
	private final int[] demapper;

	public RegistryRemapperEntities(int[] remapper, int[] demapper) {
		this.remapper = remapper;
		this.demapper = demapper;

		if (this.remapper.length != this.demapper.length && this.remapper.length != EntityList.MAX_ENTITY_ID) throw new IllegalArgumentException();
	}

	public RegistryRemapperEntities(RegistryEntities registry, Map<String, Integer> remote) {
		this(new int[EntityList.MAX_ENTITY_ID], new int[EntityList.MAX_ENTITY_ID]);

		for (int i = 0; i < EntityList.MAX_ENTITY_ID; i++) {
			Class<? extends Entity> clazz = registry.wrapper.IDtoClassMap.get(i);
			if (clazz != null) {
				String name = registry.getKey(clazz);
				Integer remoteID = remote.get(name);
				if (remoteID == null) throw new IllegalStateException("Entity missing on server: " + name);
				this.remapper[i] = remoteID;
				this.demapper[remoteID] = i;
			}
		}
	}

	@Override
	public int remap(int id) {
		return this.remapper[id];
	}
	@Override
	public int demap(int id) {
		return this.demapper[id];
	}

	static {
		for (int i = 0; i < EntityList.MAX_ENTITY_ID; i++) {
			IDENTITY.remapper[i] = i;
			IDENTITY.demapper[i] = i;
		}
	}
}
