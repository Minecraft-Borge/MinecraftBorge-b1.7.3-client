package net.minecraftborge.loader.event.gui;

import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;

public abstract class RenderGuiContainerEvent extends RenderGuiEvent {
	private int mouseX, mouseY;
	private float pt;
	private int guiX, guiY;
	public RenderGuiContainerEvent(GuiContainer screen, Phase phase) {
		super(screen, phase);
	}

	// INTERNAL USE ONLY
	public void setMouse(int mx, int my, float pt, int x, int y) {
		this.mouseX = mx;
		this.mouseY = my;
		this.pt = pt;
		this.guiX = x;
		this.guiY = y;
		this.setCanceled(false);
	}

	public int getMouseX() {
		return this.mouseX;
	}
	public int getMouseY() {
		return this.mouseY;
	}
	public float getPartialTick() {
		return this.pt;
	}
	public int getGuiX() {
		return this.guiX;
	}
	public int getGuiY() {
		return this.guiY;
	}

	@Override
	public GuiContainer getScreen() {
		return (GuiContainer) super.getScreen();
	}
	public Container getContainer() {
		return this.getScreen().inventorySlots;
	}

	public static class Background extends RenderGuiContainerEvent {
		public Background(GuiContainer screen, Phase phase) {
			super(screen, phase);
		}
	}
	public static class Slots extends RenderGuiContainerEvent {
		public Slots(GuiContainer screen, Phase phase) {
			super(screen, phase);
		}
	}
	public static class Foreground extends RenderGuiContainerEvent {
		public Foreground(GuiContainer screen, Phase phase) {
			super(screen, phase);
		}
	}
	public static class Tooltip extends RenderGuiContainerEvent {
		public Tooltip(GuiContainer screen, Phase phase) {
			super(screen, phase);
		}
	}
}
