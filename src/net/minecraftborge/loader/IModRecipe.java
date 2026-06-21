package net.minecraftborge.loader;

import net.minecraft.src.IRecipe;

public interface IModRecipe extends IRecipe {
	default int sortingIndex() {
		return 0;
	}
}
