package org.svenehrke.builderpattern.implementation.generics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FooTest {
	@Test
	public void exerciseWithRequiredsOnly() {
//		Foo7 foo = new Foo(Foo.Builder.newInstance().initR1(1).initR2(2));
		Foo foo = new Foo(Foo.newBuilder().initR2(2).initR1(1));
		assertEquals(1, foo.getR1());
		assertEquals(2, foo.getR2());

		assertEquals(0, foo.getO1());
		assertEquals(0, foo.getO2());
		assertEquals(0, foo.getO3());
	}

	@Test
	public void exerciseWithStandardInitializationSequence() {
//		Foo foo = new Foo(Foo.newBuilder().initR1(1).initR2(2).withO1(1).withO2(2).withO3(3));
		Foo foo = new Foo(Foo.newBuilder().initR1(1).initR2(2).withO2(2));
		assertEquals(1, foo.getR1());
		assertEquals(2, foo.getR2());

		assertEquals(2, foo.getO2());
	}

	@Test
	public void exerciseWithRequiredsMixedWithOptionals() {
//		Foo foo = new Foo(Foo.Builder.newInstance().initR1(1).withO1(1).withO2(2).withO3(3)); // won't compile
		Foo foo = new Foo(Foo.newBuilder().initR1(1).withO1(1).withO2(2).withO3(3).initR2(2));
		// foo = new Foo(Foo.Builder.newInstance().initR2(1).withO1(1).initR1(1).withO2(2).withO3(3)); // works
		// foo = new Foo(Foo.Builder.newInstance().initR2(1).withO1(1).initR1(1).withO2(2).withO3(3).withO3(3)); // works
		assertEquals(1, foo.getR1());
		assertEquals(2, foo.getR2());

		assertEquals(1, foo.getO1());
		assertEquals(2, foo.getO2());
		assertEquals(3, foo.getO3());

	}

	@Test
	public void exerciseWithOptionalsFirst() {
		Foo foo = new Foo(Foo.newBuilder().withO1(3).initR1(1).initR2(2));
		assertEquals(1, foo.getR1());
		assertEquals(2, foo.getR2());

		assertEquals(3, foo.getO1());
	}
}
