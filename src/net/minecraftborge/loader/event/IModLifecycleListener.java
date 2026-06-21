package net.minecraftborge.loader.event;

import net.minecraftborge.loader.event.lifecycle.ModInitializationEvent;
import net.minecraftborge.loader.event.lifecycle.ModPostInitializationEvent;
import net.minecraftborge.loader.event.lifecycle.ModPreInitializationEvent;

@SuppressWarnings("unused")
public interface IModLifecycleListener {
	default void modPreInit(ModPreInitializationEvent event) {}
	default void modInit(ModInitializationEvent event) {}
	default void modPostInit(ModPostInitializationEvent event) {}

	default void newestVersionOnline(String version) {}
}
