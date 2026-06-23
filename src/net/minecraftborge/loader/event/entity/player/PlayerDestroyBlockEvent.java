package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;
import net.minecraftborge.loader.event.Cancelable;

@Cancelable
public class PlayerDestroyBlockEvent extends PlayerBlockInteractionEvent {
	private final Phase phase;
	private final int blockId, blockMetadata;
	public PlayerDestroyBlockEvent(EntityPlayer entity, Phase phase, int x, int y, int z, int blockId, int blockMetadata) {
		super(entity, x, y, z);
		this.phase = phase;
		this.blockId = blockId;
		this.blockMetadata = blockMetadata;
	}

	public Phase getPhase() {
		return this.phase;
	}
	public int getBlockId() {
		return this.blockId;
	}
	public int getBlockMetadata() {
		return this.blockMetadata;
	}
}
