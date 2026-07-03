package net.minecraftborge.loader.math;

public class Vector3f implements Vector3fc {
	public float x, y, z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3f() {
		this(0.0F, 0.0F, 0.0F);
	}
	public Vector3f(Vector3fc vec) {
		this(vec.x(), vec.y(), vec.z());
	}

	@Override
	public float x() {
		return this.x;
	}
	@Override
	public float y() {
		return this.y;
	}
	@Override
	public float z() {
		return this.z;
	}

	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	public Vector3f set(Vector3fc vec) {
		return this.set(vec.x(), vec.y(), vec.z());
	}
}
