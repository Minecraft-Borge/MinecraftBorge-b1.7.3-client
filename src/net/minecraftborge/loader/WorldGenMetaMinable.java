package net.minecraftborge.loader;

import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import java.util.Random;

public class WorldGenMetaMinable extends WorldGenerator {
	private final int minableBlockId;
	private final int minableMetadata;
	private final int numberOfBlocks;
	private final int groundBlock;

	public WorldGenMetaMinable(int blockID, int meta, int count, int replace) {
		this.minableBlockId = blockID;
		this.minableMetadata = meta;
		this.numberOfBlocks = count;
		this.groundBlock = replace;
	}
	public WorldGenMetaMinable(int blockID, int meta, int count) {
		this(blockID, meta, count, Block.stone.blockID);
	}

	public boolean generate(World world, Random random, int x, int y, int z) {
		float var6 = random.nextFloat() * (float)Math.PI;
		double var7 = (float)(x + 8) + MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0F;
		double var9 = (float)(x + 8) - MathHelper.sin(var6) * (float)this.numberOfBlocks / 8.0F;
		double var11 = (float)(z + 8) + MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0F;
		double var13 = (float)(z + 8) - MathHelper.cos(var6) * (float)this.numberOfBlocks / 8.0F;
		double var15 = y + random.nextInt(3) + 2;
		double var17 = y + random.nextInt(3) + 2;

		for(int var19 = 0; var19 <= this.numberOfBlocks; ++var19) {
			double var20 = var7 + (var9 - var7) * (double)var19 / (double)this.numberOfBlocks;
			double var22 = var15 + (var17 - var15) * (double)var19 / (double)this.numberOfBlocks;
			double var24 = var11 + (var13 - var11) * (double)var19 / (double)this.numberOfBlocks;
			double var26 = random.nextDouble() * (double)this.numberOfBlocks / 16.0D;
			double var28 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
			double var30 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
			int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
			int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
			int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
			int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
			int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
			int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);

			for(int oreX = var32; oreX <= var35; ++oreX) {
				double var39 = ((double)oreX + 0.5D - var20) / (var28 / 2.0D);
				if(var39 * var39 < 1.0D) {
					for(int oreY = var33; oreY <= var36; ++oreY) {
						double var42 = ((double)oreY + 0.5D - var22) / (var30 / 2.0D);
						if(var39 * var39 + var42 * var42 < 1.0D) {
							for(int oreZ = var34; oreZ <= var37; ++oreZ) {
								double var45 = ((double)oreZ + 0.5D - var24) / (var28 / 2.0D);
								if(var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && world.getBlockId(oreX, oreY, oreZ) == this.groundBlock) {
									world.setBlockAndMetadata(oreX, oreY, oreZ, this.minableBlockId, this.minableMetadata);
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
}
