package net.minecraftborge.loader;

import net.minecraft.src.Block;

import java.util.Arrays;

public class MetaBlock {
	private static final MetaBlock[] CACHE = new MetaBlock[Block.blocksList.length * 16];
	private static final MetaBlock[] WILDCARD = new MetaBlock[Block.blocksList.length];

	public static final MetaBlock AIR = new MetaBlock(null, 0);

	public final Block block;
	public final int meta;

	private MetaBlock(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}

	public int pack() {
		return this.isAir() ? 0 : pack(this.block.blockID, this.meta);
	}
	public boolean isAir() {
		return this.block == null;
	}

	public static MetaBlock get(Block block, int meta) {
		if (meta == -1) return WILDCARD[block.blockID];
		return CACHE[(block.blockID << 4) | meta];
	}

	static {
		Arrays.fill(CACHE, AIR);
	}

	public static void initialize() {
		for (int i = 0; i < Block.blocksList.length; i++) {
			Block block = Block.blocksList[i];
			if (block == null) continue;
			for (int meta = 0; meta < 16; meta++) {
				CACHE[pack(i, meta)] = new MetaBlock(block, i);
			}
			WILDCARD[i] = new MetaBlock(block, -1);
		}
	}

	public static int pack(int blockID, int meta) {
		return (blockID << 4) | meta;
	}
}
