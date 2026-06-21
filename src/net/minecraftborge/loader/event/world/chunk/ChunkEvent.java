package net.minecraftborge.loader.event.world.chunk;

import net.minecraft.src.Chunk;
import net.minecraftborge.loader.event.world.WorldEvent;

public abstract class ChunkEvent extends WorldEvent {
	private final Chunk chunk;

	public ChunkEvent(Chunk chunk) {
		super(chunk.worldObj);
		this.chunk = chunk;
	}

	public Chunk getChunk() {
		return this.chunk;
	}
}
