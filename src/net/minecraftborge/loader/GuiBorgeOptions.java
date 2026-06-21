package net.minecraftborge.loader;

import net.minecraft.src.*;
import net.minecraftborge.MinecraftBorge;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiBorgeOptions extends GuiScreen {
	private final GuiScreen parent;
	private final GameSettings settings;
	private String title = "null";
	private String disclaimer = "null";

	private static final EnumBorgeOptions[] OPTIONS = new EnumBorgeOptions[]{
			EnumBorgeOptions.MODIFY_MAIN_MENU,
			EnumBorgeOptions.PERSISTENT_BLOCK_UPDATES,
			EnumBorgeOptions.FIX_FURNACE_FUEL_CONSUMPTION,
			EnumBorgeOptions.FIX_AMBIENT_OCCLUSION,
	};
	private final Object[] states = new Object[]{
			MinecraftBorge.blackboard.getOrDefault(EnumBorgeOptions.MODIFY_MAIN_MENU.getActualString(), true),
			MinecraftBorge.blackboard.getOrDefault(EnumBorgeOptions.PERSISTENT_BLOCK_UPDATES.getActualString(), true),
			MinecraftBorge.blackboard.getOrDefault(EnumBorgeOptions.FIX_FURNACE_FUEL_CONSUMPTION.getActualString(), true),
			MinecraftBorge.blackboard.getOrDefault(EnumBorgeOptions.FIX_AMBIENT_OCCLUSION.getActualString(), true),
	};
	private final List<List<String>> descriptions = new ArrayList<>();

	public GuiBorgeOptions(GuiScreen parent, GameSettings settings) {
		this.parent = parent;
		this.settings = settings;

		this.initDescriptions();
	}

	@Override
	public void initGui() {
		StringTranslate translate = StringTranslate.getInstance();

		this.title = translate.translateKey("borge.options.title");
		this.disclaimer = translate.translateKey("borge.options.disclaimer");

		int count = 0;
		EnumBorgeOptions[] options = OPTIONS;
		int size = options.length;

		for (int i = 0; i < size; i++) {
			EnumBorgeOptions entry = options[i];
			if (!entry.getEnumFloat()) {
				this.controlList.add(new GuiSmallButton(entry.ordinal(), this.width / 2 - 155 + count % 2 * 160, this.height / 6 + 24 * (count >> 1),
						translate.translateKeyOr(entry.getEnumString(), entry.getActualString()) + ": " + ((boolean)this.states[entry.ordinal()] ? translate.translateKey("gui.yes") : translate.translateKey("gui.no"))));
			}

			count++;
		}

		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 160, translate.translateKey("gui.done")));
	}

	private void initDescriptions() {
		for (EnumBorgeOptions options : OPTIONS) {
			List<String> list = new ArrayList<>();
			switch (options) {
				case MODIFY_MAIN_MENU:
					list.add("Some people do not want their menu littered with");
					list.add("'modified buttons and the like'.");
					list.add("This option exists for the select few who wish");
					list.add("to make their main menu look unmodded.");
					break;
				case PERSISTENT_BLOCK_UPDATES:
					list.add("Scheduled block updates are discarded");
					list.add("between world reloads.");
					list.add("This may break some mod functionality,");
					list.add("so this patch introduces a fix");
					list.add("that allows scheduled ticks to exist");
					list.add("in between reloads.");
					break;
				case FIX_FURNACE_FUEL_CONSUMPTION:
					list.add("The furnace, by default, consumes the bucket too");
					list.add("when a lava bucket is used as fuel.");
					list.add("This is because it disregards the container system.");
					list.add("Though not that much of an issue with lava,");
					list.add("it may be an actual issue with modded content.");
					list.add("This patch fixes it.");
					break;
				case FIX_AMBIENT_OCCLUSION:
					list.add("When a block model is smaller");
					list.add("than a full block, the AO algorithm");
					list.add("does not account for this.");
					list.add("This bug created strange shading on certain");
					list.add("blocks, such as snow layers.");
					list.add("This patch changes the algorithm to also take");
					list.add("model size into account.");
					break;
			}
			this.descriptions.add(list);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id < 100 && button instanceof GuiSmallButton) {
				EnumBorgeOptions options = EnumBorgeOptions.get(button.id);
				if (options != null) {
					if (options.getEnumBoolean()) {
						StringTranslate translate = StringTranslate.getInstance();
						this.states[options.ordinal()] = !(boolean)this.states[options.ordinal()];
						button.displayString =  translate.translateKeyOr(options.getEnumString(), options.getActualString()) + ": "
								+ ((boolean)this.states[options.ordinal()] ? translate.translateKey("gui.yes") : translate.translateKey("gui.no"));
					}
				}
			}

			if (button.id == 200) {
				this.mc.gameSettings.saveOptions();
				this.saveBorgeOptions();
				this.mc.displayGuiScreen(this.parent);
			}
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, this.disclaimer, this.width / 2, this.height / 6 + 185, 0x808080);
		super.drawScreen(mouseX, mouseY, partialTick);
		for (GuiButton button : this.controlList) {
			if (button.id < 100 && button.isMouseOver(mouseX, mouseY)) {
				EnumBorgeOptions options = EnumBorgeOptions.get(button.id);
				if (options != null) {
					drawTooltipWithGradientBackdrop(
							this, this.fontRenderer, button.xPosition + 3, button.yPosition + button.getHeight() + 3 + 3,
							options.getActualString(), this.descriptions.get(button.id)
					);
				}
			}
		}
	}

	@SuppressWarnings("all") // i do not care
	private void saveBorgeOptions() {
		try {
			File file = new File("BorgeConfig.txt");
			if (file.exists()) file.delete();
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			for (int i = 0; i < OPTIONS.length; i++) {
				EnumBorgeOptions key = OPTIONS[i];
				Object value = this.states[i];
				writer.write(key.getActualString() + "=" + value.toString() + "\n");
			}
			writer.close();
		} catch (Exception e) {

		}
	}
}
