package net.minecraftborge.loader;

/**
 * Abstracted representation of a tool material
 */
public interface IToolMaterial {
	int getDurability();
	float getMiningSpeed();
	int getAttackDamage();
	int getToolLevel();
}
