package net.minecraftborge.loader.event.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraftborge.loader.event.Cancelable;

@Cancelable
public class GuiButtonPressEvent extends GuiEvent {
	private final GuiButton button;
	private final int mouseX, mouseY;

	public GuiButtonPressEvent(GuiScreen screen, GuiButton button, int mouseX, int mouseY) {
		super(screen);

		this.button = button;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}

	public GuiButton getButton() {
		return this.button;
	}
	public int getMouseX() {
		return this.mouseX;
	}
	public int getMouseY() {
		return this.mouseY;
	}
}
