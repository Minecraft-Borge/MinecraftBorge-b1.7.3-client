package net.minecraftborge.loader.event.render;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityLiving;
import net.minecraftborge.loader.event.Event;

public class GetFOVModifierEvent extends Event {
	private float partialTick;
	private float fov;
	private float fovModified;

	private final Minecraft mc;

	public GetFOVModifierEvent(Minecraft mc) {
		this.mc = mc;
	}

	public void prepare(float partialTick, float fov) {
		this.partialTick = partialTick;
		this.fov = this.fovModified = fov;
	}

	public float getPartialTick() {
		return this.partialTick;
	}
	public float getFOV() {
		return this.fov;
	}
	public float getFOVModified() {
		return this.fovModified;
	}

	public void setFOV(float fov) {
		this.fovModified = fov;
	}

	public Minecraft getMC() {
		return this.mc;
	}
	public EntityLiving getCamera() {
		return this.mc.renderViewEntity;
	}
}
