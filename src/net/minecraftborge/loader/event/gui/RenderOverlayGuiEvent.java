package net.minecraftborge.loader.event.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.ScaledResolution;
import net.minecraftborge.loader.event.Cancelable;
import net.minecraftborge.loader.event.Event;

@Cancelable
public class RenderOverlayGuiEvent extends Event {
	private final Minecraft mc;
	private final GuiIngame ingameGUI;
	private final Layer layer;
	public ScaledResolution resolution;
	public float partialTick;

	public RenderOverlayGuiEvent(Minecraft mc, GuiIngame screen, Layer layer) {
		this.mc = mc;
		this.ingameGUI = screen;
		this.layer = layer;
	}

	public Minecraft getMc() {
		return this.mc;
	}
	public GuiIngame getIngameGUI() {
		return this.ingameGUI;
	}
	public Layer getLayer() {
		return this.layer;
	}

	public enum Layer {
		PRE,
		VIGNETTE,
		PUMPKIN,
		PORTAL,
		ARMOR_BAR,
		BUBBLES_BAR,
		HOTBAR_ITEMS,
		DEBUG_SCREEN,
		CHAT_SCREEN,
		POST,
	}
}
