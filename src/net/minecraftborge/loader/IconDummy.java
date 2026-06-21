package net.minecraftborge.loader;

/**
 * For special use cases for when a render type needs to apply custom logic
 * (for example, mirroring the door texture)
 */
public class IconDummy implements Icon {
	@Override
	public double getU(double lerp) {
		return lerp;
	}

	@Override
	public double getV(double lerp) {
		return lerp;
	}
}
