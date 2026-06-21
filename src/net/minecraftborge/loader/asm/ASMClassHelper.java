package net.minecraftborge.loader.asm;

import net.minecraftborge.loader.event.*;
import net.minecraftborge.loader.loading.ModClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings("unchecked")
public class ASMClassHelper implements Opcodes {
	private static boolean asmEventBusGenerated = false;
	static {
		// noinspection all
		new File("generated_classes/").mkdirs();
	}

	public static void generateASMEventBus(ModClassLoader loader, ASMDataTable table) throws Exception {
		if (asmEventBusGenerated) System.err.println("ASMEventBus generating another time! Is this intentional?");
		asmEventBusGenerated = true;
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

		cw.visit(
				V1_8,
				ACC_PUBLIC,
				"net/minecraftborge/loader/event/ASMEventBus",
				null,
				"java/lang/Object",
				null
		);

		emptyCtor(cw);

		Map<Class<? extends Event>, List<MethodASMData>> subscribedMethods = new HashMap<>();
		List<ClassASMData> buses = table.getAnnotated(EventBusSubscriber.class.getName());
		for (ClassASMData clazz : buses) {
			List<MethodASMData> subscribers = clazz.annotatedMethods.getOrDefault(EventHandler.class.getName(), Collections.emptyList());
			if (subscribers.isEmpty()) System.out.println("WARNING: Event bus subscriber" + clazz.className + " has no annotated methods!");
			for (MethodASMData data : subscribers) {
				Type[] args = Type.getArgumentTypes(data.descriptor);
				if (args.length != 1) {
					System.out.println("Invalid subscriber signature: " + Arrays.toString(args));
					continue;
				}
				Type arg = args[0];
				Class<?> argClass = loader.loadClass(arg.getClassName());
				if (Event.class.isAssignableFrom(argClass) && !Modifier.isAbstract(argClass.getModifiers())) {
					subscribedMethods.computeIfAbsent((Class<? extends Event>) argClass, k -> new ArrayList<>()).add(data);
				} else {
					System.out.println("Invalid event: " + argClass);
				}
			}
		}

		for (Map.Entry<Class<? extends Event>, List<MethodASMData>> entry : subscribedMethods.entrySet()) {
			addEventMethod(cw, entry.getKey(), entry.getValue());
		}

		cw.visitEnd();

		byte[] data = cw.toByteArray();
		try (FileOutputStream out = new FileOutputStream("generated_classes/ASMEventBus.class")) {
			out.write(data);
		}
		Class<?> asmEventbus = loader.defineClass("net.minecraftborge.loader.event.ASMEventBus", data);
		System.out.println("ASMEventBus created (" + data.length + " bytes)");
		EventBus.defineEventBus(asmEventbus);
	}

	public static void addEventMethod(ClassWriter cw, Class<? extends Event> eventClass, List<MethodASMData> subscribers) {
		subscribers.sort((method1, method2) -> {
			Map<String, Object> data1 = method1.annotations.get(EventHandler.class.getName());
			if (data1 == null) return 0;
			EventPriority priority1 = EventPriority.read(data1.get("value"));
			Map<String, Object> data2 = method2.annotations.get(EventHandler.class.getName());
			if (data2 == null) return 0;
			EventPriority priority2 = EventPriority.read(data2.get("value"));
			return Integer.compare(priority2.ordinal(), priority1.ordinal());
		});
		String eventDescriptor = Type.getDescriptor(eventClass);
		MethodVisitor mv = cw.visitMethod(
				ACC_PUBLIC | ACC_STATIC,
				"push",
				"(" + eventDescriptor + ")V",
				null,
				null
		);
		mv.visitCode();
		for (MethodASMData method : subscribers) {
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(
					INVOKESTATIC,
					method.owner.replace('.', '/'),
					method.name,
					method.descriptor,
					false
			);
		}
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
	}

	public static void emptyCtor(ClassWriter cw) {
		MethodVisitor mv = cw.visitMethod(
				ACC_PRIVATE,
				"<init>",
				"()V",
				null,
				null
		);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(
				INVOKESPECIAL,
				"java/lang/Object",
				"<init>",
				"()V",
				false
		);
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
	}
}
