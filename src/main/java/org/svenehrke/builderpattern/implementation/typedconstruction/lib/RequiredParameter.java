package org.svenehrke.builderpattern.implementation.typedconstruction.lib;

public class RequiredParameter<T, V> {
	private V value;

	public T init(String name, V value) {
		if (value == null) {
			throw new IllegalArgumentException("parameter '" + name + "' must not be null");
		}
		this.value = value;
		return (T)this;
	}

	public V getValue() {
		return value;
	}
}
