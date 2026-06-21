package net.minecraftborge.loader;

import net.minecraft.src.GuiScreen;
import net.minecraftborge.MinecraftBorge;
import net.minecraftborge.loader.event.IModLifecycleListener;
import net.minecraftborge.loader.event.lifecycle.ModInitializationEvent;
import net.minecraftborge.loader.event.lifecycle.ModPostInitializationEvent;
import net.minecraftborge.loader.event.lifecycle.ModPreInitializationEvent;
import net.minecraftborge.loader.loading.ModCandidate;
import net.minecraftborge.loader.net.ModNetHandler;

import java.util.*;

/**
 * Keeps a list of all currently loaded mods along with data about them
 */
public class ModList {
	private static ModList instance;
	private ModList(List<String> sortedModIDs, Map<String, ModCandidate> byName, List<IModLifecycleListener> listeners) {
		System.out.println(sortedModIDs.size() + " mods");
		System.out.println(sortedModIDs);

		this.modIDList = sortedModIDs;
		this.modIDListImmutable = Collections.unmodifiableList(this.modIDList);
		this.modLifecycleListeners = listeners;
		this.modContainerList = new ArrayList<>();
		this.index = new HashMap<>();
		this.modNetHandlers = new ModNetHandler[this.modIDList.size()];
		Arrays.fill(this.modNetHandlers, ModNetHandler.DUMMY);
		this.guiFactories = new HashMap<>();

		this.index(byName);
	}
	public static void initialize(List<String> sortedModIDs, Map<String, ModCandidate> byName, List<IModLifecycleListener> listeners) {
		if (instance != null) throw new RuntimeException();
		instance = new ModList(sortedModIDs, byName, listeners);
	}

	public static ModList get() {
		return instance;
	}

	private void index(Map<String, ModCandidate> map) {
		for (int i = 0; i < this.modIDList.size(); ++i) {
			String modid = this.modIDList.get(i);
			this.index.put(modid, i);
			ModCandidate candidate = map.get(modid);

			String name = candidate.name;
			String version = candidate.version;
			String display = candidate.attributes.getValue("Mod-Name");
			String description = candidate.attributes.getValue("Mod-Description");
			String[] dependencies = candidate.dependencies.toArray(new String[0]);
			String modGuiClassName = candidate.attributes.getValue("Mod-Gui-Class");
			Class<? extends GuiScreen> modGuiClass = null;
			try {
				Class<?> clazz = modGuiClassName != null && !modGuiClassName.trim().isEmpty() ?
						MinecraftBorge.getClassLoader().loadClass(modGuiClassName.trim()) : null;
				if (clazz != null && GuiScreen.class.isAssignableFrom(clazz)) modGuiClass = (Class<? extends GuiScreen>) clazz;
			} catch (Throwable ignored) {

			}
			ModContainer container = new ModContainer(display != null ? display : name, name, description, version, dependencies, modGuiClass, i);
			this.modContainerList.add(container);
		}
	}

	private ModContainer currentlyLoading = null;
	private final List<String> modIDList;
	private final List<String> modIDListImmutable;
	private final List<IModLifecycleListener> modLifecycleListeners;
	private final List<ModContainer> modContainerList;
	private final ModNetHandler[] modNetHandlers;
	private final Map<String, Integer> index;
	private final Map<String, IModGUIFactory> guiFactories;

	public void modPreInit(ModPreInitializationEvent event) {
		for (int i = 0; i < this.modIDList.size(); i++) {
			IModLifecycleListener listener = this.modLifecycleListeners.get(i);

			if (listener != null) {
				this.currentlyLoading = this.modContainerList.get(i);
				listener.modPreInit(event);
			}
		}
		this.currentlyLoading = null;
	}
	public void modInit(ModInitializationEvent event) {
		for (int i = 0; i < this.modIDList.size(); i++) {
			IModLifecycleListener listener = this.modLifecycleListeners.get(i);

			if (listener != null) {
				this.currentlyLoading = this.modContainerList.get(i);
				listener.modInit(event);
			}
		}
		this.currentlyLoading = null;
	}
	public void modPostInit(ModPostInitializationEvent event) {
		for (int i = 0; i < this.modIDList.size(); i++) {
			IModLifecycleListener listener = this.modLifecycleListeners.get(i);

			if (listener != null) {
				this.currentlyLoading = this.modContainerList.get(i);
				listener.modPostInit(event);
			}
		}
		this.currentlyLoading = null;
	}
	public ModContainer getCurrentlyLoading() {
		return this.currentlyLoading;
	}

	public int size() {
		return this.modIDList.size();
	}

	public ModContainer getModContainer(int index) {
		return this.modContainerList.get(index);
	}
	public int getModIndex(String modid) {
		Integer i = this.index.get(modid);
		if (i == null) throw new NullPointerException("Mod " + modid + " is not loaded");
		return i;
	}

	public ModNetHandler getNetHandler(int index) {
		return this.modNetHandlers[index];
	}

	public void notifyNewestModVersion(String modid, String version) {
		this.modLifecycleListeners.get(this.getModIndex(modid)).newestVersionOnline(version);
	}

	public void registerNetHandler(String modid, ModNetHandler netHandler) {
		this.modNetHandlers[this.getModIndex(modid)] = netHandler;
	}
	public void registerGUIFactory(String modid, IModGUIFactory factory) {
		this.guiFactories.put(modid, factory);
	}
	public IModGUIFactory getGUIFactory(String modid) {
		return this.guiFactories.get(modid);
	}

	public List<String> getRemoteMods() {
		return this.modIDListImmutable;
	}
	public List<String> getLoadedMods() {
		return this.modIDListImmutable;
	}
}
