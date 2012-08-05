package org.svenehrke.buildergenerator.namedconstruction;

import java.util.Arrays;
import java.util.HashSet;

public class AttributeDefinition {
	public static final HashSet<String> PRIMITIVES = new HashSet<String>(Arrays.asList("int", "double", "float", "char"));
	private final boolean required;
	private final String type;
	private final String name;

	public AttributeDefinition(boolean aRequired, String aType, String aName) {
		required = aRequired;
		type = aType;
		name = aName;
	}

	public static AttributeDefinition parse(String s) throws ParseException {
		String[] tokens = s.split(",");
		if (tokens.length != 3) {
			throw new ParseException(String.format("String '%s' does not consist of 3 parts separated by commas", s));
		}
		String sr = tokens[0];
		if (!("R".equalsIgnoreCase(sr) || "O".equalsIgnoreCase(sr))) {
			throw new ParseException(String.format("String '%s': first parameter must be 'R' or 'O'", s));
		}

		String st = tokens[1];
		if (st.length() == 0) {
			throw new ParseException(String.format("String '%s': second parameter invalid", s));
		}
		String sn = tokens[2];
		if (sn.length() == 0) {
			throw new ParseException(String.format("String '%s': third parameter invalid", s));
		}
		return new AttributeDefinition("R".equalsIgnoreCase(tokens[0]), tokens[1], tokens[2]);
	}

	public boolean isRequired() {
		return required;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean isPrimitive() {
		return PRIMITIVES.contains(type);
	}
}
