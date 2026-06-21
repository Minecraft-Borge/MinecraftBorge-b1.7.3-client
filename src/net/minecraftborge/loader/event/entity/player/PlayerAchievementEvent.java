package net.minecraftborge.loader.event.entity.player;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.StatBase;

public class PlayerAchievementEvent extends PlayerEvent {
	private final StatBase achievement;

	public PlayerAchievementEvent(EntityPlayer entity, StatBase achievement) {
		super(entity);
		this.achievement = achievement;
	}

	public StatBase getAchievement() {
		return this.achievement;
	}
}
