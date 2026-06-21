package net.minecraftborge.loader;

import net.minecraft.src.TextureFX;
import net.minecraftborge.MinecraftBorge;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@SuppressWarnings("unused")
public class TextureSimpleAnimationFX extends TextureFX {
	private final byte[][] animation;
	private final int[] frames;
	private int counter;

	public TextureSimpleAnimationFX(TerrainIcon texture, String source) {
		super(texture);

		String filename = "assets/animation/" + source + ".png";
		try (
			InputStream in = MinecraftBorge.getResourceAsStream(filename + ".txt");
			Scanner scanner = new Scanner(in);
		) {
			String[] framesText = scanner.nextLine().split(";");
			this.frames = new int[framesText.length];
			for (int i = 0; i < framesText.length; i++) {
				this.frames[i] = Integer.parseUnsignedInt(framesText[i]);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not read animation metadata", e);
		}

		try {
			BufferedImage img = ImageIO.read(MinecraftBorge.getResource(filename));
			if (img.getHeight() % 16 != 0) throw new IOException("Animation texture height must be multiple of 16");
			if (img.getWidth() != 16) throw new IOException("Animation texture width must be 16");
			int size = img.getHeight() / 16;
			this.animation = new byte[size][1024];
			int[] data = new int[256];
			for (int i = 0; i < size; i++) {
				img.getRGB(0, i*16, 16, 16, data, 0, 16);
				this.unpack(i, data);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not read animation image", e);
		}
	}

	private void unpack(int frame, int[] data) {
		byte[] texture = this.animation[frame];
		for (int i = 0; i < 256; i++) {
			int a = data[i] >> 24 & 255;
			int r = data[i] >> 16 & 255;
			int g = data[i] >> 8 & 255;
			int b = data[i] & 255;

			texture[i*4] = (byte) r;
			texture[i*4+1] = (byte) g;
			texture[i*4+2] = (byte) b;
			texture[i*4+3] = (byte) a;
		}
	}

	@Override
	public void onTick() {
		this.counter = (this.counter + 1) % this.frames.length;
		System.arraycopy(this.animation[this.frames[this.counter]], 0, this.imageData, 0, 1024);
	}
}
