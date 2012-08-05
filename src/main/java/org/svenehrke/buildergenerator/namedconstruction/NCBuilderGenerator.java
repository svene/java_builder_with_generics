package org.svenehrke.buildergenerator.namedconstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NCBuilderGenerator {
	private final List<String> lines;
	private final InputData inputData;

	// For testing:
	NCBuilderGenerator(String... lines) {
		this(Arrays.asList(lines));
	}
	public NCBuilderGenerator(List<String> lines) {
		this.lines = lines;
		inputData = InputData.parse(lines);
	}

	public String generate() {
		List<String> sa = new ArrayList<String>();
		sa.add("/* Source:");
		sa.addAll(lines);
		sa.add("*/");
		sa.add("public final class " + inputData.className + " {");
		sa.add(attributeLines());
		sa.addAll(constructor());
		sa.add("}");
		return toText(sa);
	}

	private List<String> constructor() {
		List<String> sa = new ArrayList<String>();
		String s = String.format("\tprivate %s(", inputData.className);

		// Field declarations:
		int idx = 0;
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			s += String.format("%s%s %s", idx > 0 ? ", " : "", ad.getType(), ad.getName());
			idx++;
		}
		s += ") {";
		sa.add(s);

		// NotNull precondition:
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			if (!ad.isRequired() || ad.isPrimitive()) continue;
			sa.add(String.format("\t\tif (%s == null) {", ad.getName()));
			sa.add(String.format("\t\t\tthrow new IllegalArgumentException(\"parameter '%s' must not be null\");", ad.getName()));
			sa.add(String.format("\t\t}"));
			idx++;
		}
		sa.add("\t}");

		return sa;
	}

	private String attributeLines() {
		String result = "";
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			result += String.format("\tprivate final %s %s;\n", ad.getType(), ad.getName());
		}
		return result;
	}

	public static String toText(String... lines) {
		return toText(Arrays.asList(lines));
	}

	public static String toText(List<String> lines) {
		String result = "";
		for (String line : lines) {
			result += line + "\n";
		}
		return result;
	}

}
