package net.minecraftborge.loader.event.world;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.World;
import net.minecraftborge.loader.event.Cancelable;

import java.util.List;

@Cancelable
public class CollectBoundingBoxesEvent extends WorldEvent {
	private final List<AxisAlignedBB> list;
	private final AxisAlignedBB collider;
	private final Entity entity; //nullable
	public CollectBoundingBoxesEvent(World world, List<AxisAlignedBB> list, AxisAlignedBB collider, Entity entity) {
		super(world);
		this.list = list;
		this.collider = collider;
		this.entity = entity;
	}

	public List<AxisAlignedBB> getAABBList() {
		return this.list;
	}
	public AxisAlignedBB getCollider() {
		return this.collider;
	}
	public Entity getEntity() {
		return this.entity;
	}
}
