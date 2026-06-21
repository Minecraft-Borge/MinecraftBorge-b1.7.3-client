package net.minecraftborge.loader.event;

/**
 * Any class containing event listener methods must be annotated with this.
 */
public @interface EventBusSubscriber {
	/**
	 * @return mod id
	 */
	String value() default "";
}
