package net.minecraftborge.loader.event.register;

import net.minecraftborge.MinecraftBorge;
import net.minecraftborge.loader.event.Event;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class ExtractSoundsEvent extends Event {
	private final File resourcesFolder;

	public ExtractSoundsEvent(File resourcesFolder) {
		this.resourcesFolder = resourcesFolder;
	}

	public void extract(String root) {
		try {
			System.out.println("Extracting sounds from " + root);
			ArrayList<String> soundLocations = new ArrayList<>();
			try (InputStream in = MinecraftBorge.getResourceAsStream(root + "sounds.txt")) {
				Scanner scanner = new Scanner(in);
				while (scanner.hasNextLine()) {
					String next = scanner.nextLine().trim();
					if (!next.isEmpty() && !next.startsWith("#")) {
						soundLocations.add(next);
					}
				}
				scanner.close();
			}

			byte[] pkt = new byte[4096];
			int n;
			for (String location : soundLocations) {
				System.out.println("Extracting sound " + location);
				File file = new File(this.resourcesFolder, location);
				this.createDirs(file);
				file.createNewFile();
				try (
					InputStream in = MinecraftBorge.getResourceAsStream(root + location);
					OutputStream out = Files.newOutputStream(file.toPath())
				) {
					while ((n = in.read(pkt)) != -1) {
						out.write(pkt, 0, n);
					}
				}
			}
			System.out.println("Completed successfully!");
		} catch (Exception e) {
			System.err.println("Could not extract sounds from " + root);
			e.printStackTrace();
		}
	}

	private void createDirs(File location) {
		location.getParentFile().mkdirs();
	}
}
