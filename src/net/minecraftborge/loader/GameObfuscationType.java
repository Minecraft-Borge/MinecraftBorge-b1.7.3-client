package net.minecraftborge.loader;

import net.minecraft.src.EnumToolMaterial;

/**
 * Detects the obfuscation in the game environment.
 */
public enum GameObfuscationType {
	NONE, DEFAULT, UNKNOWN;

	public static final String UNOBFUSCATED_TOOL_MATERIAL_NAME = "net/minecraft/src/EnumToolMaterial";

	public static GameObfuscationType detect() {
		String name = EnumToolMaterial.class.getName();
		switch (name.replace('.', '/')) {
			case UNOBFUSCATED_TOOL_MATERIAL_NAME:
				return NONE;
			case "bu":
				return DEFAULT;
			default:
				return UNKNOWN;
		}
	}
}
