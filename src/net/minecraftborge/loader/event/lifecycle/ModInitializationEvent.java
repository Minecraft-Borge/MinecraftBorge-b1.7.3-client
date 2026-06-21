package net.minecraftborge.loader.event.lifecycle;

import net.minecraft.src.StringTranslate;
import net.minecraftborge.loader.IModGUIFactory;
import net.minecraftborge.loader.ModContainer;
import net.minecraftborge.loader.ModList;
import net.minecraftborge.loader.net.ModNetHandler;

import java.util.Objects;

public class ModInitializationEvent extends ModLifecycleEvent {
	public ModInitializationEvent() {}

	public void registerLanguage() {
		ModContainer current = ModList.get().getCurrentlyLoading();
		StringTranslate.getInstance().load("lang/" + current.modid + "_US.lang");
	}

	public void registerNetHandler(ModNetHandler netHandler) {
		Objects.requireNonNull(netHandler, "netHandler");
		ModContainer current = ModList.get().getCurrentlyLoading();
		ModList.get().registerNetHandler(current.modid, netHandler);
	}
	public void registerGUIFactory(IModGUIFactory factory) {
		Objects.requireNonNull(factory, "factory");
		ModContainer current = ModList.get().getCurrentlyLoading();
		ModList.get().registerGUIFactory(current.modid, factory);
	}
}
