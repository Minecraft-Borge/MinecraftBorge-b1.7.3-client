package net.minecraftborge.loader.tag;

import net.minecraftborge.loader.MetaBlock;

import java.util.*;

public class BlockTags {
	private static final List<String> TAG_NAMES = new ArrayList<>();
	private static final List<List<MetaBlock>> ENTRIES = new ArrayList<>();
	private static final Map<String, Integer> NAME_LOOKUP = new HashMap<>();
	private static final Map<Integer, List<Integer>> TAG_LOOKUP = new HashMap<>();

	public static void registerDefaults() {

	}

	public static void tag(String name, MetaBlock block) {
		if (block == null || block.isAir()) throw new IllegalArgumentException("Cannot register empty block in BlockTags");
		Integer tagID = NAME_LOOKUP.get(name);
		if (tagID == null) {
			tagID = TAG_NAMES.size();
			TAG_NAMES.add(name);
			ENTRIES.add(new ArrayList<>());
			NAME_LOOKUP.put(name, tagID);
		}
		int packed = 0;
		ENTRIES.get(tagID).add(block);
		TAG_LOOKUP.computeIfAbsent(packed, $ -> new ArrayList<>()).add(tagID);
	}
	public static void tag(String name, MetaBlock... blocks) {
		for (MetaBlock block : blocks) tag(name, block);
	}

	public static List<MetaBlock> getTagged(String name) {
		Integer tagID = NAME_LOOKUP.get(name);
		return tagID != null ? ENTRIES.get(tagID) : Collections.emptyList();
	}
	public static List<Integer> getTags(MetaBlock block) {
		int packed = block.pack();
		List<Integer> normal = TAG_LOOKUP.getOrDefault(packed, Collections.emptyList());
		int packed1 = MetaBlock.get(block.block, -1).pack();
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

	public static boolean matches(MetaBlock example, MetaBlock test) {
		if (example.isAir() || test.isAir()) return false;
		return example.block == test.block && (example.meta == -1 || example.meta == test.meta);
	}
}
