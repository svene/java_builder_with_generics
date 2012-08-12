package org.svenehrke.buildergenerator.namedconstruction;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.fail;
import static org.svenehrke.buildergenerator.namedconstruction.NCBuilderGenerator.toText;

import static org.junit.Assert.assertEquals;

public class NCBuilderGeneratorTest {

	@Test
	public void generate_Foo() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"package org.svenehrke;",
				"",
				"/* Source:",
				"org.svenehrke.Foo",
				"*/",
				"public final class Foo {",
				"",
				"\tprivate Foo() {",
				"\t}",
				"}"
			),
			new NCBuilderGenerator("org.svenehrke.Foo").generate());
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
	@Ignore
	public void generate_Foo_with_required() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"package org.svenehrke;",
				"",
				"/* Source:",
				"org.svenehrke.Foo",
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
			new NCBuilderGenerator("org.svenehrke.Foo", "R,String,required1").generate());
	}

	@Test
	public void firstRequiredRoutineText_R1() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"\tpublic static OptionalBuilder Required1(String value) {"
				,"\t\treturn new OptionalBuilder(new Foo(value));"
				,"\t}"
				),

			toText(NCBuilderGenerator.firstRequiredRoutineText(InputData.parse("Foo", "R,String,required1")))
		);
	}

	@Test
	public void firstRequiredRoutineText_R2() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"\tpublic static Builder1 Required1(String value) {"
				,"\t\treturn new Builder1(new Foo(value, \"\"));"
				,"\t}"
				),

			toText(NCBuilderGenerator.firstRequiredRoutineText(InputData.parse(
				"Foo"
				,"R,String,required1"
				,"R,String,required2"
			)))
		);
	}
	@Test
	public void firstRequiredRoutineText_O1() throws Exception {
		try {
			NCBuilderGenerator.firstRequiredRoutineText(InputData.parse("Foo", "O,String,optional1"));
			fail("IllegalArgumentException expected");
		}
		catch (IllegalArgumentException e) {
			assertEquals("at least one required argument required", e.getMessage());
		}
	}

	@Test
	public void builders_R1() throws Exception {

		assertEquals(
			NCBuilderGenerator.toText(
				"",
				"\tpublic static OptionalBuilder Required1(String value) {",
				"\t\treturn new OptionalBuilder(new Foo(value));",
				"\t}",
				optionalBuilderText()
				),
			toText(new NCBuilderGenerator("org.svenehrke.Foo", "R,String,required1").builders())
		);
	}

	@Test
	@Ignore
	public void builder() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(Arrays.asList(
				"\t\tpublic OptionalBuilder Required1(int value) {"
				, "\t\t\treturn new OptionalBuilder(obj.newOptional1(value));"
				, "\t\t}"
			))
			,
			NCBuilderGenerator.builderText(AttributeDefinition.parse("R,int,required2"), "Foo", 1)
		);

	}

	@Test
	public void builders_R2() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"",
				"\tpublic static Builder1 Required1(String value) {",
				"\t\treturn new Builder1(new Foo(value, 0));",
				"\t}",
				"\tpublic static class Builder1 {",
				"\t\tFoo obj;",
				"\t\tpublic Builder1(Foo obj) {",
				"\t\t\tthis.obj = obj;",
				"\t\t}",
				"\t\tpublic OptionalBuilder Required2(int value) {",
				"\t\t\treturn new OptionalBuilder(obj.newRequired2(value));",
				"\t\t}",
				"\t}",
				optionalBuilderText()
			),
			toText(new NCBuilderGenerator("org.svenehrke.Foo", "R,String,required1", "R,int,required2").builders())
		);
	}
	@Test
	public void testOptionalBuilderRoutine() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(Arrays.asList(
				"\t\tpublic OptionalBuilder Optional1(int value) {"
				, "\t\t\treturn new OptionalBuilder(obj.newOptional1(value));"
				, "\t\t}"
			))
			,
			NCBuilderGenerator.optionalBuilderRoutineText(AttributeDefinition.parse("O,int,optional1"))
		);

		assertEquals(
			NCBuilderGenerator.toText(Arrays.asList(
				"\t\tpublic OptionalBuilder Optional3(BigDecimal value) {"
				, "\t\t\treturn new OptionalBuilder(obj.newOptional3(value));"
				, "\t\t}"
			))
			,
			NCBuilderGenerator.optionalBuilderRoutineText(AttributeDefinition.parse("O,BigDecimal,optional3"))
		);

	}

	@Test
	public void builders_R2O1() throws Exception {
		assertEquals(
			NCBuilderGenerator.toText(
				"",
				"\tpublic static Builder1 Required1(String value) {",
				"\t\treturn new Builder1(new Foo(value, 0));",
				"\t}",
				"\tpublic static class Builder1 {",
				"\t\tFoo obj;",
				"\t\tpublic Builder1(Foo obj) {",
				"\t\t\tthis.obj = obj;",
				"\t\t}",
				"\t\tpublic OptionalBuilder Required2(int value) {",
				"\t\t\treturn new OptionalBuilder(obj.newRequired2(value));",
				"\t\t}",
				"\t}",
				"\tpublic static class OptionalBuilder {",
				"\t\tFoo obj;",
				"\t\tpublic OptionalBuilder(Foo obj) {",
				"\t\t\tthis.obj = obj;",
				"\t\t}",
				NCBuilderGenerator.optionalBuilderRoutineText(AttributeDefinition.parse("O,int,optional1")),
				"\t\tpublic Foo build() {",
				"\t\t\treturn obj;",
				"\t\t}"
			),
			toText(new NCBuilderGenerator(
				"org.svenehrke.Foo"
				,"R,String,required1"
				,"R,int,required2"
				,"O,int,optional1"
			).builders())
		);
	}

	@Test
	@Ignore
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

	private String optionalBuilderText() {
//	private String optionalBuilderText(AttributeDefinition... aAttributeDefinitions) {
		return NCBuilderGenerator.toText(
			"\tpublic static class OptionalBuilder {",
			"\t\tFoo obj;",
			"\t\tpublic OptionalBuilder(Foo obj) {",
			"\t\t\tthis.obj = obj;",
			"\t\t}",
			"\t\tpublic Foo build() {",
			"\t\t\treturn obj;",
			"\t\t}"
		);
	}

}
