package net.minecraftborge.loader.event.render;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityLiving;
import net.minecraftborge.loader.event.Event;
import net.minecraftborge.loader.math.Vector3f;
import net.minecraftborge.loader.math.Vector3fc;

public class GetFogColorEvent extends Event {
	private final Vector3f color, colorOriginal;
	private final Minecraft mc;

	public GetFogColorEvent(Minecraft mc) {
		this.color = new Vector3f();
		this.colorOriginal = new Vector3f();
		this.mc = mc;
	}

	public void prepare(float r, float g, float b) {
		this.color.set(this.colorOriginal.set(r, g, b));
	}

	public Vector3f getColor() {
		return this.color;
	}
	public Vector3fc getColorOriginal() {
		return this.colorOriginal;
	}
	public Minecraft getMC() {
		return this.mc;
	}
	public EntityLiving getCamera() {
		return this.mc.renderViewEntity;
	}
}
