package net.minecraftborge.loader.net;

import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import net.minecraftborge.loader.ModList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet143Custom extends Packet {
	public int modID;
	public byte[] data;

	public Packet143Custom() {}
	public Packet143Custom(int modID, byte[] data) {
		this.modID = modID;
		this.data = data;
	}

	@Override
	public void readPacketData(DataInputStream data) throws IOException {
		this.modID = data.readUnsignedShort();
		this.data = new byte[data.readInt()];
		data.readFully(this.data);
	}

	@Override
	public void writePacketData(DataOutputStream data) throws IOException {
		data.writeShort(this.modID);
		data.writeInt(this.data.length);
		data.write(this.data);
	}

	@Override
	public void processPacket(NetHandler network) {
		network.registerPacket(this);
		ModList.get().getNetHandler(this.modID).processPacket(network, this.data);
	}

	@Override
	public int getPacketSize() {
		return 4 + this.data.length;
	}
}
