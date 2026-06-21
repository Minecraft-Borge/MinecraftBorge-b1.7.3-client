package net.minecraftborge.loader.net;

import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet142OpenModGUI extends Packet {
	public int modID;
	public int containerId;
	public int guiID;
	public byte[] extraData;

	public Packet142OpenModGUI() {}
	public Packet142OpenModGUI(int modID, int containerId, int guiID, byte[] extraData) {
		this.modID = modID;
		this.containerId = containerId;
		this.guiID = guiID;
		this.extraData = extraData;
		if (extraData.length > 0xFFFF) throw new IllegalArgumentException("Extra data size must not exceed " + 0xFFFF);
	}

	@Override
	public void readPacketData(DataInputStream data) throws IOException {
		this.modID = data.readUnsignedShort();
		this.containerId = data.readUnsignedByte();
		this.guiID = data.readUnsignedByte();
		int size = data.readUnsignedShort();
		this.extraData = new byte[size];
		for (int i = 0; i < size; i++) {
			this.extraData[i] = data.readByte();
		}
	}

	@Override
	public void writePacketData(DataOutputStream data) throws IOException {
		data.writeShort(this.modID);
		data.writeByte(this.containerId);
		data.writeByte(this.guiID);
		data.writeShort(this.extraData.length);
		for (byte b : this.extraData) data.writeByte(b);
	}

	@Override
	public void processPacket(NetHandler network) {
		network.handleOpenModGUI(this);
	}

	@Override
	public int getPacketSize() {
		return 4 + this.extraData.length;
	}
}
