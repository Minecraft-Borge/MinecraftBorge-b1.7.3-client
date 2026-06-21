package net.minecraftborge.loader.capability;

import net.minecraft.src.ItemStack;

public interface IItemHandler {
	int getSlots();
	ItemStack getStackInSlot(int slot);
	ItemStack insertItem(int slot, ItemStack stack, boolean simulate);
	ItemStack extractItem(int slot, int amount, boolean simulate);
	int getMaxStackSize(int slot);
	default boolean isItemValid(int slot, ItemStack stack) {
		return true;
	}
}
