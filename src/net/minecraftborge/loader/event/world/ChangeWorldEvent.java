package net.minecraftborge.loader.event.world;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class ChangeWorldEvent extends WorldEvent {
	private final World previousWorld;
	private final String message;
	private final EntityPlayer player;

	public ChangeWorldEvent(World previousWorld, World world, String message, EntityPlayer player) {
		super(world);
		this.previousWorld = previousWorld;
		this.message = message;
		this.player = player;
	}

	public World getPreviousWorld() {
		return this.previousWorld;
	}
	public String getMessage() {
		return this.message;
	}
	public EntityPlayer getPlayer() {
		return this.player;
	}
}
