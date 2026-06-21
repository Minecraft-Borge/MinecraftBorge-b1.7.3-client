package net.minecraftborge.loader.event.misc;

import net.minecraftborge.loader.event.Cancelable;
import net.minecraftborge.loader.event.Event;

@Cancelable
public class ChatMessageEvent extends Event {
	private String message;

	public ChatMessageEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
