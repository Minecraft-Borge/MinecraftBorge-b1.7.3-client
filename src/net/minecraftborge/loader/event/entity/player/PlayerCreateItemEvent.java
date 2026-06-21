package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class PlayerCreateItemEvent extends PlayerEvent {
	private final World world;
	private final ItemStack stack;

	public PlayerCreateItemEvent(ItemStack stack, World world, EntityPlayer entity) {
		super(entity);
		this.world = world;
		this.stack = stack;
	}

	public World getWorld() {
		return this.world;
	}
	public ItemStack getStack() {
		return this.stack;
	}
}
