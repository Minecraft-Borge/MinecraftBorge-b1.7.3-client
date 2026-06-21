package net.minecraftborge.loader.event;

/**
 * Any event listener methods must be annotated with this.
 * @see EventBus
 * @see EventBusSubscriber
 */
public @interface EventHandler {
	/**
	 * @return event priority
	 */
	EventPriority value() default EventPriority.NORMAL;
}
