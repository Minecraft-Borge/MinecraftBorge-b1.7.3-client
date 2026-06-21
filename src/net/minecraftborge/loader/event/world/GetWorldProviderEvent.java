package net.minecraftborge.loader.event.world;

import net.minecraft.src.WorldProvider;
import net.minecraftborge.loader.event.Event;

import java.util.Objects;

public class GetWorldProviderEvent extends Event {
	private final int dimensionID;
	private WorldProvider provider = null;

	public GetWorldProviderEvent(int dimensionID) {
		this.dimensionID = dimensionID;
	}

	public int getDimensionID() {
		return this.dimensionID;
	}

	public WorldProvider getProvider() {
		return this.provider;
	}
	public void setProvider(WorldProvider provider) {
		Objects.requireNonNull(provider, "World provider cannot be null!");
		if (this.provider != null) {
			System.out.println("WARNING: World provider got overridden (Dim ID: " + this.dimensionID + ")");
			System.out.println("Previous provider: " + this.provider.getClass().getSimpleName());
			System.out.println("New provider: " + provider.getClass().getSimpleName());
		}
		this.provider = provider;
	}
}
