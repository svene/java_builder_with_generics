package org.svenehrke.buildergenerator.namedconstruction;

import org.junit.Test;
import org.svenehrke.TestUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InputDataTest {
	@Test
	public void to_lines() throws Exception {
		assertEquals(1, toLines("Foo").size() );
		assertEquals(1, toLines("Foo\n").size() );
		assertEquals(2, toLines("Foo\nBar").size() );
		assertEquals(2, toLines("Foo\nBar\n").size() );
	}

	private List<String> toLines(final String aString) {
		return InputData.toLines(TestUtil.toBufferedReader(aString));
	}

	@Test
	public void parse_error() throws Exception {
		try {
			assertEquals("Foo", InputData.parse("Foo", "").className);
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("ERROR (line 1): ''", e.getMessage());
		}
		try {
			assertEquals("Foo", InputData.parse("Foo", "R,t").className);
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("ERROR (line 1): 'R,t'", e.getMessage());
		}
		try {
			assertEquals("Foo", InputData.parse("Foo", "R,t,").className);
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("ERROR (line 1): 'R,t,'", e.getMessage());
		}
		try {
			assertEquals("Foo", InputData.parse("Foo", "R,,n").className);
			fail("ParseException expected");
		} catch (ParseException e) {
			assertEquals("ERROR (line 1): 'R,,n'", e.getMessage());
		}
	}
	@Test
	public void testParsing() throws Exception {
		assertEquals("Foo", InputData.parse("Foo").className);
		assertEquals("Bar", InputData.parse("Bar").className);
		assertEquals("Something", InputData.parse("Something").className);
	}


}
