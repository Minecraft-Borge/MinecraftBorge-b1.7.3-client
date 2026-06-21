package net.minecraftborge.loader.event.world;

import net.minecraft.src.World;
import net.minecraftborge.loader.event.Event;

public abstract class WorldEvent extends Event {
	private final World world;

	public WorldEvent(World world) {
		this.world = world;
	}

	public World getWorld() {
		return this.world;
	}
}
