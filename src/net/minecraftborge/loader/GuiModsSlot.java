package net.minecraftborge.loader;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Tessellator;
import net.minecraftborge.MinecraftBorge;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Constructor;

public class GuiModsSlot extends GuiSlot {
	final GuiMods parent;

	public GuiModsSlot(GuiMods parent) {
		super(GuiMods.getMinecraft(parent), parent.width, parent.height, 32, parent.height - 55 + 4, 36);
		this.parent = parent;
	}

	@Override
	protected int getSize() {
		return ModList.get().size() + 2;
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		if (doubleClick && index >= 2) {
			ModContainer container = ModList.get().getModContainer(index - 2);
			Class<? extends GuiScreen> clazz = container.modGuiClass;
			if (clazz != null) {
				GuiScreen screen = null;
				try {
					Constructor<? extends GuiScreen> ctr = clazz.getConstructor(GuiScreen.class);
					screen = ctr.newInstance(this.parent);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (screen != null) {
					GuiMods.getMinecraft(this.parent).displayGuiScreen(screen);
				}
			}
		}
	}

	@Override
	protected boolean isSelected(int index) {
		return false;
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 36;
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int index, int x, int y, int height, Tessellator tes) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		switch (index) {
			case 0:
				this.drawMinecraftSlot(x, y, height, tes);
				break;
			case 1:
				this.drawBorgeSlot(x, y, height, tes);
				break;
			default:
				ModContainer container = ModList.get().getModContainer(index - 2);
				int texture = GuiMods.getMinecraft(this.parent).renderEngine.getTexture("mod_logo/" + container.modid + ".png");
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
				tes.startDrawingQuads();
				tes.setColorOpaque_I(-1);
				tes.addVertexWithUV(x, y + height, 0.0D, 0.0D, 1.0D);
				tes.addVertexWithUV(x + 32, y + height, 0.0D, 1.0D, 1.0D);
				tes.addVertexWithUV(x + 32, y, 0.0D, 1.0D, 0.0D);
				tes.addVertexWithUV(x, y, 0.0D, 0.0D, 0.0D);
				tes.draw();
				FontRenderer font = GuiMods.getFont(this.parent);
				this.parent.drawString(font, container.name + " (" + container.modid + ")", x + 32 + 2, y + 1, 0xFFFFFF);
				this.parent.drawString(font, container.description, x + 32 + 2, y + 12, 0x808080);
				this.parent.drawString(font, container.version, x + 32+ 2, y + 12 + 10, 0x808080);
				break;
		}
	}

	protected void drawMinecraftSlot(int x, int y, int height, Tessellator tes) {
		int texture = GuiMods.getMinecraft(this.parent).renderEngine.getTexture("assets/terrain/grass.png");
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		tes.startDrawingQuads();
		tes.setColorOpaque_I(-1);
		tes.addVertexWithUV(x, y + height, 0.0D, 0.0D, 1.0D);
		tes.addVertexWithUV(x + 32, y + height, 0.0D, 1.0D, 1.0D);
		tes.addVertexWithUV(x + 32, y, 0.0D, 1.0D, 0.0D);
		tes.addVertexWithUV(x, y, 0.0D, 0.0D, 0.0D);
		tes.draw();
		FontRenderer font = GuiMods.getFont(this.parent);
		this.parent.drawString(font, "Minecraft (minecraft)", x + 32 + 2, y + 1, 0xFFFFFF);
		this.parent.drawString(font, "The base game we all know and love", x + 32 + 2, y + 12, 0x808080);
		this.parent.drawString(font, MinecraftBorge.MC_VERSION, x + 32+ 2, y + 12 + 10, 0x808080);
	}
	protected void drawBorgeSlot(int x, int y, int height, Tessellator tes) {
		int texture = GuiMods.getMinecraft(this.parent).renderEngine.getTexture("borge_icon.png");
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		tes.startDrawingQuads();
		tes.setColorOpaque_I(-1);
		tes.addVertexWithUV(x, y + height, 0.0D, 0.0D, 1.0D);
		tes.addVertexWithUV(x + 32, y + height, 0.0D, 1.0D, 1.0D);
		tes.addVertexWithUV(x + 32, y, 0.0D, 1.0D, 0.0D);
		tes.addVertexWithUV(x, y, 0.0D, 0.0D, 0.0D);
		tes.draw();
		FontRenderer font = GuiMods.getFont(this.parent);
		this.parent.drawString(font, "MinecraftBorge (borge)", x + 32 + 2, y + 1, 0xFFFFFF);
		this.parent.drawString(font, "Drag-and-drop mod loader", x + 32 + 2, y + 12, 0x808080);
		this.parent.drawString(font, MinecraftBorge.VERSION, x + 32+ 2, y + 12 + 10, 0x808080);
	}
}
