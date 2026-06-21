package net.minecraftborge.loader;

public enum EnumBorgeOptions {
	MODIFY_MAIN_MENU("ModifyMainMenu", false, true),
	PERSISTENT_BLOCK_UPDATES("PersistentBlockUpdates", false, true),
	FIX_FURNACE_FUEL_CONSUMPTION("FixFurnaceFuelConsumption", false, true),
	FIX_AMBIENT_OCCLUSION("FixAmbientOcclusion", false, true);

	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;
	private final String actualString;

	public static EnumBorgeOptions get(int index) {
		EnumBorgeOptions[] values = values();
		return index < 0 || index >= values.length ? null : values[index];
	}

	EnumBorgeOptions(String name, boolean isFloat, boolean isBoolean) {
		this.enumString = "borge.options." + name;
		this.actualString = name;
		this.enumFloat = isFloat;
		this.enumBoolean = isBoolean;
	}

	public boolean getEnumFloat() {
		return this.enumFloat;
	}

	public boolean getEnumBoolean() {
		return this.enumBoolean;
	}

	public String getEnumString() {
		return this.enumString;
	}
	public String getActualString() {
		return this.actualString;
	}
}
