package net.minecraftborge.loader;

import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.TexturePackBase;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TerrainTextureMap {
	public static final Color TRANSLUCENT = new Color(0, true);
	private static final BufferedImage MISSINGNO = new BufferedImage(16, 16, 2);
	static {
		Graphics graphics = MISSINGNO.getGraphics();
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, 16, 16);
		graphics.setColor(Color.MAGENTA);
		graphics.fillRect(0, 0, 8, 8);
		graphics.fillRect(8, 8, 8, 8);
		graphics.dispose();
	}

	private final Map<String, TerrainIcon> icons;
	private final Map<String, Integer> textureSources;
	private final BufferedImage texture;
	private Integer openGLTexture;

	private final Icon[] destroy = new Icon[10];
	public Icon getDestroyTexture(int index) {
		return this.destroy[index];
	}
	public final int count;
	public final AtlasSorter sorter;
	public final int width, height;

	public double getTileWidth() {
		return this.width;
	}
	public double getTileHeight() {
		return this.height;
	}

	public TerrainTextureMap(Map<String, TerrainIcon> icons, int count) {
		this.icons = icons;
		this.textureSources = new HashMap<>();
		this.count = count;
		this.sorter = new AtlasSorter(count);
		this.width = this.sorter.width;
		this.height = this.sorter.height;

		for (String texture : icons.keySet()) {
			this.textureSources.put(texture, icons.get(texture).textureIndex);
		}

		for (TerrainIcon icon : icons.values()) {
			icon.populate(this.sorter);
		}

		for (int i = 0; i < 10; i++) {
			this.destroy[i] = icons.get("destroy" + i);
		}

		this.texture = new BufferedImage(256 * this.sorter.width, 256 * this.sorter.height, 2);
	}

	public TerrainIcon getIconByName(String name) {
		return this.icons.get(name);
	}
	public int getIconIndexByName(String name) {
		return this.textureSources.get(name);
	}

	public BufferedImage getTexture() {
		return this.texture;
	}
	public int getGPUTexture() {
		return this.openGLTexture;
	}

	public void reload(Minecraft mc, TexturePackBase pack) {
		System.out.println();
		System.out.println("Reloading terrain texture atlas");
		long start = System.currentTimeMillis();
		Graphics graphics = this.texture.getGraphics();
		graphics.setColor(TRANSLUCENT);
		graphics.fillRect(0, 0, this.texture.getWidth(), this.texture.getHeight());

		for (Map.Entry<String, Integer> entry : this.textureSources.entrySet()) {
			TerrainIcon icon = this.getIconByName(entry.getKey());
			BufferedImage sprite;
			try (InputStream in = Objects.requireNonNull(pack.getResourceAsStream("/assets/terrain/" + entry.getKey() + ".png"))) {
				sprite = ImageIO.read(in);
				if (sprite.getWidth() != icon.width << 4 || sprite.getHeight() != icon.height << 4) {
					System.out.println("WARNING: texture " + entry.getKey() + " has incorrect resolution " + sprite.getWidth() + "x" + sprite.getHeight());
				}
			} catch (Throwable e) {
				System.out.println("Exception loading sprite " + entry.getKey() + ": " + e);
				sprite = MISSINGNO;
			}

			int atlasPackedPos = this.sorter.atlasPositions[icon.atlasIndex];
			int x = this.sorter.x(atlasPackedPos) * 256 + (icon.localX << 4);
			int y = this.sorter.y(atlasPackedPos) * 256 + (icon.localY << 4);

			int rW = icon.width << 4;
			int rH = icon.height << 4;

			graphics.drawImage(sprite, x, y, rW, rH, null);
		}

		graphics.dispose();

		RenderEngine engine = mc.renderEngine;
		if (this.openGLTexture == null) {
			this.openGLTexture = engine.allocateAndSetupTexture(this.texture);
		} else {
			engine.setupTexture(this.texture, this.openGLTexture);
		}

		try {
			ImageIO.write(this.texture, "PNG", new File(Minecraft.getMinecraftDir(), "debug.stitched_terrain.png"));
		} catch (Exception e) {
			System.err.println("Exception writing stitched terrain: " + e);
		}
		System.out.println("Finished (" + (System.currentTimeMillis() - start) + "ms)");
		System.out.println();
	}
}
