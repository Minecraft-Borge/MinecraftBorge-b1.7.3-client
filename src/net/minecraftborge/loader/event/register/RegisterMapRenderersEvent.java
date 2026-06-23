package net.minecraftborge.loader.event.register;

import net.minecraftborge.loader.IMapLikeRenderer;
import net.minecraftborge.loader.event.Event;

import java.util.Map;

public class RegisterMapRenderersEvent extends Event {
	private final Map<String, IMapLikeRenderer.Factory> registry;

	public RegisterMapRenderersEvent(Map<String, IMapLikeRenderer.Factory> registry) {
		this.registry = registry;
	}

	public void register(String itemID, IMapLikeRenderer.Factory factory) {
		this.registry.put(itemID, factory);
	}
}
