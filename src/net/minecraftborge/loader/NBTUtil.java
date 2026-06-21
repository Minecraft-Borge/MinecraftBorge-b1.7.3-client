package net.minecraftborge.loader;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;

import java.util.*;

/**
 * Utility class for NBT related conversions
 */
public class NBTUtil {
	public static void convertToIntMap(NBTTagCompound nbt, Map<String, Integer> map) {
		for (String key : nbt.keySet()) {
			map.put(key, nbt.getInteger(key));
		}
	}
	public static Map<String, Integer> convertToIntMap(NBTTagCompound nbt) {
		Map<String, Integer> map = new HashMap<>();
		convertToIntMap(nbt, map);
		return map;
	}

	public static void convertFromIntMap(Map<String, Integer> map, NBTTagCompound nbt) {
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			if (value == null) System.out.println("Null entry " + key);
			else nbt.setInteger(key, value);
		}
	}
	public static NBTTagCompound convertFromIntMap(Map<String, Integer> map) {
		NBTTagCompound nbt = new NBTTagCompound();
		convertFromIntMap(map, nbt);
		return nbt;
	}

	public static List<String> convertToStringList(NBTTagList nbt, boolean discardEmpty) {
		ArrayList<String> list = new ArrayList<>(nbt.tagCount());
		for (int i = 0; i < nbt.tagCount(); i++) {
			String str = nbt.stringAt(i);
			if (!discardEmpty || !str.trim().isEmpty()) list.add(nbt.stringAt(i));
		}
		list.trimToSize();
		return list;
	}
	public static NBTTagList convertFromStringList(List<String> list, boolean discardEmpty) {
		NBTTagList nbt = new NBTTagList(list.size());
		for (String str : list) {
			if (!discardEmpty || !str.trim().isEmpty()) nbt.appendTag(new NBTTagString(str));
		}
		nbt.trimToSize();
		return nbt;
	}
}
