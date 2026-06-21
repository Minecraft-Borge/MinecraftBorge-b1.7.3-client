package net.minecraftborge.loader;

import net.minecraft.src.Achievement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Achievement list abstraction, allows for mods to implement their own achievement screens easily.
 * Must be registered on mod initialization.
 */
public abstract class AchievementListBase {
	private static final List<AchievementListBase> ALL_ACHIEVEMENT_LISTS = new ArrayList<>();
	private static final List<AchievementListBase> IMMUTABLE_VIEW = Collections.unmodifiableList(ALL_ACHIEVEMENT_LISTS);

	public static List<AchievementListBase> getAchievementLists() {
		return IMMUTABLE_VIEW;
	}
	public static void addAchievementList(AchievementListBase list) {
		ALL_ACHIEVEMENT_LISTS.add(list);
	}

	protected AchievementListBase() {
		addAchievementList(this);
	}

	public int minColumn;
	public int minRow;
	public int maxColumn;
	public int maxRow;
	public final List<Achievement> list = new ArrayList<>();

	public abstract String getName();
}
