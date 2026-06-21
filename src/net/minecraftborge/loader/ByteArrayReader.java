package net.minecraftborge.loader;

public class ByteArrayReader {
	private byte[] data;
	private int position = 0;
	public ByteArrayReader() {}

	public void setData(byte[] data) {
		this.data = data;
		this.position = 0;
	}

	public byte readByte() {
		return this.data[this.position++];
	}
	public short readShort() {
		short v = (short) ByteUtil.readShort(this.data, this.position);
		this.position += 2;
		return v;
	}
	public int readInt() {
		int v = ByteUtil.readInt(this.data, this.position);
		this.position += 4;
		return v;
	}
	public long readLong() {
		long v = ByteUtil.readLong(this.data, this.position);
		this.position += 8;
		return v;
	}
	public int readUnsignedByte() {
		return this.data[this.position++] & 255;
	}
	public int readUnsignedShort() {
		int v = ByteUtil.readShort(this.data, this.position);
		this.position += 2;
		return v;
	}
	public boolean readBoolean() {
		return this.readByte() != 0;
	}
}
