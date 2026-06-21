package net.minecraftborge.loader;

import net.minecraft.src.GuiScreen;

/**
 * All simple data known about a mod
 */
public class ModContainer {
	public final String name;
	public final String modid;
	public final String description;
	public final String version;
	public final String[] dependencies;
	public final Class<? extends GuiScreen> modGuiClass;
	public final int modIndex;

	public ModContainer(String name, String modid, String description,
						String version, String[] dependencies,
						Class<? extends GuiScreen> modGuiClass,
						int modIndex
	) {
		this.name = name;
		this.modid = modid;
		this.description = description;
		this.version = version;
		this.dependencies = dependencies;
		this.modGuiClass = modGuiClass;
		this.modIndex = modIndex;
	}
}
