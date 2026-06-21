package net.minecraftborge.loader;

import net.minecraft.src.ItemDye;

import java.util.Arrays;
import java.util.Locale;

public final class DyeHelper {
	private DyeHelper() {}

	public static final String[] ITEM_NAMES = Arrays.stream(ItemDye.dyeColors)
			.map(s -> s.substring(0, 1).toUpperCase(Locale.ENGLISH) + s.substring(1))
			.toArray(String[]::new);
	public static final String[] COLOR_NAMES = new String[16];

	static {
		for (int i = 0; i < 16; i++) {
			COLOR_NAMES[i] = ITEM_NAMES[15-i];
		}
	}
}
