package net.minecraftborge.loader.event.gui;

import net.minecraft.src.GuiScreen;

public class GuiTickEvent extends GuiEvent {
	private final Phase phase;

	public GuiTickEvent(GuiScreen screen, Phase phase) {
		super(screen);
		this.phase = phase;
	}

	public Phase getPhase() {
		return this.phase;
	}
}
