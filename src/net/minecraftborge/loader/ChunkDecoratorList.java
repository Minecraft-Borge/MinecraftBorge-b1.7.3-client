package net.minecraftborge.loader;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.World;

import java.util.*;

public class ChunkDecoratorList {
	public static final int PRE = 0;
	public static final int LAKES = 1;
	public static final int SOIL = 2;
	public static final int ORES = 3;
	public static final int TREES = 4;
	public static final int PLANTS = 5;
	public static final int WATERFALLS = 6;
	public static final int POST = 7;

	private final Map<IChunkDecorator, Integer> decoratorWeights = new HashMap<>();
	private final List<IChunkDecorator> sortedDecorators = new ArrayList<>();

	public void addDecorator(IChunkDecorator decorator, int weight) {
		this.decoratorWeights.put(decorator, weight);
		this.sortedDecorators.add(decorator);

		this.sortedDecorators.sort((d1, d2) -> Integer.compare(this.decoratorWeights.get(d1), this.decoratorWeights.get(d2)));
	}
	public void iterate(World world, int chunkX, int chunkZ, BiomeGenBase biome, Random random) {
		for (IChunkDecorator decorator : this.sortedDecorators) {
			decorator.decorate(world, chunkX, chunkZ, biome, random);
		}
	}

	public static ChunkDecoratorList[] createDecoratorArray() {
		return new ChunkDecoratorList[]{
				new ChunkDecoratorList(),
				new ChunkDecoratorList(),
				new ChunkDecoratorList(),
				new ChunkDecoratorList(),
				new ChunkDecoratorList(),
				new ChunkDecoratorList(),
				new ChunkDecoratorList(),
				new ChunkDecoratorList(),
		};
	}
}
