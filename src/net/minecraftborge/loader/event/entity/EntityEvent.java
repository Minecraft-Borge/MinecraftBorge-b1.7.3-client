package net.minecraftborge.loader.event.entity;

import net.minecraft.src.Entity;
import net.minecraftborge.loader.event.Event;

public abstract class EntityEvent extends Event {
	private final Entity entity;
	public EntityEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return this.entity;
	}
}
