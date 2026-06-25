package net.minecraftborge.loader;

import java.io.DataInput;
import java.io.IOException;

public class ByteArrayReader implements DataInput {
	private byte[] data;
	private int position = 0;
	public ByteArrayReader() {}

	public void setData(byte[] data) {
		this.data = data;
		this.position = 0;
	}

	@Override
	public byte readByte() {
		return this.data[this.position++];
	}

	@Override
	public short readShort() {
		short v = (short) ByteUtil.readShort(this.data, this.position);
		this.position += 2;
		return v;
	}

	@Override
	public int readInt() {
		int v = ByteUtil.readInt(this.data, this.position);
		this.position += 4;
		return v;
	}

	@Override
	public long readLong() {
		long v = ByteUtil.readLong(this.data, this.position);
		this.position += 8;
		return v;
	}

	@Override
	public float readFloat() {
		return Float.intBitsToFloat(this.readInt());
	}

	@Override
	public double readDouble() {
		return Double.longBitsToDouble(this.readLong());
	}

	@Override
	public String readLine() throws IOException {
		return readString(this, Short.MAX_VALUE);
	}

	@Override
	public String readUTF() throws IOException {
		return readString(this, Short.MAX_VALUE);
	}

	@Override
	public int readUnsignedByte() {
		return this.data[this.position++] & 255;
	}

	@Override
	public int readUnsignedShort() {
		int v = ByteUtil.readShort(this.data, this.position);
		this.position += 2;
		return v;
	}

	@Override
	public char readChar() {
		return (char) this.readShort();
	}

	@Override
	public void readFully(byte[] b) {
		for (int i = 0; i < b.length; i++) {
			b[i] = this.readByte();
		}
	}

	@Override
	public void readFully(byte[] b, int off, int len) {
		for (int i = 0; i < len; i++) {
			b[i + off] = this.readByte();
		}
	}

	@Override
	public int skipBytes(int n) {
		this.position += n;
		if (this.position >= this.data.length) throw new ArrayIndexOutOfBoundsException(this.position);
		return n;
	}

	@Override
	public boolean readBoolean() {
		return this.readByte() != 0;
	}

	public static String readString(DataInput data, int maxLength) throws IOException {
		short len = data.readShort();
		if(len > maxLength) {
			throw new IOException("Received string length longer than maximum allowed (" + len + " > " + maxLength + ")");
		} else if(len < 0) {
			throw new IOException("Received string length is less than zero! Weird string!");
		} else {
			StringBuilder builder = new StringBuilder();

			for(int i = 0; i < len; ++i) {
				builder.append(data.readChar());
			}

			return builder.toString();
		}
	}
}
