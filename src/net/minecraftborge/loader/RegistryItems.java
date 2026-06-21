package net.minecraftborge.loader;

import net.minecraft.src.Item;

import java.util.*;

public class RegistryItems implements IRegistry<Item> {
	RegistryItems() {
		Arrays.fill(this.nameLookup, "air");
	}

	private boolean frozen = false;
	private final Map<String, Item> nameToValueMap = new HashMap<>(Item.itemsList.length);
	private final String[] nameLookup = new String[Item.itemsList.length];

	@Override
	public Item getValue(String key) {
		return this.nameToValueMap.get(key);
	}
	@Override
	public String getKey(Item value) {
		return this.nameLookup[value.shiftedIndex];
	}

	@Override
	public Collection<String> getKeys() {
		return this.nameToValueMap.keySet();
	}
	@Override
	public Collection<Item> getValues() {
		return this.nameToValueMap.values();
	}

	@Override
	public void register(String key, Item value) {
		if (this.frozen) throw new IllegalStateException("Frozen registry");
		if (key == null) throw new IllegalArgumentException("Registry key is null!");
		if (this.nameToValueMap.containsKey(key)) throw new IllegalArgumentException("Duplicate registry key: " + key);
		if (!Namespace.validate(key)) throw new IllegalArgumentException("Invalid registry key: " + key);
		this.nameToValueMap.put(key, value);
		this.nameLookup[value.shiftedIndex] = key;
	}
	public void register(Item item) {
		this.register(item.getRegistryName(), item);
	}

	@Override
	public void registerBase() {
		if (this.frozen) throw new IllegalStateException("Frozen registry");
		int counter = 0;
		for (int i = 0; i < Item.itemsList.length; i++) {
			Item item = Item.itemsList[i];
			if (item != null) {
				this.register(item);
				counter++;
			}
		}
		this.frozen = true;
		System.out.println("Registered " + counter + " items!");
	}
}
