package org.svenehrke.builderpattern.implementation.typedconstruction;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.svenehrke.builderpattern.implementation.typedconstruction.TCFoo.*;

public class TCFooTest {

	@Test
	public void test_constructor_contracts() throws Exception {
		try{
			new TCFoo(null, null, null, null, null, null);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'r1' must not be null"));
		}
		try{
			new TCFoo(null, required2(2), null, null, null, null);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'r1' must not be null"));
		}
		try{
			new TCFoo(required1(null), required2(2), null, null, null, null);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'r1' must not be null"));
		}
		try{
			new TCFoo(required1("1"), null, null, null, null, null);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'r2' must not be null"));
		}
		try{
			new TCFoo(required1("1"), required2(null), null, null, null, null);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'r2' must not be null"));
		}
		try{
			new TCFoo(required1("1"), required2(2), null, null, null, null);
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("parameter 'r3' must not be null"));
		}

		// No exception expected:
		new TCFoo(required1("1"), required2(2), required3(3), null, null, null);
	}

	@Test
	public void exercise_with_requireds_only() throws Exception {
		TCFoo foo = new TCFoo(required1("1"), required2(2), required3(3), null, null, null);
		verifyInitialization("1", 2, 3, null, null, null, foo);
	}


	@Test
	public void exercise_with_optionals() throws Exception {
		TCFoo foo = new TCFoo(required1("1"), required2(2), required3(3), optional1("o1"), optional2("o2"), optional3("o3"));
		verifyInitialization("1", 2, 3, "o1", "o2", "o3", foo);
	}

	private void verifyInitialization(String r1, Integer r2, long r3, String o1, String o2, String o3, TCFoo foo) {
		assertEquals(r1, foo.getRequired1());
		assertEquals(r2, foo.getRequired2());
		assertEquals(r3, foo.getRequired3());

		assertEquals(o1, foo.getOptional1());
		assertEquals(o2, foo.getOptional2());
		assertEquals(o3, foo.getOptional3());
	}
}