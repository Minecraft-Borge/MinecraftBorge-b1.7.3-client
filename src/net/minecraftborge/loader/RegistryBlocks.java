package net.minecraftborge.loader;

import net.minecraft.src.Block;

import java.util.*;

public class RegistryBlocks implements IRegistry<Block> {
	RegistryBlocks() {
		Arrays.fill(this.nameLookup, "air");
	}

	private boolean frozen = false;
	private final Map<String, Block> nameToValueMap = new HashMap<>(Block.blocksList.length);
	private final String[] nameLookup = new String[Block.blocksList.length];

	@Override
	public Block getValue(String key) {
		return this.nameToValueMap.get(key);
	}
	@Override
	public String getKey(Block value) {
		return this.nameLookup[value.blockID];
	}

	@Override
	public Collection<String> getKeys() {
		return this.nameToValueMap.keySet();
	}
	@Override
	public Collection<Block> getValues() {
		return this.nameToValueMap.values();
	}

	@Override
	public void register(String key, Block value) {
		if (this.frozen) throw new IllegalStateException("Frozen registry");
		if (key == null) throw new IllegalArgumentException("Registry key is null!");
		if (this.nameToValueMap.containsKey(key)) throw new IllegalArgumentException("Duplicate registry key: " + key);
		if (!Namespace.validate(key)) throw new IllegalArgumentException("Invalid registry key: " + key);
		this.nameToValueMap.put(key, value);
		this.nameLookup[value.blockID] = key;
	}
	public void register(Block block) {
		this.register(block.getRegistryName(), block);
	}

	@Override
	public void registerBase() {
		if (this.frozen) throw new IllegalStateException("Frozen registry");
		int counter = 0;
		for (int i = 0; i < Block.blocksList.length; i++) {
			Block block = Block.blocksList[i];
			if (block != null) {
				this.register(block);
				counter++;
			}
		}
		this.frozen = true;
		System.out.println("Registered " + counter + " blocks!");
	}
}
