package net.minecraftborge.loader.event.gui;

import net.minecraft.src.GuiScreen;

public class RenderGuiEvent extends GuiEvent {
	private final Phase phase;

	public RenderGuiEvent(GuiScreen screen, Phase phase) {
		super(screen);
		this.phase = phase;
	}

	public Phase getPhase() {
		return this.phase;
	}
}
