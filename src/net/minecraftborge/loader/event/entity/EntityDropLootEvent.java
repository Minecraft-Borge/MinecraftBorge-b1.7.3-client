package net.minecraftborge.loader.event.entity;

import net.minecraft.src.Entity;
import net.minecraftborge.loader.event.Cancelable;

@Cancelable
public class EntityDropLootEvent extends EntityEvent {
	public EntityDropLootEvent(Entity entity) {
		super(entity);
	}
}
