package net.minecraftborge.loader;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public interface IRegistry<T> extends Iterable<T> {
	T getValue(String key);
	String getKey(T value);

	Collection<String> getKeys();
	Collection<T> getValues();

	void register(String key, T value);

	@Override
	default Iterator<T> iterator() {
		return this.getValues().iterator();
	}
	@Override
	default void forEach(Consumer<? super T> action) {
		this.getValues().forEach(action);
	}
	@Override
	default Spliterator<T> spliterator() {
		return this.getValues().spliterator();
	}

	default void registerBase() {}
}
