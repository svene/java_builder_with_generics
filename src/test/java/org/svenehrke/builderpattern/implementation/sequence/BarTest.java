package org.svenehrke.builderpattern.implementation.sequence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BarTest {
	@Test
	public void exerciseWithRequiredsOnly() {
		Bar bar = Bar.newBuilder().initR1(1).initR2(2).build();
		assertEquals(1, bar.getR1());
		assertEquals(2, bar.getR2());

		assertEquals(0, bar.getO1());
		assertEquals(0, bar.getO2());
		assertEquals(0, bar.getO3());
	}

	@Test
	public void exerciseWithStandardInitializationSequence() {
		Bar bar = Bar.newBuilder().initR1(1).initR2(2).withO1(1).withO2(2).withO3(3).build();
		assertDataInitializedCorrectly(bar);
	}

	@Test
	public void exerciseWithFreeSequenceForOptionals() {
		Bar bar = Bar.newBuilder().initR1(1).initR2(2).withO2(2).withO1(1).withO3(3).build();
		assertDataInitializedCorrectly(bar);
	}

	@Test
	public void exerciseWithFreeSequenceForOptionals2() {
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
