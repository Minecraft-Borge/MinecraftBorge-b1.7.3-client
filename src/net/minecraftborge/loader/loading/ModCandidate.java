package net.minecraftborge.loader.loading;

import net.minecraftborge.loader.Namespace;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class ModCandidate {
	public final String name;
	public final String version;
	public final List<String> dependencies;
	public final JarFile jar;
	public final File sourceFile;
	public final Attributes attributes;

	public ModCandidate(JarFile jar, File sourceFile, Attributes attributes) {
		this.name = attributes.getValue("Mod-Id");
		this.version = attributes.getValue("Mod-Version");
		this.jar = jar;
		this.sourceFile = sourceFile;
		this.attributes = attributes;

		String dependenciesList = attributes.getValue("Mod-Dependencies");
		if (dependenciesList == null) {
			this.dependencies = Collections.emptyList();
		} else {
			List<String> list = new ArrayList<>();
			String[] array = dependenciesList.split(";");
			for (String dependent : array) {
				if (Namespace.validate(dependent)) list.add(dependent);
				else System.err.println("Ignoring invalid " + dependent);
			}
			this.dependencies = Collections.unmodifiableList(list);
		}

		System.out.println("New candidate " + this.name + " (" + this.attributes.size() + " manifest attribs)");
	}

	public boolean validateProperties() {
		return this.name != null && this.version != null;
	}
}
