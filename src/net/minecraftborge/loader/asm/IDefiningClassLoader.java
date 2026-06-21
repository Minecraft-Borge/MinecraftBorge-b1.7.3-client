package net.minecraftborge.loader.asm;

public interface IDefiningClassLoader {
	Class<?> defineClass(String name, byte[] data) throws ClassNotFoundException;
	Class<?> loadClass(String name) throws ClassNotFoundException;
}
