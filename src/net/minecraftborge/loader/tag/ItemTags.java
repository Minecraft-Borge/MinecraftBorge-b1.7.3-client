package net.minecraftborge.loader.tag;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftborge.loader.DyeHelper;

import java.util.*;

public class ItemTags {
	private static final List<String> TAG_NAMES = new ArrayList<>();
	private static final List<List<ItemStack>> ENTRIES = new ArrayList<>();
	private static final Map<String, Integer> NAME_LOOKUP = new HashMap<>();
	private static final Map<Integer, List<Integer>> TAG_LOOKUP = new HashMap<>();

	public static void registerDefaults() {
		tag("dirt", Block.dirt);
		tag("dirt", Block.grass);
		tag("stone", Block.stone);
		tag("cobblestone", Block.cobblestone);
		tag("sand", Block.sand);
		tag("gravel", Block.gravel);

		tag("oreCoal", Block.oreCoal);
		tag("oreIron", Block.oreIron);
		tag("oreGold", Block.oreGold);
		tag("oreDiamond", Block.oreDiamond);
		tag("oreLapis", Block.oreLapis);
		tag("oreRedstone", Block.oreRedstone);
		tag("oreRedstone", Block.oreRedstoneGlowing);

		tag("treeLeaves", new ItemStack(Block.leaves, 1, 0));
		tag("treeLeaves", new ItemStack(Block.leaves, 1, 1));
		tag("treeLeaves", new ItemStack(Block.leaves, 1, 2));
		tag("treeSapling", new ItemStack(Block.sapling, 1, 0));
		tag("treeSapling", new ItemStack(Block.sapling, 1, 1));
		tag("treeSapling", new ItemStack(Block.sapling, 1, 2));
		tag("logWood", new ItemStack(Block.wood, 1, 0));
		tag("logWood", new ItemStack(Block.wood, 1, 1));
		tag("logWood", new ItemStack(Block.wood, 1, 2));
		tag("planksWood", Block.planks);
		tag("stickWood", Item.stick);

		tag("cloth", new ItemStack(Block.cloth, 1, -1));

		tag("ingotIron", Item.ingotIron);
		tag("ingotGold", Item.ingotGold);
		tag("gemCoal", Item.coal);
		tag("gemLapis", new ItemStack(Item.dyePowder, 1, 4));
		tag("gemDiamond", Item.diamond);
		tag("dustRedstone", Item.redstone);

		tag("blockIron", Block.blockIron);
		tag("blockGold", Block.blockGold);
		tag("blockLapis", Block.blockLapis);
		tag("blockDiamond", Block.blockDiamond);

		for (int i = 0; i < 16; i++) {
			tag("dye" + DyeHelper.ITEM_NAMES[i], new ItemStack(Item.dyePowder, 1, i));
		}
	}

	public static void tag(String name, Block block) {
		tag(name, new ItemStack(block, 1, 0));
	}
	public static void tag(String name, Item item) {
		tag(name, new ItemStack(item, 1, 0));
	}
	public static void tag(String name, ItemStack stack) {
		if (isItemEmpty(stack)) throw new IllegalArgumentException("Cannot register empty item in ItemTags");
		Integer tagID = NAME_LOOKUP.get(name);
		if (tagID == null) {
			tagID = TAG_NAMES.size();
			TAG_NAMES.add(name);
			ENTRIES.add(new ArrayList<>());
			NAME_LOOKUP.put(name, tagID);
		}
		int packed = pack(stack);
		ENTRIES.get(tagID).add(stack);
		TAG_LOOKUP.computeIfAbsent(packed, $ -> new ArrayList<>()).add(tagID);
	}
	public static void tag(String name, ItemStack... stacks) {
		for (ItemStack stack : stacks) tag(name, stack);
	}

	public static List<ItemStack> getTagged(String name) {
		Integer tagID = NAME_LOOKUP.get(name);
		if (tagID == null) return Collections.emptyList();
		return ENTRIES.get(tagID);
	}
	public static List<Integer> getTags(ItemStack stack) {
		int packed = pack(stack);
		List<Integer> normal = TAG_LOOKUP.getOrDefault(packed, Collections.emptyList());
		int packed1 = pack(stack.itemID, -1);
		List<Integer> wildcard = TAG_LOOKUP.getOrDefault(packed1, Collections.emptyList());
		if (normal.isEmpty()) return wildcard;
		if (wildcard.isEmpty()) return normal;
		ArrayList<Integer> list = new ArrayList<>(normal.size() + wildcard.size());
		list.addAll(normal);
		list.addAll(wildcard);
		return list;
	}

	public static String getTagName(int tagID) {
		return TAG_NAMES.get(tagID);
	}
	public static List<String> getAllTags() {
		return Collections.unmodifiableList(TAG_NAMES);
	}

	public static boolean isItemEmpty(ItemStack stack) {
		return stack == null || stack.stackSize <= 0 || stack.getItem() == null;
	}
	public static int pack(int itemID, int metadata) {
		return (itemID & 0xFFFF) | ((metadata & 0xFFFF) << 16);
	}
	public static int pack(ItemStack stack) {
		return pack(stack.itemID, stack.getItemDamage());
	}

	public static boolean matches(ItemStack example, ItemStack test) {
		if (example == null || test == null) return false;
		return example.itemID == test.itemID && (example.getItemDamage() == -1 || example.getItemDamage() == test.getItemDamage());
	}
}
