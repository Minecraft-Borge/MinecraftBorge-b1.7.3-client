package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;

public class PlayerPickupItemEvent extends PlayerEvent {
	private final EntityItem itemEntity;
	private final int oldStackSize;
	public PlayerPickupItemEvent(EntityPlayer entity, EntityItem itemEntity, int oldStackSize) {
		super(entity);
		this.itemEntity = itemEntity;
		this.oldStackSize = oldStackSize;
	}

	public EntityItem getEntityItem() {
		return this.itemEntity;
	}
	public int getOldStackSize() {
		return this.oldStackSize;
	}
}
