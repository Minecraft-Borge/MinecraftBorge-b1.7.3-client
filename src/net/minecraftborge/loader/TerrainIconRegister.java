package net.minecraftborge.loader;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraftborge.loader.event.EventBus;
import net.minecraftborge.loader.event.register.TerrainStitchEvent;

import java.util.*;

public class TerrainIconRegister implements IconRegister {
	private final List<TextureVacancy> checker = new ArrayList<>();
	public int nextTextureIndex = 1;
	private final Map<String, TerrainIcon> icons = new HashMap<>();

	public TerrainIconRegister() {

	}

	public void registerBlocks() {
		for (int i = 0; i < Block.blocksList.length; i++) {
			if (Block.blocksList[i] != null) {
				Block.blocksList[i].registerIcons(this);
			}
		}
	}
	public void registerItems() {
		for (int i = 0; i < Item.itemsList.length; i++) {
			if (Item.itemsList[i] != null) {
				Item.itemsList[i].registerIcons(this);
			}
		}
	}
	public void registerExtra() {
		for (int i = 0; i < 10; i++) {
			this.getTexture("destroy" + i, 16, 16);
		}

		TerrainStitchEvent event = new TerrainStitchEvent(this);
		EventBus.push(event);
	}

	@Override
	public Icon getTexture(String name, int w, int h) {
		if (w <= 0 || h <= 0) throw new IllegalArgumentException("Texture size must be positive");
		if ((w % 16) != 0 || (h % 16) != 0) throw new IllegalArgumentException("Texture sprite size must be multiple of 16");
		if (w > 256 || h > 256) throw new IllegalArgumentException("Texture size must not exceed 256x256");
		if (this.icons.containsKey(name)) {
			TerrainIcon icon = this.icons.get(name);
			if (icon.width != w >> 4 || icon.height != h >> 4) System.err.println("Mismatching sizes for the same texture " + name + "!!!");
			return icon;
		}
		int location = -1;
		int checker = -1;
		while (location == -1) {
			checker++;
			if (this.checker.size() == checker) {
				System.out.println("Allocating new texture region (#" + (checker+1) + ")");
				this.checker.add(new TextureVacancy());
			}
			TextureVacancy vacancy = this.checker.get(checker);
			location = vacancy.getVacantSpace(w >> 4, h >> 4);
		}
		this.checker.get(checker).occupy(location, w >> 4, h >> 4);
		TerrainIcon icon = new TerrainIcon(location + (checker * 256), w >> 4, h >> 4, checker, location & 15, location >> 4);
		this.icons.put(name, icon);
		return icon;
	}

	public TerrainTextureMap createMap() {
		return new TerrainTextureMap(this.icons, this.checker.size());
	}

	private static class TextureVacancy {
		private final boolean[][] spaces = new boolean[16][16];

		private TextureVacancy() {}

		public int getVacantSpace(int w, int h) {
			if (w == 1 && h == 1) {
				for (int y = 0; y < 16; y++) {
					for (int x = 0; x < 16; x++) {
						if (this.spaces[x][y]) continue;
						return x + y * 16;
					}
				}
				return -1;
			}
			for (int y = 0; y <= 16 - h; y++) {
				loop:
				for (int x = 0; x <= 16 - w; x++) {
					for (int i = 0; i < w; i++) {
						for (int j = 0; j < h; j++) {
							if (this.spaces[x+i][y+j]) continue loop;
						}
					}
					return x + y * 16;
				}
			}
			return -1;
		}
		public void occupy(int location, int w, int h) {
			int x = location & 15;
			int y = location >> 4;

			if (w == 1 && h == 1) this.spaces[x][y] = true;
			else {
				for (int i = 0; i < w; i++) {
					for (int j = 0; j < h; j++) {
						this.spaces[x + i][y + j] = true;
					}
				}
			}
		}
	}
}
