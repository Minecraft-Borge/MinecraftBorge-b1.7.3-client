package net.minecraftborge.loader.event.misc;

import net.minecraft.src.ItemStack;
import net.minecraftborge.loader.IFurnace;
import net.minecraftborge.loader.event.Cancelable;
import net.minecraftborge.loader.event.Event;

@Cancelable
public class FurnaceBurnTimeEvent extends Event {
	private final IFurnace te;
	private final ItemStack stack;
	private int burnTime;
	public FurnaceBurnTimeEvent(IFurnace te, ItemStack stack, int burnTime) {
		this.te = te;
		this.stack = stack;
		this.burnTime = burnTime;
	}

	public IFurnace getTileEntityFurnace() {
		return this.te;
	}
	public ItemStack getStack() {
		return this.stack;
	}
	public int getBurnTime() {
		return this.burnTime;
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}
}
