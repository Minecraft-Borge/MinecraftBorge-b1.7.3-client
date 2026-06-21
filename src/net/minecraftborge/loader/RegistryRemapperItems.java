package net.minecraftborge.loader;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.Locale;
import java.util.Map;

public class RegistryRemapperItems implements RegistryMapper {
	public static final RegistryRemapperItems IDENTITY = new RegistryRemapperItems(new int[Item.itemsList.length], new int[Item.itemsList.length]) {
		@Override
		public ItemStack remap(ItemStack stack) {
			return stack;
		}

		@Override
		public ItemStack demap(ItemStack stack) {
			return stack;
		}
	};

	private final int[] remapper;
	private final int[] demapper;

	public RegistryRemapperItems(int[] remapper, int[] demapper) {
		this.remapper = remapper;
		this.demapper = demapper;

		if (this.remapper.length != this.demapper.length && this.remapper.length != Item.itemsList.length) throw new IllegalArgumentException();
	}

	public RegistryRemapperItems(RegistryItems registry, Map<String, Integer> remote) {
		this(new int[Item.itemsList.length], new int[Item.itemsList.length]);

		for (int i = 0; i < Item.itemsList.length; i++) {
			Item item = Item.itemsList[i];
			if (item != null) {
				String name = registry.getKey(item);
				Integer remoteID = remote.get(name);
				if (remoteID == null) throw new IllegalStateException("Item missing on server: " + name);
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

	public ItemStack remap(ItemStack stack) {
		if (stack == null) return null;
		return new ItemStack(
				this.remapper[stack.itemID],
				stack.stackSize,
				stack.getItemDamage()
		);
	}
	public ItemStack demap(ItemStack stack) {
		if (stack == null) return null;
		int id = this.demapper[stack.itemID];
		if (id == stack.itemID) return stack;
		return new ItemStack(
				this.demapper[stack.itemID],
				stack.stackSize,
				stack.getItemDamage()
		);
	}

	static {
		for (int i = 0; i < Item.itemsList.length; i++) {
			IDENTITY.remapper[i] = i;
			IDENTITY.demapper[i] = i;
		}
	}
}
