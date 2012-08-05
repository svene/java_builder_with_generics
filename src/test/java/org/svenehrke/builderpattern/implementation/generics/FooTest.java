package org.svenehrke.builderpattern.implementation.generics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class FooTest {
	@Test
	public void example_with_requireds_only() {
		verifyInitialization(1, 2, 0, 0, 0, new Foo(Foo.newBuilder().Required2(2).Required1(1)));
	}

	@Test
	public void example_with_standard_initialization_sequence() {
		verifyInitialization(1, 2, 0, 2, 0, new Foo(Foo.newBuilder().Required1(1).Required2(2).optional2(2)));
	}

	@Test
	public void example_with_requireds_mixed_with_optionals() {
		Foo foo = new Foo(Foo.newBuilder().Required1(1).optional1(1).optional2(2).optional3(3).Required2(2));
		verifyInitialization(1, 2, 1, 2, 3, foo);
	}

	@Test
	public void exerciseWithOptionalsFirst() {
		Foo foo = new Foo(Foo.newBuilder().optional1(3).Required1(1).Required2(2));
		verifyInitialization(1, 2, 3, 0, 0, foo);
	}

	@Test
	public void create_new_from_existing_always_returns_new_object() {
		Foo foo, foo1, foo2, foo3, foo4;
		foo = new Foo(Foo.newBuilder().optional1(3).Required1(1).Required2(2));

		assertNotSame(foo, foo1 = new Foo(Foo.newBuilder(foo).Required1(10)));
		assertNotSame(foo1, foo2 = new Foo(Foo.newBuilder(foo1).Required2(20)));
		assertNotSame(foo2, foo3 = new Foo(Foo.newBuilder(foo2).optional1(30)));
		assertNotSame(foo3, foo4 = new Foo(Foo.newBuilder(foo3).optional2(40)));
		assertNotSame(foo4, new Foo(Foo.newBuilder(foo4).optional3(50)));
	}

	@Test
	public void create_new_from_existing() {
		Foo foo, foo1, foo2, foo3, foo4;
		foo = new Foo(Foo.newBuilder().Required1(1).Required2(2).optional1(3).optional2(4).optional3(5));

		verifyInitialization(10, 2, 3, 4, 5, foo1 = new Foo(Foo.newBuilder(foo).Required1(10)));
		verifyInitialization(10, 20, 3, 4, 5, foo2 = new Foo(Foo.newBuilder(foo1).Required2(20)));
		verifyInitialization(10, 20, 30, 4, 5, foo3 = new Foo(Foo.newBuilder(foo2).optional1(30)));
		verifyInitialization(10, 20, 30, 40, 5, foo4 = new Foo(Foo.newBuilder(foo3).optional2(40)));
		verifyInitialization(10, 20, 30, 40, 50, new Foo(Foo.newBuilder(foo4).optional3(50)));
	}

	private void verifyInitialization(int r1, int r2, int o1, int o2, int o3, Foo foo) {
		assertEquals(r1, foo.getRequired1());
		assertEquals(r2, foo.getRequired2());

		assertEquals(o1, foo.getOptional1());
		assertEquals(o2, foo.getOptional2());
		assertEquals(o3, foo.getOptional3());
	}

}
