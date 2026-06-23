package net.minecraftborge.loader;

import net.minecraft.src.*;

import java.util.Random;

public class ContainerUtil {
	public static void dropContents(World world, double x, double y, double z, IInventory inventory, Random rand) {
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null) {
				double ix = x + rand.nextFloat() * 0.8F + 0.1F;
				double iy = y + rand.nextFloat() * 0.8F + 0.1F;
				double iz = z + rand.nextFloat() * 0.8F + 0.1F;

				while (stack.stackSize > 0) {
					int size = Math.min(stack.stackSize, rand.nextInt(21) + 10);
					stack.stackSize -= size;

					Entity entity = stack.getItem().createDroppedEntity(world, ix, iy, iz, new ItemStack(stack.itemID, size, stack.getItemDamage()));
					entity.motionX = rand.nextGaussian() * 0.05F;
					entity.motionY = rand.nextGaussian() * 0.05F + 0.2F;
					entity.motionZ = rand.nextGaussian() * 0.05F;
					world.entityJoinedWorld(entity);
				}
			}
		}
	}
}
