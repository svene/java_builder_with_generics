package org.svenehrke.buildergenerator.namedconstruction;

import java.util.Arrays;
import java.util.List;

public class NCBuilderGenerator {
	private final InputData inputData;

	// For testing:
	NCBuilderGenerator(String... lines) {
		this(Arrays.asList(lines));
	}
	public NCBuilderGenerator(List<String> lines) {
		inputData = InputData.parse(lines);
	}

	public String generate() {
		return "public final class " +
			inputData.className +
			" {\n"
			+ attributeLines()
			+ "}\n";
	}

	private String attributeLines() {
		String result = "";
		for (AttributeDefinition ad : inputData.attributeDefinitions) {
			result += String.format("\tprivate final %s %s;\n", ad.getType(), ad.getName());
		}
		return result;
	}

}
