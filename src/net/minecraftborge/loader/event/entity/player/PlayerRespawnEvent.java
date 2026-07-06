package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;
import net.minecraftborge.loader.event.Cancelable;

@Cancelable // cancel to force respawning at world spawn
public class PlayerRespawnEvent extends PlayerEvent {
	public PlayerRespawnEvent(EntityPlayer entity) {
		super(entity);
	}
}
