package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;
import net.minecraftborge.loader.event.Cancelable;

@Cancelable
public class PlayerJumpEvent extends PlayerEvent {
	public PlayerJumpEvent(EntityPlayer entity) {
		super(entity);
	}
}
