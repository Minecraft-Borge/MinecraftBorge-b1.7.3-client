package net.minecraftborge.loader;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiContainer;

/**
 * A GUI factory, used to allow the server to remotely open mod GUIs.
 */
@FunctionalInterface
public interface IModGUIFactory {
	GuiContainer createGUI(int id, EntityPlayer player, byte[] extraData);
}
