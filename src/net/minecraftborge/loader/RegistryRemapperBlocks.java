package net.minecraftborge.loader;

import net.minecraft.src.Block;

import java.util.Locale;
import java.util.Map;

public class RegistryRemapperBlocks implements RegistryMapper {
	public static final RegistryRemapperBlocks IDENTITY = new RegistryRemapperBlocks(new int[Block.blocksList.length], new int[Block.blocksList.length]) {
		@Override
		public void remap(byte[] chunk) {

		}

		@Override
		public void demap(byte[] chunk) {

		}
	};

	private final int[] remapper;
	private final int[] demapper;

	public RegistryRemapperBlocks(int[] remapper, int[] demapper) {
		this.remapper = remapper;
		this.demapper = demapper;

		if (this.remapper.length != this.demapper.length && this.remapper.length != Block.blocksList.length) throw new IllegalArgumentException();
	}

	public RegistryRemapperBlocks(RegistryBlocks registry, Map<String, Integer> remote) {
		this(new int[Block.blocksList.length], new int[Block.blocksList.length]);

		for (int i = 0; i < Block.blocksList.length; i++) {
			Block block = Block.blocksList[i];
			if (block != null) {
				String name = registry.getKey(block);
				Integer remoteID = remote.get(name);
				if (remoteID == null) throw new IllegalStateException("Block missing on server: " + name);
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

	public void remap(byte[] chunk) {
		for (int i = 0; i < chunk.length; i++) {
			chunk[i] = (byte) this.remapper[chunk[i] & 255];
		}
	}
	public void demap(byte[] chunk) {
		for (int i = 0; i < chunk.length; i++) {
			chunk[i] = (byte) this.demapper[chunk[i] & 255];
		}
	}

	public void demapUntil(byte[] chunk, int lim) {
		for (int i = 0; i < lim; i++) {
			chunk[i] = (byte) this.demapper[chunk[i] & 255];
		}
	}

	static {
		for (int i = 0; i < Block.blocksList.length; i++) {
			IDENTITY.remapper[i] = i;
			IDENTITY.demapper[i] = i;
		}
	}
}
