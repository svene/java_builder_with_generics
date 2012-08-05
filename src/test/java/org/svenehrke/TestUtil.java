package org.svenehrke;

import java.io.*;

public class TestUtil {
	public static String getTextfileContent(String resourcePath) {
		try {
			final InputStream is = TestUtil.class.getClassLoader().getResourceAsStream(resourcePath);
			if (is == null) {
				throw new RuntimeException(String.format("could not find resource '%s'", resourcePath));
			}
			/*
			* To convert the InputStream to String we use the
			* Reader.read(char[] buffer) method. We iterate until the
			* Reader return -1 which means there's no more data to
			* read. We use the StringWriter class to produce the string.
			*/
			Writer writer = new StringWriter();

			try {
				Reader reader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));
				int n;
				char[] buffer = new char[1024];
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(String.format("could not load text for resource '%s'", resourcePath), e);
		}
	}

	public static BufferedReader toBufferedReader(final String input) {
		return new BufferedReader(new StringReader(input));
	}

	public static String toText(String... lines) {
		String result = "";
		for (String line : lines) {
			result += line + "\n";
		}
		return result;
	}


	public static String outClassLine(String aClassname) {
		return "public final class " + aClassname + " {";
	}
	public static String outAttributeLine(String aType, String aAttributeName) {
		return String.format("\tprivate final %s %s;", aType, aAttributeName);
	}
	public static String outEndLine() {
		return "}";
	}
}
