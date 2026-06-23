package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public abstract class PlayerBlockInteractionEvent extends PlayerEvent {
	private final int x, y, z;
	private final World world;

	public PlayerBlockInteractionEvent(EntityPlayer entity, int x, int y, int z) {
		super(entity);
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = entity.worldObj;
	}

	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getZ() {
		return this.z;
	}
	public World getWorld() {
		return this.world;
	}
}
