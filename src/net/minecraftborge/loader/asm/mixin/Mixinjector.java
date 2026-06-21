package net.minecraftborge.loader.asm.mixin;

import net.minecraftborge.loader.asm.ASMDataTable;
import net.minecraftborge.loader.asm.ClassASMData;
import net.minecraftborge.loader.loading.ModClassLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Mixinjector {
	public static void applyMixins(ModClassLoader classLoader, ASMDataTable table, Collection<String> loadedModIDs) {
		List<ClassASMData> mixins = table.getAnnotated(Mixin.class.getName());
		if (!mixins.isEmpty()) {
			System.out.println("[MIXIN] Found " + mixins.size() + " mixin candidates");
			System.out.println("[MIXIN] Note that mixins are experimental and may not work properly!!");
		}
		for (ClassASMData mixin : mixins) {
			Map<String, Object> props = mixin.annotations.get(Mixin.class.getName());
			String target = String.valueOf(props.get("value"));
			if (props.containsKey("requiredMod")) {
				String mod = String.valueOf(props.get("requiredMod")).toLowerCase(Locale.ENGLISH);
				if (!loadedModIDs.contains(mod)) {
					System.out.println("[MIXIN] Skipping mixin " + mixin.className + " (Mod " + mod + " is required but not loaded)");
					continue;
				}
			}

			byte[] data = null;
			try (
					InputStream cin = classLoader.getResourceAsStream(target.replace('.', '/') + ".class");
					InputStream min = classLoader.getResourceAsStream(mixin.className.replace('.', '/') + ".class")
			) {
				if (cin == null) {
					System.err.println("[MIXIN] Failed to apply mixin " + mixin.className + " because target class " + target + " not found");
					continue;
				}
				if (min == null) {
					System.err.println("[MIXIN] Searching for a mixin class that doesn't exist (" + mixin.className + ")");
					continue;
				}
				ClassReader cr = new ClassReader(cin);
				ClassWriter cw = new ClassWriter(cr, 0);
				ClassReader mcr = new ClassReader(min);
				MixinClassVisitor visitor = new MixinClassVisitor(cw, target.replace('.', '/'));
				mcr.accept(visitor, 0);

				data = cw.toByteArray();
			} catch (Throwable e) {
				System.err.println("[MIXIN] Failed to apply mixin " + mixin.className + ": " + e);
				e.printStackTrace(System.err);
			}
			if (data != null) {
				System.out.println("[MIXIN] Applying mixin " + mixin.className + "->" + target + " (" + data.length + " bytes)");
				classLoader.defineClass(target, data);
			}
		}
	}
}
