package net.minecraftborge.loader.event.misc;

import net.minecraft.src.StatBase;
import net.minecraftborge.loader.event.Event;

public class ReplaceSimilarBlocksEvent extends Event {
	private final StatBase[] stats;
	private final Replace func;
	public ReplaceSimilarBlocksEvent(StatBase[] stats, Replace func) {
		this.stats = stats;
		this.func = func;
	}

	public void replace(int from, int to) {
		this.func.replace(this.stats, from, to);
	}

	@FunctionalInterface
	public interface Replace {
		void replace(StatBase[] stats, int from, int to);
	}
}
