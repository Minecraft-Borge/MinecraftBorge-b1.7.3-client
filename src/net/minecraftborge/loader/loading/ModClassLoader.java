package net.minecraftborge.loader.loading;

import net.minecraftborge.loader.asm.IDefiningClassLoader;

import java.net.URL;
import java.net.URLClassLoader;

public class ModClassLoader extends URLClassLoader implements IDefiningClassLoader {
	public ModClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	@Override
	public Class<?> defineClass(String name, byte[] b) {
		return super.defineClass(name, b, 0, b.length);
	}
}
