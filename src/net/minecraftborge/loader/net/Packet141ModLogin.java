package net.minecraftborge.loader.net;

import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import net.minecraftborge.loader.ModList;
import net.minecraftborge.loader.RegistryBlocks;
import net.minecraftborge.loader.RegistryEntities;
import net.minecraftborge.loader.RegistryItems;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Packet141ModLogin extends Packet {
	public boolean flag;
	public Map<String, Integer> remoteBlocks, remoteItems, remoteEntities, remoteMods;
	private int packetSize = -1;

	public Packet141ModLogin() {
		this.flag = true;
	}
	public Packet141ModLogin(RegistryBlocks blocks, RegistryItems items, RegistryEntities entities) {
		this.flag = false;
		this.remoteBlocks = new HashMap<>(blocks.getKeys().size());
		for (String block : blocks.getKeys()) {
			this.remoteBlocks.put(block, blocks.getValue(block).blockID);
		}
		this.remoteItems = new HashMap<>(items.getKeys().size());
		for (String item : items.getKeys()) {
			this.remoteItems.put(item, items.getValue(item).shiftedIndex);
		}
		this.remoteEntities = new HashMap<>(entities.getKeys().size());
		for (String entity : entities.getKeys()) {
			this.remoteEntities.put(entity, entities.wrapper.classToIDMap.get(entities.getValue(entity)));
		}
		this.remoteMods = new HashMap<>(ModList.get().getRemoteMods().size());
		for (String mod : ModList.get().getRemoteMods()) {
			this.remoteMods.put(mod, ModList.get().getModIndex(mod));
		}
	}

	@Override
	public void readPacketData(DataInputStream data) throws IOException {
		this.flag = data.readBoolean();
		if (this.flag) return;
		this.read(data, data.readUnsignedShort(), this.remoteBlocks = new HashMap<>());
		this.read(data, data.readUnsignedShort(), this.remoteItems = new HashMap<>());
		this.read(data, data.readUnsignedShort(), this.remoteEntities = new HashMap<>());
		this.read(data, data.readUnsignedShort(), this.remoteMods = new HashMap<>());
	}

	private void read(DataInputStream data, int count, Map<String, Integer> map) throws IOException {
		for (int i = 0; i < count; i++) {
			map.put(readString(data, 64), data.readUnsignedShort());
		}
		System.out.println(count + " vs " + map.size());
	}

	@Override
	public void writePacketData(DataOutputStream data) throws IOException {
		if (this.flag) {
			data.writeBoolean(true);
			return;
		}
		data.writeBoolean(false);
		data.writeShort(this.remoteBlocks.size());
		for (Map.Entry<String, Integer> entry : this.remoteBlocks.entrySet()) {
			writeString(entry.getKey(), data);
			data.writeShort(entry.getValue());
		}
		data.writeShort(this.remoteItems.size());
		for (Map.Entry<String, Integer> entry : this.remoteItems.entrySet()) {
			writeString(entry.getKey(), data);
			data.writeShort(entry.getValue());
		}
		data.writeShort(this.remoteEntities.size());
		for (Map.Entry<String, Integer> entry : this.remoteEntities.entrySet()) {
			writeString(entry.getKey(), data);
			data.writeShort(entry.getValue());
		}
		data.writeShort(this.remoteMods.size());
		for (Map.Entry<String, Integer> entry : this.remoteMods.entrySet()) {
			writeString(entry.getKey(), data);
			data.writeShort(entry.getValue());
		}
	}

	@Override
	public void processPacket(NetHandler network) {
		network.handleModLogin(this);
	}

	private void calculatePacketSize() {
		this.packetSize = 9;
		for (String key : this.remoteBlocks.keySet()) {
			this.packetSize += key.length() * 2 + 4;
		}
		for (String key : this.remoteItems.keySet()) {
			this.packetSize += key.length() * 2 + 4;
		}
		for (String key : this.remoteEntities.keySet()) {
			this.packetSize += key.length() * 2 + 4;
		}
		for (String key : this.remoteMods.keySet()) {
			this.packetSize += key.length() * 2 + 4;
		}
		System.out.println("Packet141ModLogin " + this.packetSize + " bytes");
	}
	@Override
	public int getPacketSize() {
		if (this.flag) return 1;
		if (this.packetSize == -1) {
			this.calculatePacketSize();
		}
		return this.packetSize;
	}
}
