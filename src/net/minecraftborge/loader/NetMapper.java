package net.minecraftborge.loader;

import net.minecraftborge.loader.net.Packet141ModLogin;

/**
 * Remaps int values from incoming and outgoing packets to make sure the registries are synchronized
 */
public class NetMapper {
	public static final NetMapper IDENTITY = new NetMapper();

	public final RegistryRemapperBlocks blocks;
	public final RegistryRemapperItems items;
	public final RegistryRemapperEntities entities;
	public final RegistryRemapperMods mods;

	public NetMapper(Packet141ModLogin source) {
		this.blocks = new RegistryRemapperBlocks(GameRegistries.BLOCKS, source.remoteBlocks);
		this.items = new RegistryRemapperItems(GameRegistries.ITEMS, source.remoteItems);
		this.entities = new RegistryRemapperEntities(GameRegistries.ENTITIES, source.remoteEntities);
		this.mods = new RegistryRemapperMods(ModList.get(), source.remoteMods);
	}
	private NetMapper() {
		this.blocks = RegistryRemapperBlocks.IDENTITY;
		this.items = RegistryRemapperItems.IDENTITY;
		this.entities = RegistryRemapperEntities.IDENTITY;
		this.mods = RegistryRemapperMods.IDENTITY;
	}
}
