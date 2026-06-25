package net.minecraftborge.loader;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class ByteArrayWriter implements DataOutput {
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

	@Override
	public void writeByte(int v) {
		this.data[this.position++] = (byte) v;
	}

	@Override
	public void writeShort(int v) {
		ByteUtil.writeShort(this.data, this.position, v);
		this.position += 2;
	}

	@Override
	public void writeChar(int v) {
		this.writeShort((char) v);
	}

	@Override
	public void writeInt(int v) {
		ByteUtil.writeInt(this.data, this.position, v);
		this.position += 4;
	}

	@Override
	public void writeLong(long v) {
		ByteUtil.writeLong(this.data, this.position, v);
		this.position += 8;
	}

	@Override
	public void writeFloat(float v) {
		this.writeInt(Float.floatToRawIntBits(v));
	}

	@Override
	public void writeDouble(double v) {
		this.writeLong(Double.doubleToRawLongBits(v));
	}

	@Override
	public void writeBytes(String s) {
		for (char c : s.toCharArray()) this.writeByte(c);
	}

	@Override
	public void writeChars(String s) {
		for (char c : s.toCharArray()) this.writeChar(c);
	}

	@Override
	public void writeUTF(String s) throws IOException {
		writeString(s, this);
	}

	@Override
	public void write(int b) throws IOException {
		this.writeByte(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		for (byte value : b) {
			this.writeByte(value);
		}
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		for (int i = 0; i < len; i++) {
			this.writeByte(b[i+off]);
		}
	}

	@Override
	public void writeBoolean(boolean v) {
		this.writeByte(v ? 1 : 0);
	}

	public static void writeString(String string, DataOutput data) throws IOException {
		if(string.length() > Short.MAX_VALUE) {
			throw new IOException("String too big");
		} else {
			data.writeShort(string.length());
			data.writeChars(string);
		}
	}
}
