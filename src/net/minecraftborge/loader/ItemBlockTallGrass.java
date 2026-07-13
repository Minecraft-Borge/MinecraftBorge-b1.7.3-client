package net.minecraftborge.loader;

import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.ItemBlock;

public class ItemBlockTallGrass extends ItemBlock {
	public ItemBlockTallGrass(int blockID) {
		super(blockID);
	}

	@Override
	public int getColorFromDamage(int damage) {
		return ColorizerGrass.getGrassColor(0.5, 0.5);
	}
}
