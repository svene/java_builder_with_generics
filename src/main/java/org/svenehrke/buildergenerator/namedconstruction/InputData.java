package org.svenehrke.buildergenerator.namedconstruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class InputData {

	public String className;
	public List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();

	static InputData parse(String... lines) {
		return parse(Arrays.asList(lines));
	}
	static InputData parse(List<String> lines) {
		final InputData result = new InputData();
		// 1. line:
		result.className = lines.get(0).trim();
		int ln = 0;
		for (String line : lines.subList(1, lines.size())) {
			ln++;
			try {
				result.attributeDefinitions.add(AttributeDefinition.parse(line));
			} catch (ParseException e) {
				throw new ParseException(String.format("ERROR (line %d): '%s'", ln, line), e);
			}
		}
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

}
