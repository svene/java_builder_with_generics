package org.svenehrke.buildergenerator.namedconstruction;

import org.junit.Test;
import static org.svenehrke.TestUtil.*;

import static org.junit.Assert.assertEquals;

public class NCBuilderGeneratorTest {

	@Test
	public void test_outClassLine() throws Exception {
		assertEquals("public final class Foo {", outClassLine("Foo"));
	}

	@Test
	public void generate_Foo() throws Exception {
		assertEquals(toText(outClassLine("Foo"), outEndLine()), new NCBuilderGenerator("Foo").generate());
	}

	@Test
	public void generate_Bar() throws Exception {
		assertEquals(toText(outClassLine("Bar"), outEndLine()), new NCBuilderGenerator("Bar").generate());
	}

	@Test
	public void generate_Foo_with_required() throws Exception {
		assertEquals(
			toText(
				outClassLine("Foo"),
				outAttributeLine("String", "required1"),
				outEndLine()
			),
			new NCBuilderGenerator("Foo", "R,String,required1").generate());
	}

}
