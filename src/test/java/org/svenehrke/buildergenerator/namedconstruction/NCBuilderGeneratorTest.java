package org.svenehrke.buildergenerator.namedconstruction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NCBuilderGeneratorTest {

	@Test
	public void generate_Foo() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"/* Source:",
				"Foo",
				"*/",
				"public final class Foo {",
				"",
				"\tprivate Foo() {",
				"\t}",
				"}"
			),
			new NCBuilderGenerator("Foo").generate());
	}

	@Test
	public void generate_Bar() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"/* Source:",
				"Bar",
				"*/",
				"public final class Bar {",
				"",
				"\tprivate Bar() {",
				"\t}",
				"}"
			),
			new NCBuilderGenerator("Bar").generate()
		);
	}

	@Test
	public void generate_Foo_with_required() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"/* Source:",
				"Foo",
				"R,String,required1",
				"*/",
				"public final class Foo {",
				"\tprivate final String required1;",
				"",
				"\tprivate Foo(String required1) {",
				"\t\tif (required1 == null) {",
				"\t\t\tthrow new IllegalArgumentException(\"parameter 'required1' must not be null\");",
				"\t\t}",
				"\t\tthis.required1 = required1;",
				"\t}",
				"",
				"\tpublic String getRequired1() { return required1; }",
				"",
				"\tpublic Foo newRequired1(String value) {",
				"\t\treturn new Foo(value);",
				"\t}",
				"",
				"\tpublic static Builder2 required1(String value) {",
				"\t\treturn new Builder2(new Foo(value, \"\", \"\"));",
				"\t}",

				"}"
			),
			new NCBuilderGenerator("Foo", "R,String,required1").generate());
	}
	@Test
	public void generate_Foo_with_2required_3optional() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"/* Source:",
				"Foo",
				"R,String,required1",
				"R,int,required2",
				"O,BigDecimal,optional1",
				"O,BigInteger,optional2",
				"O,double,optional3",
				"*/",
				"public final class Foo {",
				"\tprivate final String required1;",
				"\tprivate final int required2;",
				"\tprivate final BigDecimal optional1;",
				"\tprivate final BigInteger optional2;",
				"\tprivate final double optional3;",
				"",
				"\tprivate Foo(String required1, int required2, BigDecimal optional1, BigInteger optional2, double optional3) {",
				"\t\tif (required1 == null) {",
				"\t\t\tthrow new IllegalArgumentException(\"parameter 'required1' must not be null\");",
				"\t\t}",
				"\t\tthis.required1 = required1;",
				"\t\tthis.required2 = required2;",
				"\t\tthis.optional1 = optional1;",
				"\t\tthis.optional2 = optional2;",
				"\t\tthis.optional3 = optional3;",
				"\t}",
				"",
				"\tpublic String getRequired1() { return required1; }",
				"\tpublic int getRequired2() { return required2; }",
				"\tpublic BigDecimal getOptional1() { return optional1; }",
				"\tpublic BigInteger getOptional2() { return optional2; }",
				"\tpublic double getOptional3() { return optional3; }",
				"",
				"\tpublic Foo newRequired1(String value) {",
				"\t\treturn new Foo(value, required2, optional1, optional2, optional3);",
				"\t}",
				"\tpublic Foo newRequired2(int value) {",
				"\t\treturn new Foo(required1, value, optional1, optional2, optional3);",
				"\t}",
				"\tpublic Foo newOptional1(BigDecimal value) {",
				"\t\treturn new Foo(required1, required2, value, optional2, optional3);",
				"\t}",
				"\tpublic Foo newOptional2(BigInteger value) {",
				"\t\treturn new Foo(required1, required2, optional1, value, optional3);",
				"\t}",
				"\tpublic Foo newOptional3(double value) {",
				"\t\treturn new Foo(required1, required2, optional1, optional2, value);",
				"\t}",
				"",
				"\tpublic static Builder2 required1(String value) {",
				"\t\treturn new Builder2(new Foo(value, \"\", \"\"));",
				"\t}",
				"\tpublic static class Builder2 {",
				"\t\tFoo obj;",
				"\t\tpublic Builder2(Foo obj) {",
				"\t\t\tthis.obj = obj;",
				"\t\t}",
				"\t}",
				"}"
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
