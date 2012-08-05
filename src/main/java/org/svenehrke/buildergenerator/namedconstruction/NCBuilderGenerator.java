package org.svenehrke.buildergenerator.namedconstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
		sa.addAll(getters());
		sa.addAll(newInitilizers());

		sa.add("}");
		return toText(sa);
	}

	private List<String> constructor() {
		List<String> result = new ArrayList<String>();
		String s = String.format("\tprivate %s(", inputData.className);

		// Field declarations:
		int idx = 0;
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			s += String.format("%s%s %s", idx > 0 ? ", " : "", ad.getType(), ad.getName());
			idx++;
		}
		s += ") {";
		result.add(s);

		// NotNull precondition:
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			if (!ad.isRequired() || ad.isPrimitive()) continue;
			result.add(String.format("\t\tif (%s == null) {", ad.getName()));
			result.add(String.format("\t\t\tthrow new IllegalArgumentException(\"parameter '%s' must not be null\");", ad.getName()));
			result.add(String.format("\t\t}"));
			idx++;
		}

		// Field assignments:
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			result.add(String.format("\t\tthis.%s = %s;", ad.getName(), ad.getName()));
		}

		result.add("\t}");

		return result;
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

	public List<String> getters() {
		List<String> result = new ArrayList<String>();
		if (inputData.attributeDefinitions.size() > 0) {
			result.add("");
		}
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			result.add(String.format("\tpublic %s get%s() { return %s; }", ad.getType(), ad.getCapitalizedName(), ad.getName()));
		}
		return result;
	}

	public List<String> newInitilizers() {
		List<String> result = new ArrayList<String>();
		int i = 0;
		if (inputData.attributeDefinitions.size() > 0) {
			result.add("");
		}
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			result.add(String.format("\tpublic %s new%s(%s value) {", inputData.className, ad.getCapitalizedName(), ad.getType()));

			String s = "";
			int j = 0;
			for (AttributeDefinition ad2 : inputData.attributeDefinitions) {
				if (j > 0) s += ", ";
				s += i == j ? "value" : ad2.getName();
				j++;
			}
			result.add(String.format("\t\treturn new %s(%s);", inputData.className, s));
			result.add("\t}");
			i++;
		}
		return result;
	}
}
