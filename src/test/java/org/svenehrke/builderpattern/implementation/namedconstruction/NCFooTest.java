package org.svenehrke.builderpattern.implementation.namedconstruction;

import org.junit.Test;

import static org.junit.Assert.*;

public class NCFooTest {

	@Test
	public void excercise_invalid_construction() throws Exception {
		try {
			new NCFoo(null, null, null, null, null, null);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'required1' must not be null"));
		}
		try {
			new NCFoo("1", null, null, null, null, null);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'required2' must not be null"));
		}
		try {
			new NCFoo("1", "2", null, null, null, null);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'required3' must not be null"));
		}

		// No exception expected:
		new NCFoo("1", "2", "3", null, null, null);
	}

	@Test
	public void exerciseWithRequiredsOnly() {
		NCFoo foo = NCFoo.required1("1").required2("2").required3("3");
		verifyInitialization("1", "2", "3", null, null, null, foo);
	}

	@Test
	public void exerciseWithOptionals() {
		NCFoo foo = NCFoo.required1("1").required2("2").required3("3").newOptional1("o1").newOptional2("o2").newOptional3("o3");
		verifyInitialization("1", "2", "3", "o1", "o2", "o3", foo);
	}

	@Test
	public void create_new_from_existing_is_always_new_object() {
		NCFoo foo, foo1, foo2, foo3, foo4, foo5;
		foo = NCFoo.required1("1").required2("2").required3("3").newOptional1("o1").newOptional2("o2").newOptional3("o3");

		assertNotSame(foo, foo1 = foo.newRequired1("1a"));
		assertNotSame(foo1, foo2 = foo1.newRequired2("2a"));
		assertNotSame(foo2, foo3 = foo2.newRequired3("3a"));
		assertNotSame(foo3, foo4 = foo3.newOptional1("o1a"));
		assertNotSame(foo4, foo5 = foo4.newOptional2("o2a"));
		assertNotSame(foo5, foo4.newOptional3("o3a"));
	}

	@Test
	public void create_new_from_existing() {
		NCFoo foo, foo1, foo2, foo3, foo4, foo5;
		foo = NCFoo.required1("1").required2("2").required3("3").newOptional1("o1").newOptional2("o2").newOptional3("o3");
		verifyInitialization("1a", "2", "3", "o1", "o2", "o3", foo1 = foo.newRequired1("1a"));
		verifyInitialization("1a", "2a", "3", "o1", "o2", "o3", foo2 = foo1.newRequired2("2a"));
		verifyInitialization("1a", "2a", "3a", "o1", "o2", "o3", foo3 = foo2.newRequired3("3a"));
		verifyInitialization("1a", "2a", "3a", "o1a", "o2", "o3", foo4 = foo3.newOptional1("o1a"));
		verifyInitialization("1a", "2a", "3a", "o1a", "o2a", "o3", foo5 = foo4.newOptional2("o2a"));
		verifyInitialization("1a", "2a", "3a", "o1a", "o2a", "o3a", foo5.newOptional3("o3a"));
	}

	private void verifyInitialization(String r1, String r2, String r3, String o1, String o2, String o3, NCFoo foo) {
		assertEquals(r1, foo.getRequired1());
		assertEquals(r2, foo.getRequired2());
		assertEquals(r3, foo.getRequired3());

		assertEquals(o1, foo.getOptional1());
		assertEquals(o2, foo.getOptional2());
		assertEquals(o3, foo.getOptional3());
	}


}