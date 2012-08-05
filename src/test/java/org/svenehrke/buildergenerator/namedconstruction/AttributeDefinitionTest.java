package org.svenehrke.buildergenerator.namedconstruction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AttributeDefinitionTest {

	@Test
	public void parse() throws Exception {
		AttributeDefinition ad = AttributeDefinition.parse("R,String,required1");
		assertTrue(ad.isRequired());
		assertEquals("String", ad.getType());
		assertEquals("required1", ad.getName());
	}

	@Test
	public void parse_error() throws Exception {
		try {
			AttributeDefinition.parse("");
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("String '' does not consist of 3 parts separated by commas", e.getMessage());
		}
		try {
			AttributeDefinition.parse("R,t");
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("String 'R,t' does not consist of 3 parts separated by commas", e.getMessage());
		}
		try {
			AttributeDefinition.parse("R,t,");
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("String 'R,t,' does not consist of 3 parts separated by commas", e.getMessage());
		}
		try {
			AttributeDefinition.parse(",t,n");
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("String ',t,n': first parameter must be 'R' or 'O'", e.getMessage());
		}
		try {
			AttributeDefinition.parse("R,,n");
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("String 'R,,n': second parameter invalid", e.getMessage());
		}
	}

}
