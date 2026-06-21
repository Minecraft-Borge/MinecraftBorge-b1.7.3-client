package net.minecraftborge.loader;

import java.util.Arrays;

public class ByteArrayWriter {
	private final byte[] data;
	private int position;

	public ByteArrayWriter(int size) {
		this.data = new byte[size];
		this.position = 0;
	}
	public void reset() {
		Arrays.fill(this.data, (byte) 0);
		this.position = 0;
	}
	public byte[] finish() {
		byte[] buf = new byte[this.position];
		System.arraycopy(this.data, 0, buf, 0, this.position);
		return buf;
	}

	public void writeByte(int v) {
		this.data[this.position++] = (byte) v;
	}
	public void writeShort(int v) {
		ByteUtil.writeShort(this.data, this.position, v);
		this.position += 2;
	}
	public void writeInt(int v) {
		ByteUtil.writeInt(this.data, this.position, v);
		this.position += 4;
	}
	public void writeLong(long v) {
		ByteUtil.writeLong(this.data, this.position, v);
		this.position += 8;
	}
	public void writeBoolean(boolean v) {
		this.writeByte(v ? 1 : 0);
	}
}
