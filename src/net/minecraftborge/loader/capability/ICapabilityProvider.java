package net.minecraftborge.loader.capability;

public interface ICapabilityProvider<C> {
	boolean hasCapability(Capability<?> capability, C context);

	<T> T getCapability(Capability<T> capability, C context);
}
