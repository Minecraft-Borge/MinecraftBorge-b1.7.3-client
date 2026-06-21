package net.minecraftborge.loader;

@SuppressWarnings("unused")
public class Side {
	public static final int
			DOWN = 0, UP = 1,
			EAST = 2, WEST = 3,
			NORTH = 4, SOUTH = 5;

	public static final int
			NEG_Y = DOWN, POS_Y = UP,
			NEG_Z = EAST, POS_Z = WEST,
			NEG_X = NORTH, POS_X = SOUTH;

	public static final int Y = 0, Z = 1, X = 2;

	public static int getAxis(int side) {
		switch (side) {
			case NEG_Y:
			case POS_Y:
				return Y;
			case NEG_Z:
			case POS_Z:
				return Z;
			case NEG_X:
			case POS_X:
				return X;
		}
		return 0;
	}
}
