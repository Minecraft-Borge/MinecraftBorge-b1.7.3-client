package net.minecraftborge.loader.event.gui;

import net.minecraft.src.GuiScreen;
import net.minecraftborge.loader.event.Cancelable;

@Cancelable
public class GuiKeyboardEvent extends GuiEvent {
	public GuiKeyboardEvent(GuiScreen screen) {
		super(screen);
	}
}
