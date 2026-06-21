package net.minecraftborge.loader;

/**
 * Representation of a texture atlas sprite
 */
public interface Icon {
	double getU(double lerp);
	double getV(double lerp);
}
