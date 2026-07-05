package net.minecraftborge.loader;

import net.minecraft.src.NibbleArray;

public class MutableNibbleArray extends NibbleArray {
	public byte[] data;

	public MutableNibbleArray(int size) {
		super(0);
		this.data = new byte[size >> 1];
	}

	public MutableNibbleArray(byte[] data) {
		super(new byte[0]);
		this.data = data;
	}

	public int getNibble(int var1, int var2, int var3) {
		int var4 = var1 << 11 | var3 << 7 | var2;
		int var5 = var4 >> 1;
		int var6 = var4 & 1;
		return var6 == 0 ? this.data[var5] & 15 : this.data[var5] >> 4 & 15;
	}

	public void setNibble(int var1, int var2, int var3, int var4) {
		int var5 = var1 << 11 | var3 << 7 | var2;
		int var6 = var5 >> 1;
		int var7 = var5 & 1;
		if(var7 == 0) {
			this.data[var6] = (byte)(this.data[var6] & 240 | var4 & 15);
		} else {
			this.data[var6] = (byte)(this.data[var6] & 15 | (var4 & 15) << 4);
		}
	}

	public int getNibble(int index) {
		return (index & 1) == 0 ? this.data[index >> 1] & 15 : this.data[index >> 1] >> 4 & 15;
	}
	public void setNibble(int index, int nibble) {
		if ((index & 1) == 0) {
			this.data[index >> 1] = (byte)(this.data[index >> 1] & 240 | nibble & 15);
		} else {
			this.data[index >> 1] = (byte)(this.data[index >> 1] & 15 | (nibble & 15) << 4);
		}
	}

	public boolean isValid() {
		return this.data != null;
	}
}
