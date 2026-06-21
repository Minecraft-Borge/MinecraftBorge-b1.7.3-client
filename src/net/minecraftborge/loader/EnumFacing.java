package net.minecraftborge.loader;

public enum EnumFacing {
	DOWN(Side.DOWN, -1, Side.UP, 0, -1, 0),
	UP(Side.UP, -1, Side.DOWN, 0, 1, 0),
	EAST(Side.EAST, 2, Side.WEST, 0, 0, -1),
	WEST(Side.WEST, 0, Side.EAST, 0, 0, 1),
	NORTH(Side.NORTH, 1, Side.SOUTH, -1, 0, 0),
	SOUTH(Side.SOUTH, 3, Side.NORTH, 1, 0, 0);

	private final int index, horizontalIndex;
	private final int opposite;
	private final int offsetX, offsetY, offsetZ;

	EnumFacing(int index, int horizontalIndex, int opposite, int offsetX, int offsetY, int offsetZ) {
		this.index = index;
		this.horizontalIndex = horizontalIndex;
		this.opposite = opposite;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}

	public int getIndex() {
		return this.index;
	}
	public int getHorizontalIndex() {
		return this.horizontalIndex;
	}
	public EnumFacing getOpposite() {
		return VALUES[this.opposite];
	}

	public int getOffsetX() {
		return this.offsetX;
	}
	public int getOffsetY() {
		return this.offsetY;
	}
	public int getOffsetZ() {
		return this.offsetZ;
	}

	public boolean isHorizontal() {
		return this.horizontalIndex != -1;
	}

	public static final EnumFacing[] VALUES = new EnumFacing[6];
	public static final EnumFacing[] HORIZONTALS = new EnumFacing[4];

	static {
		for (EnumFacing facing : values()) {
			VALUES[facing.getIndex()] = facing;
			if (facing.isHorizontal()) HORIZONTALS[facing.getHorizontalIndex()] = facing;
		}
	}
}
