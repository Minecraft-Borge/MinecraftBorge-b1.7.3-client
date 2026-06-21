package net.minecraftborge.loader;

import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RecipeShapelessFix implements IModRecipe {
	private final int sortingIndex;
	private final ItemStack result;
	private final List<Ingredient> inputs;

	public RecipeShapelessFix(int sortingIndex, ItemStack result, List<Ingredient> inputs) {
		this.sortingIndex = sortingIndex;
		this.result = result;
		this.inputs = inputs;
	}
	public RecipeShapelessFix(ItemStack result, List<Ingredient> inputs) {
		this(0, result, inputs);
	}

	@Override
	public boolean matches(InventoryCrafting grid) {
		ArrayList<Ingredient> list = new ArrayList<>(this.inputs);

		for (int y = 0; y < grid.matrixSize(); ++y) {
			for (int x = 0; x < grid.matrixSize(); ++x) {
				ItemStack stack = grid.getStackInGrid(x, y);
				if (stack != null) {
					boolean match = false;

					for (Ingredient in : list) {
						if (in.test(stack)) {
							match = true;
							list.remove(in);
							break;
						}
					}

					if (!match) return false;
				}
			}
		}

		return list.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting grid) {
		return this.result.copy();
	}

	@Override
	public int getRecipeSize() {
		return this.inputs.size();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.result;
	}

	@Override
	public int sortingIndex() {
		return this.sortingIndex;
	}
}
