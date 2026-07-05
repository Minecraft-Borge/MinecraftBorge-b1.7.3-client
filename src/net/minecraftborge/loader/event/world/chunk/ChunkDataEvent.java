package net.minecraftborge.loader.event.world.chunk;

import net.minecraft.src.Chunk;
import net.minecraft.src.NBTTagCompound;

public abstract class ChunkDataEvent extends ChunkEvent {
	private final NBTTagCompound data;
	public ChunkDataEvent(Chunk chunk, NBTTagCompound data) {
		super(chunk);
		this.data = data;
	}

	public NBTTagCompound getData() {
		return this.data;
	}

	public static class Load extends ChunkDataEvent {
		public Load(Chunk chunk, NBTTagCompound data) {
			super(chunk, data);
		}
	}

	public static class Store extends ChunkDataEvent {
		public Store(Chunk chunk, NBTTagCompound data) {
			super(chunk, data);
		}
	}
}
