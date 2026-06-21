package net.minecraftborge.loader.event;

import net.minecraftborge.MinecraftBorge;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class EventBus {
	private static final Map<Class<? extends Event>, Method> EVENT_TO_ASM_METHOD = new HashMap<>();

	public static <T extends Event> void push(T event) {
		Method method = EVENT_TO_ASM_METHOD.get(event.getClass());
		if (method != null) {
			try {
				method.invoke(null, event);
			} catch (ReflectiveOperationException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void defineEventBus(Class<?> clazz) {
		if (!EVENT_TO_ASM_METHOD.isEmpty()) return;
		if (!clazz.getName().equals("net.minecraftborge.loader.event.ASMEventBus")) throw new IllegalArgumentException("Requires ASMEventBus, got " + clazz);

		System.out.println("Defining event bus using " + clazz);
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getParameterCount() != 1) continue;
			Class<?> param = method.getParameterTypes()[0];
			if (!Event.class.isAssignableFrom(param)) continue;
			EVENT_TO_ASM_METHOD.put((Class<? extends Event>) param, method);
			System.out.println("Event method " + param.getName());
		}
	}

	public static void finalize(Class<? extends Event> clazz) {
		System.err.println("Finalizing event " + MinecraftBorge.formatClassName(clazz));
		EVENT_TO_ASM_METHOD.remove(clazz);
	}
}
