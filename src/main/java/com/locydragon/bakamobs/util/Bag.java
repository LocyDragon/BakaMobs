package com.locydragon.bakamobs.util;

public class Bag<K,V> {
	private K key;
	private V value;
	public Bag(K key, V v) {
		this.key = key;
		this.value = v;
	}

	public K getKey() {
		return this.key;
	}

	public V getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.equals(this.key)) {
			return true;
		}
		return false;
	}
}
