package net.minecraftborge.loader;

import net.minecraft.src.MathHelper;

public class BorgeMath {
	public static double clamp(double value, double min, double max) {
		if (value <= min) return min;
		return Math.min(value, max);
	}

	public static double lerp(double from, double to, double delta) {
		return from + (to - from) * delta;
	}

	public static double clampedLerp(double from, double to, double delta) {
		if (delta <= 0.0) return from;
		if (delta >= 1.0) return to;
		return lerp(from, to, delta);
	}

	public static double lerp2(double from1, double to1, double delta1, double from2, double to2, double delta2) {
		return lerp(lerp(from1, to1, delta1), lerp(from2, to2, delta1), delta2);
	}

	public static double clampedLerp2(double from1, double to1, double delta1, double from2, double to2, double delta2) {
		return clampedLerp(clampedLerp(from1, to1, delta1), clampedLerp(from2, to2, delta1), delta2);
	}

	public static int getSuperiorExp2(int value) {
		return value == 0 ? 0 : 32 - Integer.numberOfLeadingZeros(value - 1);
	}
	public static int getNextPowerOf2(int value) {
		return MathHelper.floor_double(Math.pow(2, getSuperiorExp2(value)));
	}
}
