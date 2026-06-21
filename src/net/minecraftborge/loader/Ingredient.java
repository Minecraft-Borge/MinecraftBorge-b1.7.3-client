package net.minecraftborge.loader;

import net.minecraft.src.ItemStack;
import net.minecraftborge.loader.tag.ItemTags;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * The Ingredient is used to represent an item in a recipe, such as crafting ingredients, that may have dynamic correct options.
 */
public abstract class Ingredient implements Predicate<ItemStack> {
	public abstract List<ItemStack> getDisplayItems();

	public static Ingredient custom(Predicate<ItemStack> predicate, ItemStack icon) {
		return new Ingredient.Custom(predicate, icon);
	}
	public static Ingredient custom(Predicate<ItemStack> predicate) {
		return custom(predicate, null);
	}

	public static Ingredient empty() {
		return Ingredient.Empty.INSTANCE;
	}
	public static Ingredient of(int itemID) {
		return new Ingredient.ById(itemID);
	}
	public static Ingredient of(int itemID, int damage) {
		return new Ingredient.ByIdAndDamage(itemID, damage);
	}
	public static Ingredient of(ItemStack stack) {
		if (stack.getItemDamage() == -1) return of(stack.itemID);
		return of(stack.itemID, stack.getItemDamage());
	}
	public static Ingredient of(String tag) {
		return new Ingredient.ByTag(tag);
	}

	private static class Empty extends Ingredient {
		private static final Empty INSTANCE = new Empty();
		private Empty() {}

		@Override
		public List<ItemStack> getDisplayItems() {
			return Collections.emptyList();
		}
		@Override
		public boolean test(ItemStack stack) {
			return stack == null;
		}
	}
	private static class Custom extends Ingredient {
		private final Predicate<ItemStack> predicate;
		private final ItemStack icon;

		private Custom(Predicate<ItemStack> predicate, ItemStack icon) {
			this.predicate = predicate;
			this.icon = icon;
		}

		@Override
		public List<ItemStack> getDisplayItems() {
			return Collections.singletonList(this.icon);
		}

		@Override
		public boolean test(ItemStack stack) {
			return this.predicate.test(stack);
		}
	}

	private static class ById extends Ingredient {
		private final ItemStack icon;
		private final int itemID;
		private ById(int itemID) {
			this.icon = new ItemStack(itemID, 1, 0);
			this.itemID = itemID;
		}

		@Override
		public List<ItemStack> getDisplayItems() {
			return Collections.singletonList(this.icon);
		}

		@Override
		public boolean test(ItemStack stack) {
			return stack != null && stack.itemID == this.itemID;
		}
	}
	private static class ByIdAndDamage extends Ingredient {
		private final ItemStack example;
		private ByIdAndDamage(int itemID, int damage) {
			this.example = new ItemStack(itemID, 1, damage);
		}

		@Override
		public List<ItemStack> getDisplayItems() {
			return Collections.singletonList(this.example);
		}

		@Override
		public boolean test(ItemStack stack) {
			return stack != null && stack.itemID == this.example.itemID && stack.getItemDamage() == this.example.getItemDamage();
		}
	}
	private static class ByTag extends Ingredient {
		private final String tag;
		private ByTag(String tag) {
			this.tag = tag;
		}

		@Override
		public List<ItemStack> getDisplayItems() {
			return ItemTags.getTagged(this.tag);
		}

		@Override
		public boolean test(ItemStack stack) {
			List<ItemStack> examples = ItemTags.getTagged(this.tag);
			for (ItemStack example : examples) {
				if (ItemTags.matches(example, stack)) return true;
			}
			return false;
		}
	}
}
