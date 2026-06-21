package net.minecraftborge.loader;

import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

public class RecipeShapedFix implements IModRecipe {
	private final int sortingIndex;
	private final int width, height;
	private final ItemStack result;
	private final Ingredient[] inputs;

	public RecipeShapedFix(int sortingIndex, int width, int height, ItemStack result, Ingredient[] inputs) {
		this.sortingIndex = sortingIndex;
		this.width = width;
		this.height = height;
		this.result = result;
		this.inputs = inputs;
	}
	public RecipeShapedFix(int width, int height, ItemStack result, Ingredient[] inputs) {
		this(0, width, height, result, inputs);
	}

	@Override
	public boolean matches(InventoryCrafting grid) {
		for (int x = 0; x <= grid.matrixSize() - this.width; ++x) {
			for (int y = 0; y <= grid.matrixSize() - this.height; ++y) {
				if (this.matches(grid, x, y, false)) return true;
				if (this.matches(grid, x, y, true)) return true;
			}
		}

		return false;
	}

	private boolean matches(InventoryCrafting grid, int globalX, int globalY, boolean mirrored) {
		for (int x = 0; x < grid.matrixSize(); ++x) {
			for (int y = 0; y < grid.matrixSize(); ++y) {
				int gridX = x - globalX;
				int gridY = y - globalY;

				Ingredient in = null;
				if (gridX >= 0 && gridY >= 0 && gridX < this.width && gridY < this.height) {
					if (mirrored) {
						in = this.inputs[this.width - gridX	- 1 + gridY * this.width];
					} else {
						in = this.inputs[gridX + gridY * this.width];
					}
				}

				ItemStack stack = grid.getStackInGrid(x, y);
				if (stack != null || in != null) {
					if (stack == null || in == null) return false;

					if (!in.test(stack)) return false;
				}
			}
		}

		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting grid) {
		return this.result.copy();
	}

	@Override
	public int getRecipeSize() {
		return this.width * this.height;
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
