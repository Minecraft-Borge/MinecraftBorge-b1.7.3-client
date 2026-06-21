package net.minecraftborge.loader.event.entity;

import net.minecraft.src.Entity;

public class EntityTickEvent extends EntityEvent {
	private final Phase phase;

	public EntityTickEvent(Entity entity, Phase phase) {
		super(entity);
		this.phase = phase;
	}

	public Phase getPhase() {
		return this.phase;
	}
}
