package net.minecraftborge.loader.event.register;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraftborge.loader.event.Event;

import java.util.function.IntFunction;

public abstract class IdAllocationEvent<T> extends Event {
	protected IdAllocationEvent() {

	}

	public abstract int getNextFreeId();
	public <R extends T> R createWithFreeId(IntFunction<R> sup) {
		return sup.apply(this.getNextFreeId());
	}

	public static final class Blocks extends IdAllocationEvent<Block> {
		public Blocks() {

		}

		@Override
		public int getNextFreeId() {
			for (int i = 1; i < Block.blocksList.length; i++) {
				if (Block.blocksList[i] == null) return i;
			}
			throw new RuntimeException("Ran out of block IDs!");
		}
	}

	public static final class Items extends IdAllocationEvent<Item> {
		public Items() {

		}

		@Override
		public int getNextFreeId() {
			for (int i = Block.ID_SIZE; i < Item.itemsList.length; i++) {
				if (Item.itemsList[i] == null) return i - Block.ID_SIZE;
			}
			throw new RuntimeException("Ran out of item IDs!");
		}
	}
}
