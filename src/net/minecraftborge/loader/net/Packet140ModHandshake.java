package net.minecraftborge.loader.net;

import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Packet140ModHandshake extends Packet {
	public final ArrayList<String> mods = new ArrayList<>();
	private int packetSize = -1;

	public Packet140ModHandshake() {}
	public Packet140ModHandshake(List<String> mods) {
		this.mods.addAll(mods);
	}

	@Override
	public void readPacketData(DataInputStream data) throws IOException {
		int size = data.readUnsignedShort();
		this.mods.ensureCapacity(size);
		for (int i = 0; i < size; i++) {
			this.mods.add(readString(data, 32));
		}
	}

	@Override
	public void writePacketData(DataOutputStream data) throws IOException {
		data.writeShort(this.mods.size());
		for (String mod : this.mods) {
			writeString(mod, data);
		}
	}

	@Override
	public void processPacket(NetHandler network) {
		network.handleModHandshake(this);
	}

	private void calculatePacketSize() {
		this.packetSize = 0;
		for (String modid : this.mods) {
			this.packetSize += 2;
			this.packetSize += modid.length() * 2;
		}
		System.out.println("Packet140ModHandshake " + this.packetSize + " bytes");
	}
	@Override
	public int getPacketSize() {
		if (this.packetSize == -1) {
			this.calculatePacketSize();
		}
		return this.packetSize;
	}
}
