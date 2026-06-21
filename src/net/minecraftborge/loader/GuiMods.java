package net.minecraftborge.loader;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.Sys;

import java.io.File;

public class GuiMods extends GuiScreen {
	protected GuiScreen parent;
	private String fileLocation = "";
	private GuiModsSlot list;

	public GuiMods(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void initGui() {
		StringTranslate translate = StringTranslate.getInstance();
		this.controlList.add(new GuiSmallButton(5, this.width / 2 - 154, this.height - 48, translate.translateKey("borge.mods.openFolder")));
		this.controlList.add(new GuiSmallButton(6, this.width / 2 + 4, this.height - 48, translate.translateKey("gui.done")));
		this.fileLocation = (new File(Minecraft.getMinecraftDir(), "mods")).getAbsolutePath();
		this.list = new GuiModsSlot(this);
		this.list.registerScrollButtons(this.controlList, 7, 8);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 5) {
				Sys.openURL("file://" + this.fileLocation);
			} else if (button.id == 6) {
				this.mc.displayGuiScreen(this.parent);
			} else {
				this.list.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		this.list.drawScreen(mouseX, mouseY, partialTick);

		StringTranslate translate = StringTranslate.getInstance();
		this.drawCenteredString(this.fontRenderer, translate.translateKey("borge.mods.title"), this.width / 2, 16, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, translate.translateKey("borge.mods.folderInfo"), this.width / 2 - 77, this.height - 26, 0x808080);
		super.drawScreen(mouseX, mouseY, partialTick);
	}

	static Minecraft getMinecraft(GuiMods gui) {
		return gui.mc;
	}
	static FontRenderer getFont(GuiMods gui) {
		return gui.fontRenderer;
	}
}
