package org.svenehrke.builderpattern.implementation.typedconstruction.lib;

public class OptionalParameter<T, V> {
	private V value;

	public T setValue(V value) {
		this.value = value;
		return (T)this;
	}

	public static <V, T extends OptionalParameter<?, V>> V valueOf(T t) {
		return t == null ? null : t.value;
	}
}
