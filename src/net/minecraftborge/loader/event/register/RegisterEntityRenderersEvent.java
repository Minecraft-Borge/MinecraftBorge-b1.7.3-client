package net.minecraftborge.loader.event.register;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import net.minecraftborge.loader.event.Event;

import java.util.Map;

public class RegisterEntityRenderersEvent extends Event {
	private final Map<Class<? extends Entity>, Render> registry;

	public RegisterEntityRenderersEvent(Map<Class<? extends Entity>, Render> registry) {
		this.registry = registry;
	}

	public void register(Class<? extends Entity> clazz, Render render) {
		this.registry.put(clazz, render);
	}
}
