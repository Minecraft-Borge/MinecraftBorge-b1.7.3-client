package net.minecraftborge.loader;

import net.minecraft.src.Block;
import net.minecraft.src.NibbleArray;

import java.util.Locale;
import java.util.Map;

public class RegistryRemapperBlocks implements RegistryMapper {
	public static final RegistryRemapperBlocks IDENTITY = new RegistryRemapperBlocks(new short[Block.blocksList.length], new short[Block.blocksList.length]) {
		@Override
		public void remap(byte[] chunk) {

		}
		@Override
		public void demap(byte[] chunk) {

		}
		@Override
		public void demapUntil(byte[] chunk, int lim) {

		}

		@Override
		public void remapNew(byte[] chunk, NibbleArray extended) {

		}
		@Override
		public void demapNew(byte[] chunk, NibbleArray extended) {

		}
		@Override
		public void demapNewUntil(byte[] chunk, int lim) {

		}
	};

	private final short[] remapper;
	private final short[] demapper;

	public RegistryRemapperBlocks(short[] remapper, short[] demapper) {
		this.remapper = remapper;
		this.demapper = demapper;

		if (this.remapper.length != this.demapper.length && this.remapper.length != Block.blocksList.length) throw new IllegalArgumentException();
	}

	public RegistryRemapperBlocks(RegistryBlocks registry, Map<String, Integer> remote) {
		this(new short[Block.blocksList.length], new short[Block.blocksList.length]);

		for (int i = 0; i < Block.blocksList.length; i++) {
			Block block = Block.blocksList[i];
			if (block != null) {
				String name = registry.getKey(block);
				Integer remoteID = remote.get(name);
				if (remoteID == null) throw new IllegalStateException("Block missing on server: " + name);
				this.remapper[i] = remoteID.shortValue();
				this.demapper[remoteID] = (short)i;
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

	public void remapNew(byte[] chunk, NibbleArray extended) {
		for (int i = 0; i < chunk.length; i++) {
			int old = (chunk[i] & 255) | (extended.getNibble(i)) << 8;
			short now = this.remapper[old];
			chunk[i] = (byte) now;
			extended.setNibble(i, now >> 8);
		}
	}
	public void demapNew(byte[] chunk, NibbleArray extended) {
		for (int i = 0; i < chunk.length; i++) {
			int old = (chunk[i] & 255) | (extended.getNibble(i)) << 8;
			short now = this.demapper[old];
			chunk[i] = (byte) now;
			extended.setNibble(i, now >> 8);
		}
	}

	public void demapNewUntil(byte[] chunk, int lim) {
		for (int i = 0; i < lim; i++) {
			int old = (chunk[i] & 255) | (NibbleArray.getNibble(chunk, lim, i)) << 8;
			short now = this.demapper[old];
			chunk[i] = (byte) now;
			NibbleArray.setNibble(chunk, lim, i, now >> 8);
		}
	}

	static {
		for (short i = 0; i < Block.blocksList.length; i++) {
			IDENTITY.remapper[i] = i;
			IDENTITY.demapper[i] = i;
		}
	}
}
