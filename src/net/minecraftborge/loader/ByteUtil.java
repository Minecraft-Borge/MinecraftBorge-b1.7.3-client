package net.minecraftborge.loader;

public class ByteUtil {
	public static int readShort(byte[] array, int index) {
		return ((array[index] & 255) << 8) | (array[index+1] & 255);
	}
	public static int readInt(byte[] array, int index) {
		return ((array[index] & 255) << 24) | ((array[index+1] & 255) << 16) | ((array[index+2] & 255) << 8) | (array[index+3] & 255);
	}
	public static long readLong(byte[] array, int index) {
		long most = readInt(array, index) & 0xFFFFFFFFL;
		long least = readInt(array, index + 4) & 0xFFFFFFFFL;
		return (most << 32) | least;
	}

	public static void writeShort(byte[] array, int index, int value) {
		array[index] = (byte)((value >> 8) & 255);
		array[index+1] = (byte)(value & 255);
	}
	public static void writeInt(byte[] array, int index, int value) {
		array[index] = (byte)((value >> 24) & 255);
		array[index+1] = (byte)((value >> 16) & 255);
		array[index+2] = (byte)((value >> 8) & 255);
		array[index+3] = (byte)(value & 255);
	}
	public static void writeLong(byte[] array, int index, long value) {
		int most = (int)(value >> 32);
		int least = (int)(value);
		writeInt(array, index, most);
		writeInt(array, index + 4, least);
	}
}
