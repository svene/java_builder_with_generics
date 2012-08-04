package org.svenehrke.builderpattern.implementation.generics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FooTest {
	@Test
	public void exerciseWithRequiredsOnly() {
		Foo foo = new Foo(Foo.newBuilder().Required2(2).Required1(1));
		assertEquals(1, foo.getRequired1());
		assertEquals(2, foo.getRequired2());

		assertEquals(0, foo.getOptional1());
		assertEquals(0, foo.getOptional2());
		assertEquals(0, foo.getOptional3());
	}

	@Test
	public void exerciseWithStandardInitializationSequence() {
		Foo foo = new Foo(Foo.newBuilder().Required1(1).Required2(2).optional2(2));
		assertInitialization(1, 2, 0, 2, 0, foo);
	}

	@Test
	public void exerciseWithRequiredsMixedWithOptionals() {
		Foo foo = new Foo(Foo.newBuilder().Required1(1).optional1(1).optional2(2).optional3(3).Required2(2));
		assertInitialization(1, 2, 1, 2, 3, foo);
	}

	@Test
	public void exerciseWithOptionalsFirst() {
		Foo foo = new Foo(Foo.newBuilder().optional1(3).Required1(1).Required2(2));
		assertInitialization(1, 2, 3, 0, 0, foo);
	}

	private void assertInitialization(int r1, int r2, int o1, int o2, int o3, Foo foo) {
		assertEquals(r1, foo.getRequired1());
		assertEquals(r2, foo.getRequired2());

		assertEquals(o1, foo.getOptional1());
		assertEquals(o2, foo.getOptional2());
		assertEquals(o3, foo.getOptional3());
	}
}
