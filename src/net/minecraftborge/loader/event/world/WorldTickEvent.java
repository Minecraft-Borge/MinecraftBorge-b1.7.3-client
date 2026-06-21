package net.minecraftborge.loader.event.world;

import net.minecraft.src.World;

public class WorldTickEvent extends WorldEvent {
	private final Phase phase;

	public WorldTickEvent(World world, Phase phase) {
		super(world);
		this.phase = phase;
	}

	public Phase getPhase() {
		return this.phase;
	}
}
