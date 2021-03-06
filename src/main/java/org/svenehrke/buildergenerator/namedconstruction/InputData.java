package org.svenehrke.buildergenerator.namedconstruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class InputData {

	public String pgkName;
	public String className;
	public List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
	public List<AttributeDefinition> requiredAttributes = new ArrayList<AttributeDefinition>();
	public List<AttributeDefinition> optionalAttributes = new ArrayList<AttributeDefinition>();
	private int numberOfRequiredAttributes;

	static InputData parse(String... lines) {
		return parse(Arrays.asList(lines));
	}
	static InputData parse(List<String> lines) {
		final InputData result = new InputData();
		// 1. line:
		final String firstLine = lines.get(0).trim();
		int idx = firstLine.lastIndexOf(".");
		if (idx == -1) {
			result.pgkName = "";
			result.className = firstLine;
		}
		else {
			result.pgkName = firstLine.substring(0, idx);
			result.className = firstLine.substring(idx + 1);
		}
		int ln = 0;
		for (String line : lines.subList(1, lines.size())) {
			ln++;
			try {
				final AttributeDefinition ad = AttributeDefinition.parse(line);
				(ad.isRequired() ? result.requiredAttributes : result.optionalAttributes).add(ad);
				result.attributeDefinitions.add(ad);

			} catch (ParseException e) {
				throw new ParseException(String.format("ERROR (line %d): '%s'", ln, line), e);
			}
		}

		result.numberOfRequiredAttributes = numberOfRequiredAttributes(result.attributeDefinitions);
		return result;
	}

	static List<String> toLines(BufferedReader br) {
		List<String> result = new LinkedList<String>();

		try {
			String line;
			while ((line = br.readLine()) != null ) {
				result.add(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

	public List<AttributeDefinition> getRequiredAttributes() {
		return requiredAttributes;
	}

	public List<AttributeDefinition> getOptionalAttributes() {
		return optionalAttributes;
	}

	private static int numberOfRequiredAttributes(final List<AttributeDefinition> aAttributeDefinitions) {
		int result = 0;
		for (AttributeDefinition ad : aAttributeDefinitions) {
			if (ad.isRequired()) {
				result++;
			}
		}
		return result;
	}

}
