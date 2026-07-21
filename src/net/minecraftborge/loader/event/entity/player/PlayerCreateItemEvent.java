package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class PlayerCreateItemEvent extends PlayerEvent {
	private final World world;
	private final ItemStack stack;
	private final String workstation;

	public PlayerCreateItemEvent(ItemStack stack, World world, EntityPlayer entity, String workstation) {
		super(entity);
		this.world = world;
		this.stack = stack;
		this.workstation = workstation;
	}

	public World getWorld() {
		return this.world;
	}
	public ItemStack getStack() {
		return this.stack;
	}
	public String getWorkstation() {
		return this.workstation;
	}
}
