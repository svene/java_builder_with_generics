package org.svenehrke.buildergenerator.namedconstruction;

import java.util.*;

public class NCBuilderGenerator {
	private final List<String> lines;
	private final InputData inputData;
	private final Map<String, String> defaultValues = new HashMap<String, String>() {{
		put("String", "\"\"");
		put("int", "0");
		put("double", "0.0");
		put("float", "0.0");
		put("char", "'-'");
	}};

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
		if (!inputData.pgkName.isEmpty()) {
			sa.add(String.format("package %s;", inputData.pgkName));
			sa.add("");
		}
		sa.add("/* Source:");
		sa.addAll(lines);
		sa.add("*/");
		sa.add("public final class " + inputData.className + " {");
		sa.add(attributeLines());
		sa.addAll(constructor());
		sa.addAll(getters());
		sa.addAll(newInitilizers());
		sa.addAll(builders());

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
			result += line + (line.endsWith("\n") ? "" : "\n");
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

	List<String> builders() {
		List<String> result = new ArrayList<String>();

		final int cr = inputData.requiredAttributes.size();
		final int co = inputData.optionalAttributes.size();
		final String className = inputData.className;

		if (cr + co == 0) {
			return result;
		}
		result.add("");

		int idx = 0;
		if (cr == 1) {
			final AttributeDefinition ad = inputData.requiredAttributes.get(idx);

			String s = NCBuilderGenerator.toText(
				"\tpublic static OptionalBuilder ATTRIBUTE(TYPE value) {",
				"\t\treturn new OptionalBuilder(new CLASSNAME(value));",
				"\t}"
			);
			s = s
				.replaceAll("ATTRIBUTE", ad.getCapitalizedName())
				.replaceAll("TYPE", ad.getType())
				.replaceAll("CLASSNAME", className);

			result.add(s);
		}
		else if (cr > 1) {
			// First one gets a static creation routine:
			if (idx == 0) {
				final AttributeDefinition ad = inputData.requiredAttributes.get(0);
				String s = NCBuilderGenerator.toText(
					"\tpublic static BUILDER ATTRIBUTE(TYPE value) {",
					"\t\treturn new BUILDER(new CLASSNAME(valueDEFAULT_VALUES));",
					"\t}"
				);
				s = s
					.replaceAll("ATTRIBUTE", ad.getCapitalizedName())
					.replaceAll("TYPE", ad.getType())
					.replaceAll("CLASSNAME", className)
					.replaceAll("DEFAULT_VALUES",
						defaultValuesForAttributes(inputData.requiredAttributes.subList(1, inputData.requiredAttributes.size())))
					.replaceAll("BUILDER", "Builder" + Integer.toString(idx + 2));

				result.add(s);
			}
			// for all attributes except first and last:
			for (AttributeDefinition ad : inputData.attributeDefinitions.subList(1, cr - 1)) {
				String s = NCBuilderGenerator.toText(
					 "\tpublic static class BUILDER {"
					,"\t\tCLASSNAME obj;"
					,"\t\tpublic BUILDER(CLASSNAME obj) {"
					,"\t\t\tthis.obj = obj;"
					,"\t\t}"
					,"\tpublic NEXT_BUILDER ATTRIBUTE(TYPE value) {"
					,"\t\treturn new NEXT_BUILDER(new CLASSNAME(value));"
					,"\t}"
				);
				s = s
					.replaceAll("ATTRIBUTE", ad.getCapitalizedName())
					.replaceAll("TYPE", ad.getType())
					.replaceAll("CLASSNAME", className)
					.replaceAll("BUILDER", "Builder" + Integer.toString(idx + 2))
					.replaceAll("NEXT_BUILDER", "Builder" + Integer.toString(idx + 3));

				result.add(s);
			}
			// Handle last attribute:
			final AttributeDefinition ad = inputData.requiredAttributes.get(cr - 1);
			String s = NCBuilderGenerator.toText(
				 "\tpublic static class BUILDER {"
				,"\t\tCLASSNAME obj;"
				,"\t\tpublic BUILDER(CLASSNAME obj) {"
				,"\t\t\tthis.obj = obj;"
				,"\t\t}"
				,"\t\tpublic OptionalBuilder ATTRIBUTE(TYPE value) {"
				,"\t\t\treturn new OptionalBuilder(obj.newATTRIBUTE(value));"
				,"\t\t}"
				,"\t}"
			);
			s = s
				.replaceAll("ATTRIBUTE", ad.getCapitalizedName())
				.replaceAll("TYPE", ad.getType())
				.replaceAll("CLASSNAME", className)
				.replaceAll("BUILDER", "Builder" + Integer.toString(idx + 2));

			result.add(s);
		}
		result.add(String.format("\tpublic static class OptionalBuilder {"));
		result.add(String.format("\t\tFoo obj;"));
		result.add(String.format("\t\tpublic OptionalBuilder(%s obj) {", className));
		result.add(String.format("\t\t\tthis.obj = obj;"));
		result.add(String.format("\t\t}"));
		result.add(String.format("\t\tpublic Foo build() {"));
		result.add(String.format("\t\t\treturn obj;"));
		result.add(String.format("\t\t}"));

		return result;
	}

	private String defaultValuesForAttributes(List<AttributeDefinition> attributes) {
		String result = "";
		for (AttributeDefinition ad : attributes) {
			result += ", " + defaultValues.get(ad.getType());
		}
		return result;
	}

	private List<String> builderForLastRequired(String aClassName, final AttributeDefinition ad) {
		List<String> result = new ArrayList<String>();

		result.add(String.format("\tpublic static OptionalBuilder %s(%s value) {", ad.getName(), ad.getType()));
		result.add(String.format("\t\treturn new OptionalBuilder(new %s(value));", aClassName));
		result.add(String.format("\t}"));

		return result;
	}

}
