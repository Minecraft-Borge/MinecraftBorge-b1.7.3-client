package net.minecraftborge.loader.event.render;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import net.minecraftborge.loader.event.Cancelable;
import net.minecraftborge.loader.event.Event;

@Cancelable
public class RenderRainSnowEvent extends Event {
	private float partialTick;
	private float rainStrength;

	private final Minecraft mc;
	public RenderRainSnowEvent(Minecraft mc) {
		this.mc = mc;
	}

	public void prepare(float partialTick, float rainStrength) {
		this.partialTick = partialTick;
		this.rainStrength = rainStrength;
	}

	public float getPartialTick() {
		return this.partialTick;
	}
	public float getRainStrength() {
		return this.rainStrength;
	}

	public Minecraft getMC() {
		return this.mc;
	}
	public EntityLiving getCamera() {
		return this.mc.renderViewEntity;
	}
	public World getWorld() {
		return this.mc.theWorld;
	}
}
