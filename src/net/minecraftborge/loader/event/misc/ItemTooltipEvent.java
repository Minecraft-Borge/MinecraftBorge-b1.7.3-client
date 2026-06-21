package net.minecraftborge.loader.event.misc;

import net.minecraft.src.ItemStack;
import net.minecraftborge.loader.event.Event;

import java.util.List;

public class ItemTooltipEvent extends Event {
	private ItemStack stack;
	private final List<String> tooltip;

	public ItemTooltipEvent(List<String> tooltip) {
		this.tooltip = tooltip;
	}

	// INTERNAL USE ONLY
	public void setStack(ItemStack stack) {
		this.stack = stack;
	}

	public ItemStack getStack() {
		return this.stack;
	}
	public List<String> getTooltip() {
		return this.tooltip;
	}
}
