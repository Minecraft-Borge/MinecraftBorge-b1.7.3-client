package net.minecraftborge.loader;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntityFurnace;
import net.minecraftborge.loader.capability.IItemHandler;
import net.minecraftborge.loader.capability.IItemHandlerModifiable;

/**
 * Contains the various ItemHandlers for the TileEntityFurnace
 */
public abstract class ItemHandlerFurnace implements IItemHandler {
	protected final TileEntityFurnace furnace;
	protected ItemHandlerFurnace(TileEntityFurnace te) {
		this.furnace = te;
	}

	public static class Internal extends ItemHandlerFurnace {
		public Internal(TileEntityFurnace te) {
			super(te);
		}

		@Override
		public int getSlots() {
			return 3;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return this.furnace.getStackInSlot(slot);
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return stack;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return null;
		}

		@Override
		public int getMaxStackSize(int slot) {
			return 64;
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			switch (slot) {
				case 0:
					return FurnaceRecipesFix.smelting().getSmeltingResult(stack) != null;
				case 1:
					return TileEntityFurnace.getItemBurnTime(this.furnace, stack) > 0;
				default:
					return false;
			}
		}
	}
	public static class Fuel extends ItemHandlerFurnace implements IItemHandlerModifiable {
		public Fuel(TileEntityFurnace te) {
			super(te);
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack) {
			this.furnace.setInventorySlotContents(1, stack);
			this.furnace.onInventoryChanged();
		}

		@Override
		public int getSlots() {
			return 1;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return this.furnace.getStackInSlot(1);
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (stack == null || stack.stackSize <= 0) return null;
			if (!this.isItemValid(1, stack)) return stack;
			ItemStack original = this.getStackInSlot(1);
			if (original == null || original.stackSize <= 0) {
				if (!simulate) this.setStackInSlot(1, stack.copy());
				return null;
			}
			if (!original.isItemEqual(stack)) return stack;
			int space = Math.min(this.getMaxStackSize(1), original.getMaxStackSize()) - original.stackSize;
			if (space >= stack.stackSize) {
				if (!simulate) {
					original.stackSize += stack.stackSize;
					this.setStackInSlot(1, original);
				}
				return null;
			}
			ItemStack copy = stack.copy();
			copy.stackSize -= space;
			if (!simulate) {
				original.stackSize += space;
				this.setStackInSlot(1, original);
			}
			return copy;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return null;
		}

		@Override
		public int getMaxStackSize(int slot) {
			return 64;
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return TileEntityFurnace.getItemBurnTime(this.furnace, stack) > 0;
		}
	}
	public static class Input extends ItemHandlerFurnace implements IItemHandlerModifiable {
		public Input(TileEntityFurnace te) {
			super(te);
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack) {
			this.furnace.setInventorySlotContents(0, stack);
		}

		@Override
		public int getSlots() {
			return 1;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return this.furnace.getStackInSlot(0);
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (stack == null || stack.stackSize <= 0) return null;
			if (!this.isItemValid(0, stack)) return stack;
			ItemStack original = this.getStackInSlot(1);
			if (original == null || original.stackSize <= 0) {
				if (!simulate) this.setStackInSlot(0, stack.copy());
				return null;
			}
			if (!original.isItemEqual(stack)) return stack;
			int space = Math.min(this.getMaxStackSize(0), original.getMaxStackSize()) - original.stackSize;
			if (space >= stack.stackSize) {
				if (!simulate) {
					original.stackSize += stack.stackSize;
					this.setStackInSlot(0, original);
				}
				return null;
			}
			ItemStack copy = stack.copy();
			copy.stackSize -= space;
			if (!simulate) {
				original.stackSize += space;
				this.setStackInSlot(1, original);
			}
			return copy;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return null;
		}

		@Override
		public int getMaxStackSize(int slot) {
			return 64;
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return FurnaceRecipesFix.smelting().getSmeltingResult(stack) != null;
		}
	}
	public static class Result extends ItemHandlerFurnace implements IItemHandlerModifiable {
		public Result(TileEntityFurnace te) {
			super(te);
		}

		@Override
		public void setStackInSlot(int slot, ItemStack stack) {
			this.furnace.setInventorySlotContents(2, stack);
		}

		@Override
		public int getSlots() {
			return 1;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return this.furnace.getStackInSlot(2);
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return stack;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if (amount <= 0) return null;
			ItemStack original = this.getStackInSlot(2);
			if (original == null || original.stackSize <= 0) return null;
			if (amount >= original.stackSize) {
				if (!simulate) {
					this.setStackInSlot(2, null);
					return original;
				}
				return original.copy();
			}
			if (simulate) {
				ItemStack copy = original.copy();
				return copy.splitStack(amount);
			} else {
				ItemStack copy = original.splitStack(amount);
				this.setStackInSlot(2, original);
				return copy;
			}
		}

		@Override
		public int getMaxStackSize(int slot) {
			return 64;
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return false;
		}
	}
}
