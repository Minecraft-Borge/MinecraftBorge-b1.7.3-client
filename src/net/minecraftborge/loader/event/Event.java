package net.minecraftborge.loader.event;

/**
 * Base event class, any event that is pushed needs to extend this class in some way.
 * @see EventBus
 * @see EventBusSubscriber
 * @see EventHandler
 */
public abstract class Event {
	private final boolean cancelable = this.getClass().isAnnotationPresent(Cancelable.class);
	private boolean canceled = false;

	public final boolean isCancelable() {
		return this.cancelable;
	}
	public final boolean isCanceled() {
		return this.canceled;
	}

	public final void setCanceled(boolean canceled) {
		if (this.isCancelable() || !canceled) this.canceled = canceled;
	}

	public enum Phase {
		PRE, POST
	}
}
