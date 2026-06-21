package net.minecraftborge.loader;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;

import java.util.*;

public final class WorldRegistryMap {
	public final List<String> worldModList;
	public final Map<String, Integer> blocks;
	public final Map<String, Integer> items;

	public WorldRegistryMap(NBTTagCompound nbt) {
		List<String> mods = ModList.get().getLoadedMods();
		Map<String, Integer> blocks = NBTUtil.convertToIntMap(nbt.getCompoundTag("Blocks"));
		this.blocks = new HashMap<>();
		for (String key : blocks.keySet()) {
			String modid = Namespace.extractModId(key);
			if (modid == null || mods.contains(modid)) this.blocks.put(key, blocks.get(key));
		}
		Map<String, Integer> items = NBTUtil.convertToIntMap(nbt.getCompoundTag("Items"));
		this.items = new HashMap<>();
		for (String key : items.keySet()) {
			String modid = Namespace.extractModId(key);
			if (modid == null || mods.contains(modid)) this.items.put(key, items.get(key));
		}
		this.worldModList = NBTUtil.convertToStringList(nbt.getTagList("ModList"), true);
		System.out.println(this.blocks.size() + " blocks, " + this.items.size() + " items");
		this.fillInMissingIDs();
	}

	public WorldRegistryMap() {
		this.worldModList = new ArrayList<>(ModList.get().getLoadedMods());
		this.blocks = new HashMap<>();
		this.items = new HashMap<>();
		this.fillInMissingIDs();
	}

	public List<String> updateModList() {
		List<String> newMods = ModList.get().getLoadedMods();
		List<String> missingMods = new ArrayList<>();
		for (String mod : this.worldModList) {
			if (!newMods.contains(mod)) missingMods.add(mod);
		}
		return missingMods.isEmpty() ? null : missingMods;
	}
	public void fillInMissingIDs() {
		List<Block> blockNoID = new ArrayList<>();
		for (int i = 0; i < Block.blocksList.length; i++) {
			Block block = Block.blocksList[i];
			if (block != null) {
				String key = GameRegistries.BLOCKS.getKey(block);
				if (this.blocks.containsKey(key)) continue;
				if (!this.blocks.containsValue(i)) {
					this.blocks.put(key, i);
				} else {
					blockNoID.add(block);
				}
			}
		}
		for (Block block : blockNoID) {
			String key = GameRegistries.BLOCKS.getKey(block);
			for (int i = 1; i < Block.blocksList.length; i++) {
				if (!this.blocks.containsValue(i)) {
					this.blocks.put(key, i);
					break;
				}
			}
		}

		List<Item> itemNoID = new ArrayList<>();
		for (int i = 0; i < Item.itemsList.length; i++) {
			Item item = Item.itemsList[i];
			if (item != null) {
				String key = GameRegistries.ITEMS.getKey(item);
				if (this.items.containsKey(key)) continue;
				if (!this.items.containsValue(i)) {
					this.items.put(key, i);
				} else {
					itemNoID.add(item);
				}
			}
		}
		for (Item item : itemNoID) {
			String key = GameRegistries.ITEMS.getKey(item);
			for (int i = 256; i < Item.itemsList.length; i++) {
				if (!this.items.containsValue(i)) {
					this.items.put(key, i);
					break;
				}
			}
		}
	}

	public NBTTagCompound writeTagCompound(NBTTagCompound nbt) {
		nbt.setCompoundTag("Blocks", NBTUtil.convertFromIntMap(this.blocks));
		nbt.setCompoundTag("Items", NBTUtil.convertFromIntMap(this.items));
		nbt.setTag("ModList", NBTUtil.convertFromStringList(this.worldModList, true));
		return nbt;
	}
}
