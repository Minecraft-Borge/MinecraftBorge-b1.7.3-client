package net.minecraftborge.loader.net;

import net.minecraft.src.NetHandler;
import net.minecraftborge.loader.ByteArrayReader;
import net.minecraftborge.loader.ByteArrayWriter;

public abstract class ModNetHandler {
	protected final ByteArrayWriter writer = new ByteArrayWriter(32000);
	protected final ByteArrayReader reader = new ByteArrayReader();

	public abstract void processPacket(NetHandler network, byte[] data);

	public static final ModNetHandler DUMMY = new ModNetHandler() {
		@Override
		public void processPacket(NetHandler network, byte[] data) {

		}
	};
}
