package net.minecraftborge.loader.event.register;

import net.minecraft.src.RenderEngine;
import net.minecraft.src.TextureFX;
import net.minecraftborge.loader.event.Event;

public class RegisterTextureFXEvent extends Event {
	private final RenderEngine renderer;

	public RegisterTextureFXEvent(RenderEngine renderer) {
		this.renderer = renderer;
	}

	public void register(TextureFX fx) {
		this.renderer.registerTextureFX(fx);
	}
}
