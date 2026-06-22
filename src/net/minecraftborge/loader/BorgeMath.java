package net.minecraftborge.loader;

import net.minecraft.src.MathHelper;

/**
 * Various mathematical utilities for MinecraftBorge.
 */
public class BorgeMath {
	public static float clamp(float value, float min, float max) {
		if (value <= min) return min;
		return Math.min(value, max);
	}
	public static double clamp(double value, double min, double max) {
		if (value <= min) return min;
		return Math.min(value, max);
	}

	public static float lerp(float from, float to, double delta) {
		return from + (to - from) * (float) delta;
	}
	public static double lerp(double from, double to, double delta) {
		return from + (to - from) * delta;
	}

	public static float clampedLerp(float from, float to, double delta) {
		if (delta <= 0.0) return from;
		if (delta >= 1.0) return to;
		return lerp(from, to, delta);
	}
	public static double clampedLerp(double from, double to, double delta) {
		if (delta <= 0.0) return from;
		if (delta >= 1.0) return to;
		return lerp(from, to, delta);
	}

	public static float lerp2(float minXminY, float minXmaxY, float maxXminY, float maxXmaxY, double deltaX, double deltaY) {
		return lerp(lerp(minXminY, minXmaxY, deltaY), lerp(maxXminY, maxXmaxY, deltaY), deltaX);
	}
	public static double lerp2(double minXminY, double minXmaxY, double maxXminY, double maxXmaxY, double deltaX, double deltaY) {
		return lerp(lerp(minXminY, minXmaxY, deltaY), lerp(maxXminY, maxXmaxY, deltaY), deltaX);
	}

	public static float clampedLerp2(float minXminY, float minXmaxY, float maxXminY, float maxXmaxY, double lerpX, double lerpY) {
		return clampedLerp(clampedLerp(minXminY, minXmaxY, lerpY), clampedLerp(maxXminY, maxXmaxY, lerpY), lerpX);
	}
	public static double clampedLerp2(double minXminY, double minXmaxY, double maxXminY, double maxXmaxY, double lerpX, double lerpY) {
		return clampedLerp(clampedLerp(minXminY, minXmaxY, lerpY), clampedLerp(maxXminY, maxXmaxY, lerpY), lerpX);
	}

	public static int getSuperiorExp2(int value) {
		return value == 0 ? 0 : 32 - Integer.numberOfLeadingZeros(value - 1);
	}
	public static int getNextPowerOf2(int value) {
		return MathHelper.floor_double(Math.pow(2, getSuperiorExp2(value)));
	}
}
