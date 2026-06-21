package net.minecraftborge.loader;

import net.minecraft.src.World;

/**
 * Abstraction of any furnace tile entity.
 * @see net.minecraftborge.loader.event.misc.FurnaceBurnTimeEvent
 */
public interface IFurnace {
	World getFurnaceWorld();
	int getFurnaceX();
	int getFurnaceY();
	int getFurnaceZ();
}
