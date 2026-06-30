package net.minecraftborge.loader;

public class IconClone implements Icon {
	private final Icon toClone;
	public IconClone(Icon toClone) {
		this.toClone = toClone;
	}

	@Override
	public double getU(double lerp) {
		return this.toClone.getU(lerp);
	}

	@Override
	public double getV(double lerp) {
		return this.toClone.getV(lerp);
	}
}
