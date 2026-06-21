package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;

public class PlayerTickEvent extends PlayerEvent {
	private final Phase phase;

	public PlayerTickEvent(EntityPlayer entity, Phase phase) {
		super(entity);
		this.phase = phase;
	}

	public Phase getPhase() {
		return this.phase;
	}
}
