package net.minecraftborge.loader.event.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

import java.util.List;

public class InitGuiEvent extends GuiEvent {
	private final List<GuiButton> actionList;

	public InitGuiEvent(GuiScreen screen, List<GuiButton> actionList) {
		super(screen);

		this.actionList = actionList;
	}

	public List<GuiButton> getActionList() {
		return this.actionList;
	}
}
