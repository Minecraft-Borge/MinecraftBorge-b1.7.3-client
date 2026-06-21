package net.minecraftborge.loader.event.register;

import net.minecraftborge.loader.Icon;
import net.minecraftborge.loader.TerrainIconRegister;
import net.minecraftborge.loader.event.Event;

public class TerrainStitchEvent extends Event {
	private final TerrainIconRegister register;

	public TerrainStitchEvent(TerrainIconRegister register) {
		this.register = register;
	}

	public Icon registerIcon(String name, int w, int h) {
		return this.register.getTexture(name, w, h);
	}
}
