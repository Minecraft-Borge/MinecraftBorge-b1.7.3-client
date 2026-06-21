package net.minecraftborge.loader;

import net.minecraft.src.TileEntity;

public interface ITickingTile extends ITickable {
	@Override
	default void update() {
		((TileEntity)this).updateEntity();
	}
}
