package net.minecraftborge.loader;

import net.minecraft.src.ItemStack;

import java.util.List;

public class RecipeShapelessFixInternal extends RecipeShapelessFix {
	public RecipeShapelessFixInternal(int sortingIndex, ItemStack result, List<Ingredient> inputs) {
		super(sortingIndex, result, inputs);
	}

	public RecipeShapelessFixInternal(ItemStack result, List<Ingredient> inputs) {
		super(result, inputs);
	}

	@Override
	public boolean isInternalRecipeFix() {
		return true;
	}
}
