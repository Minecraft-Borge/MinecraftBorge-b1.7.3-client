package net.minecraftborge.loader;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.World;

import java.util.Random;

/**
 * Allows mods to insert their own chunk decoration for worldgen.
 */
@FunctionalInterface
public interface IChunkDecorator {
	void decorate(World world, int chunkX, int chunkZ, BiomeGenBase biome, Random random);
}
