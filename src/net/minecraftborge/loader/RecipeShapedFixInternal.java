package net.minecraftborge.loader;

import net.minecraft.src.ItemStack;

public class RecipeShapedFixInternal extends RecipeShapedFix {
	public RecipeShapedFixInternal(int sortingIndex, int width, int height, ItemStack result, Ingredient[] inputs) {
		super(sortingIndex, width, height, result, inputs);
	}

	public RecipeShapedFixInternal(int width, int height, ItemStack result, Ingredient[] inputs) {
		super(width, height, result, inputs);
	}

	@Override
	public boolean isInternalRecipeFix() {
		return true;
	}
}
