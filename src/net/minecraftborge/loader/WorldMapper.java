package net.minecraftborge.loader;

import net.minecraft.src.NBTTagCompound;

public class WorldMapper {
	public static final WorldMapper IDENTITY = new WorldMapper();

	public final RegistryRemapperBlocks blocks;
	public final RegistryRemapperItems items;

	public WorldMapper(NBTTagCompound source) {
		this.blocks = new RegistryRemapperBlocks(GameRegistries.BLOCKS, NBTUtil.convertToIntMap(source.getCompoundTag("Blocks")));
		this.items = new RegistryRemapperItems(GameRegistries.ITEMS, NBTUtil.convertToIntMap(source.getCompoundTag("Items")));
	}
	private WorldMapper() {
		this.blocks = RegistryRemapperBlocks.IDENTITY;
		this.items = RegistryRemapperItems.IDENTITY;
	}
	private WorldMapper(WorldRegistryMap source) {
		this.blocks = new RegistryRemapperBlocks(GameRegistries.BLOCKS, source.blocks);
		this.items = new RegistryRemapperItems(GameRegistries.ITEMS, source.items);
	}

	@Override
	public String toString() {
		return this == IDENTITY ? "WorldMapperIdentity" : "WorldMapper";
	}

	public static WorldMapper createFromRegistryMap(WorldRegistryMap registryMap) {
		try {
			return registryMap != null ? new WorldMapper(registryMap) : IDENTITY;
		} catch (Exception e) {
			System.err.println("Registry map error: " + e);
			return IDENTITY;
		}
	}
}
