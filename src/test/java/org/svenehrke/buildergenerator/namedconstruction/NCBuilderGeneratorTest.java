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
	@Test
	public void generate_Foo_with_2required_3optional() throws Exception {
		assertEquals(
			toText(
				outClassLine("Foo"),
				outAttributeLine("String", "required1"),
				outAttributeLine("int", "required2"),
				outAttributeLine("BigDecimal", "optional1"),
				outAttributeLine("BigInteger", "optional2"),
				outAttributeLine("double", "optional3"),
				outEndLine()
			),
			new NCBuilderGenerator("Foo"
				, "R,String,required1"
				, "R,int,required2"
				, "O,BigDecimal,optional1"
				, "O,BigInteger,optional2"
				, "O,double,optional3"
			).generate());
	}

}
