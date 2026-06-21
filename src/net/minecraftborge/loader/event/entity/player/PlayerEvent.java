package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;
import net.minecraftborge.loader.event.entity.EntityEvent;

public abstract class PlayerEvent extends EntityEvent {
	public PlayerEvent(EntityPlayer entity) {
		super(entity);
	}

	@Override
	public EntityPlayer getEntity() {
		return (EntityPlayer) super.getEntity();
	}
}
