package net.minecraftborge.loader;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public interface IMapLikeRenderer {
	void prepareRender(ItemStack stack, World world);
	void render(Tessellator tes, ItemStack stack, World world, EntityPlayer holder);

	@FunctionalInterface
	interface Factory {
		IMapLikeRenderer create(Minecraft mc);
	}
}
