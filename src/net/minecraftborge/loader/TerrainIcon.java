package net.minecraftborge.loader;

public class TerrainIcon implements Icon {
	private float minU, maxU, minV, maxV;
	public final int textureIndex;
	public final int width, height;
	public final int atlasIndex;
	private int atlasX, atlasY;
	public final int localX, localY;
	public TerrainIcon(int textureIndex, int width, int height, int atlasIndex, int localX, int localY) {
		this.textureIndex = textureIndex;
		this.width = width;
		this.height = height;
		this.atlasIndex = atlasIndex;
		this.localX = localX;
		this.localY = localY;
	}

	@Override
	public double getU(double lerp) {
		return this.minU + (this.maxU - this.minU) * lerp;
	}
	@Override
	public double getV(double lerp) {
		return this.minV + (this.maxV - this.minV) * lerp;
	}

	public void populate(int atlasSize) {
		this.minU = (this.textureIndex & 15) / 16.0F;
		this.maxU = ((this.textureIndex & 15) + this.width - 0.001F) / 16.0F;
		this.minV = ((this.textureIndex >> 4) / 16.0F) / atlasSize;
		this.maxV = (((this.textureIndex >> 4) + this.height - 0.001F) / 16.0F) / atlasSize;
	}
	public void populate(AtlasSorter sorter) {
		int pack = sorter.atlasPositions[this.atlasIndex];
		this.atlasX = sorter.x(pack);
		this.atlasY = sorter.y(pack);
		float x = this.atlasX / (float) sorter.width;
		float y = this.atlasY / (float) sorter.height;
		this.minU = this.localX / 16.0F / sorter.width + x;
		this.maxU = (this.localX + this.width - 0.001F) / 16.0F / sorter.width + x;
		this.minV = this.localY / 16.0F / sorter.height + y;
		this.maxV = (this.localY + this.height - 0.001F) / 16.0F / sorter.height + y;
	}

	public int getAtlasX() {
		return this.atlasX;
	}
	public int getAtlasY() {
		return this.atlasY;
	}
}
