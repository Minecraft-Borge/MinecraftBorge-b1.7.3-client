package net.minecraftborge.loader;

public class AtlasSorter {
	public final int[] atlasPositions;
	public final int width;
	public final int height;

	public AtlasSorter(int atlasCount) {
		int power2 = BorgeMath.getNextPowerOf2(atlasCount);
		this.atlasPositions = new int[power2];

		int w, h;
		switch (power2) {
			case 1:
				this.atlasPositions[0] = this.pack(0, 0);
				w = 1;
				h = 1;
				break;
			case 2:
				this.atlasPositions[0] = this.pack(0, 0);
				this.atlasPositions[1] = this.pack(1, 0);
				w = 2;
				h = 1;
				break;
			case 4:
				this.atlasPositions[0] = this.pack(0, 0);
				this.atlasPositions[1] = this.pack(0, 1);
				this.atlasPositions[2] = this.pack(1, 0);
				this.atlasPositions[3] = this.pack(1, 1);
				w = 2;
				h = 2;
				break;
			case 8:
				this.atlasPositions[0] = this.pack(0, 0);
				this.atlasPositions[1] = this.pack(0, 1);
				this.atlasPositions[2] = this.pack(1, 0);
				this.atlasPositions[3] = this.pack(1, 1);
				this.atlasPositions[4] = this.pack(2, 0);
				this.atlasPositions[5] = this.pack(2, 1);
				this.atlasPositions[6] = this.pack(3, 0);
				this.atlasPositions[7] = this.pack(3, 1);
				w = 4;
				h = 2;
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + power2);
		}
		this.width = w;
		this.height = h;
	}

	private int pack(int x, int y) {
		return (x & 15) | ((y & 15) << 4);
	}

	public int x(int pack) {
		return pack & 15;
	}
	public int y(int pack) {
		return (pack >> 4) & 15;
	}
}
