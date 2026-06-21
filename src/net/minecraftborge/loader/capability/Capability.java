package net.minecraftborge.loader.capability;

public class Capability<T> {
	private final Class<T> clazz;
	private final String name;

	public Capability(Class<T> clazz, String name) {
		this.clazz = clazz;
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Capability)) return false;
		Capability<?> cap = (Capability<?>) obj;
		return cap.clazz == this.clazz && this.name.equals(cap.name);
	}

	@Override
	public String toString() {
		return this.name + "[" + this.clazz.getName() + "]";
	}

	public String getName() {
		return this.name;
	}

	public T cast(Object obj) {
		return this.clazz.cast(obj);
	}
}
