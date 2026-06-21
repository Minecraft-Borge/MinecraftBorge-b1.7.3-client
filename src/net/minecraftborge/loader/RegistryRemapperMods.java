package net.minecraftborge.loader;

import java.util.Locale;
import java.util.Map;

public class RegistryRemapperMods implements RegistryMapper {
	public static final RegistryRemapperMods IDENTITY = new RegistryRemapperMods(new int[ModList.get().getRemoteMods().size()], new int[ModList.get().getRemoteMods().size()]) {
		@Override
		public int remap(int id) {
			return id;
		}

		@Override
		public int demap(int id) {
			return id;
		}
	};

	private final int[] remapper;
	private final int[] demapper;

	public RegistryRemapperMods(int[] remapper, int[] demapper) {
		this.remapper = remapper;
		this.demapper = demapper;

		if (this.remapper.length != this.demapper.length && this.remapper.length != ModList.get().getRemoteMods().size()) throw new IllegalArgumentException();
	}

	public RegistryRemapperMods(ModList registry, Map<String, Integer> remote) {
		this(new int[registry.getRemoteMods().size()], new int[registry.getRemoteMods().size()]);

		for (int i = 0; i < registry.getRemoteMods().size(); i++) {
			String mod = registry.getRemoteMods().get(i);
			if (mod != null) {
				Integer remoteID = remote.get(mod);
				if (remoteID == null) throw new IllegalStateException("Mod missing on server: " + mod);
				this.remapper[i] = remoteID;
				this.demapper[remoteID] = i;
			}
		}
	}
	@Override
	public int remap(int id) {
		return this.remapper[id];
	}
	@Override
	public int demap(int id) {
		return this.demapper[id];
	}
}
