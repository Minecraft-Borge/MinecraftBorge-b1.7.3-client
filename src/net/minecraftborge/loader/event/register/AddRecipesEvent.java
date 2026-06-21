package net.minecraftborge.loader.event.register;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftborge.loader.IModRecipe;
import net.minecraftborge.loader.Ingredient;
import net.minecraftborge.loader.RecipeShapedFix;
import net.minecraftborge.loader.RecipeShapelessFix;
import net.minecraftborge.loader.event.Event;

import java.util.ArrayList;
import java.util.HashMap;

public class AddRecipesEvent extends Event {
	private final CraftingManager manager;

	public AddRecipesEvent(CraftingManager manager) {
		this.manager = manager;
	}

	public void addRecipe(IModRecipe recipe) {
		this.manager.addRecipe(recipe);
	}

	public void addShapedRecipe(int sortingIndex, ItemStack result, Object... inputs) {
		StringBuilder keys = new StringBuilder();
		int peek = 0;
		int w = 0;
		int h = 0;

		if (inputs[peek] instanceof String[]) {
			String[] array = (String[]) inputs[peek++];

			for (String row : array) {
				++h;
				w = row.length();
				keys.append(row);
			}
		} else {
			while (inputs[peek] instanceof String) {
				String row = (String) inputs[peek++];
				++h;
				w = row.length();
				keys.append(row);
			}
		}

		HashMap<Character, Ingredient> pattern;
		for (pattern = new HashMap<>(); peek < inputs.length; peek++) {
			Character key = (Character) inputs[peek++];
			Ingredient in = null;

			if (inputs[peek] instanceof Item) {
				in = Ingredient.of(((Item)inputs[peek]).shiftedIndex);
			} else if (inputs[peek] instanceof Block) {
				in = Ingredient.of(((Block)inputs[peek]).blockID);
			} else if (inputs[peek] instanceof ItemStack) {
				in = Ingredient.of((ItemStack) inputs[peek]);
			} else if (inputs[peek] instanceof Ingredient) {
				in = (Ingredient) inputs[peek];
			} else {
				throw new IllegalArgumentException("Invalid shaped recipe parameter type: " + inputs[peek].getClass().getSimpleName());
			}

			pattern.put(key, in);
		}

		Ingredient[] grid = new Ingredient[w * h];
		for (int i = 0; i < w * h; i++) {
			char key = keys.charAt(i);
			grid[i] = pattern.getOrDefault(key, null);
		}

		this.addRecipe(new RecipeShapedFix(sortingIndex, w, h, result, grid));
	}
	public void addShapedRecipe(ItemStack result, Object... inputs) {
		this.addShapedRecipe(0, result, inputs);
	}

	public void addShapelessRecipe(int sortingIndex, ItemStack result, Object... inputs) {
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		for (Object in : inputs) {
			if (in instanceof Item) {
				ingredients.add(Ingredient.of(((Item)in).shiftedIndex));
			} else if (in instanceof Block) {
				ingredients.add(Ingredient.of(((Block)in).blockID));
			} else if (in instanceof ItemStack) {
				ingredients.add(Ingredient.of((ItemStack) in));
			} else if (in instanceof Ingredient) {
				ingredients.add((Ingredient) in);
			} else {
				throw new IllegalArgumentException("Invalid shapeless recipe parameter type: " + in.getClass().getSimpleName());
			}
		}

		this.addRecipe(new RecipeShapelessFix(sortingIndex, result, ingredients));
	}
	public void addShapelessRecipe(ItemStack result, Object... inputs) {
		this.addShapelessRecipe(0, result, inputs);
	}
}
