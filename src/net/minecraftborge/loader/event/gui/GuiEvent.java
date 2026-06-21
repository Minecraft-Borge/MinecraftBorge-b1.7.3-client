package net.minecraftborge.loader.event.gui;

import net.minecraft.src.GuiScreen;
import net.minecraftborge.loader.event.Event;

public abstract class GuiEvent extends Event {
	private final GuiScreen screen;

	public GuiEvent(GuiScreen screen) {
		this.screen = screen;
	}

	public GuiScreen getScreen() {
		return this.screen;
	}
}
