package net.minecraftborge.loader;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;

/**
 * Block rendering abstraction, by allocating a render type in {@link RenderBlocks} a mod can register a custom block model
 */
@FunctionalInterface
public interface BlockRenderType {
	/**
	 * Renders the block to the world
	 * @param world The world to render in
	 * @param block The block to render
	 * @param x Block X-position
	 * @param y Block Y-position
	 * @param z Block Z-position
	 * @param renderer The RenderBlocks instance
	 * @return Whether something was rendered
	 */
	boolean render(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer);

	/**
	 * Renders the block in the inventory.
	 * Only called if {@link BlockRenderType#renderIn3D()} returns true!
	 * @param block The block to render
	 * @param metadata The item metadata
	 * @param brightness The brightness on inventory
	 * @param renderer The RenderBlocks instance
	 */
	default void renderOnInventory(Block block, int metadata, float brightness, RenderBlocks renderer) {}

	/**
	 * @return Whether the item in the inventory should render as a model, or as a flat item texture.
	 */
	default boolean renderIn3D() {
		return false;
	}
}
