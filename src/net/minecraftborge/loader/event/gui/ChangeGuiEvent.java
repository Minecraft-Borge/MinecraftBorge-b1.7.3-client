package net.minecraftborge.loader.event.gui;

import net.minecraft.src.GuiScreen;
import net.minecraftborge.loader.event.Cancelable;
import net.minecraftborge.loader.event.Event;

@Cancelable
public class ChangeGuiEvent extends Event {
	private final GuiScreen previousScreen;
	private GuiScreen newScreen;

	public ChangeGuiEvent(GuiScreen previousScreen, GuiScreen newScreen) {
		this.previousScreen = previousScreen;
		this.newScreen = newScreen;
	}

	public GuiScreen getPreviousScreen() {
		return this.previousScreen;
	}
	public GuiScreen getNewScreen() {
		return this.newScreen;
	}
	public void setNewScreen(GuiScreen screen) {
		this.newScreen = screen;
	}
}
