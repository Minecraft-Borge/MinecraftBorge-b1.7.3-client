package net.minecraftborge.loader.capability;

import net.minecraft.src.ItemStack;

public interface IItemHandlerModifiable extends IItemHandler {
	void setStackInSlot(int slot, ItemStack stack);
}
