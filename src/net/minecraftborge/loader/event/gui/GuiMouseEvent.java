package net.minecraftborge.loader.event.gui;

import net.minecraft.src.GuiScreen;
import net.minecraftborge.loader.event.Cancelable;

@Cancelable
public class GuiMouseEvent extends GuiEvent {
	public GuiMouseEvent(GuiScreen screen) {
		super(screen);
	}
}
