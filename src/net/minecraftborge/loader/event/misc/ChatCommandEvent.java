package net.minecraftborge.loader.event.misc;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiIngame;
import net.minecraftborge.loader.event.Event;

public class ChatCommandEvent extends Event {
	private final EntityPlayer sender;
	private final String command;
	private final GuiIngame ingameGUI;

	public ChatCommandEvent(EntityPlayer sender, String command, GuiIngame ingameGUI) {
		this.sender = sender;
		this.command = command;
		this.ingameGUI = ingameGUI;
	}

	public EntityPlayer getSender() {
		return this.sender;
	}
	public String getCommand() {
		return this.command;
	}
	public void sendStatus(String message) {
		this.ingameGUI.addChatMessage(message);
	}
}
