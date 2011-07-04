package org.svenehrke.builderpattern.implementation.sequence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BarTest {
	@Test
	public void test1() {
//		Bar bar = Bar.newBuilder().initR2(2).initR1(1).build(); // won't compile
		Bar bar = Bar.newBuilder().initR1(1).initR2(2).build();
		assertEquals(1, bar.getR1());
		assertEquals(2, bar.getR2());

		assertEquals(0, bar.getO1());
		assertEquals(0, bar.getO2());
		assertEquals(0, bar.getO3());
	}

	@Test
	public void test2() {
		Bar bar = Bar.newBuilder().initR1(1).initR2(2).withO1(1).withO2(2).withO3(3).build();
		assertDataInitializedCorrectly(bar);
	}

	@Test
	public void test3() {
		Bar bar = Bar.newBuilder().initR1(1).initR2(2).withO2(2).withO1(1).withO3(3).build();
		assertDataInitializedCorrectly(bar);
	}

	@Test
	public void test4() {
		Bar bar = Bar.newBuilder().initR1(1).initR2(2).withO3(3).withO2(2).withO1(1).build();
		assertDataInitializedCorrectly(bar);
	}

	private void assertDataInitializedCorrectly(Bar aBar) {
		assertEquals(1, aBar.getR1());
		assertEquals(2, aBar.getR2());

		assertEquals(1, aBar.getO1());
		assertEquals(2, aBar.getO2());
		assertEquals(3, aBar.getO3());
	}
}
