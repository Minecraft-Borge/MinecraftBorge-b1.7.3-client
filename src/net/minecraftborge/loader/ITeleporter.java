package net.minecraftborge.loader;

import net.minecraft.src.Entity;
import net.minecraft.src.World;

public interface ITeleporter {
	void doTeleport(World world, Entity entity);
}
