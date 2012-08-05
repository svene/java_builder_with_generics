package org.svenehrke.builderpattern.implementation.namedconstruction;

/**
 * Example objects constructed with Builder pattern for objects with required fields
 * Intended to construct immutable objects.
 *
 * Features:
 * - Guarantees at compile time that all required fields are initialized.
 *   This is achieved by the usage of chained builders
 *
 *   Advantages:
 *
 *   - sequence in which attributes are initialized is given by the chain of builders
 *
 *   Disadvantages:
 *
 *   - number of required internal builder classes grows with each attribute of the class to be initialized
 *
 * Sven Ehrke
 */
public class NCFoo {
	private final String required1;
	private final String required2;
	private final String required3;

	private final String optional1;
	private final String optional2;
	private final String optional3;

	// Visible for tests:
	NCFoo(String r1, String r2, String r3) {
		this(r1, r2, r3, null, null, null);
	}
	private NCFoo(String required1, String required2, String required3, String optional1, String optional2, String optional3) {
		if (required1 == null) {
			throw new IllegalArgumentException("parameter 'required1' must not be null");
		}
		if (required2 == null) {
			throw new IllegalArgumentException("parameter 'required2' must not be null");
		}
		if (required3 == null) {
			throw new IllegalArgumentException("parameter 'required3' must not be null");
		}

		this.required1 = required1;
		this.required2 = required2;
		this.required3 = required3;

		this.optional1 = optional1;
		this.optional2 = optional2;
		this.optional3 = optional3;
	}
	public String getRequired1() { return required1; }
	public String getRequired2() { return required2; }
	public String getRequired3() { return required3; }

	public String getOptional1() { return optional1; }
	public String getOptional2() { return optional2; }
	public String getOptional3() { return optional3; }

	public NCFoo newRequired1(String v) {
		return new NCFoo(v, required2, required3, optional1, optional2, optional3);
	}
	public NCFoo newRequired2(String v) {
		return new NCFoo(required1, v, required3, optional1, optional2, optional3);
	}
	public NCFoo newRequired3(String v) {
		return new NCFoo(required1, required2, v, optional1, optional2, optional3);
	}
	public NCFoo newOptional1(String v) {
		return new NCFoo(required1, required2, required3, v, optional2, optional3);
	}
	public NCFoo newOptional2(String v) {
		return new NCFoo(required1, required2, required3, optional1, v, optional3);
	}
	public NCFoo newOptional3(String v) {
		return new NCFoo(required1, required2, required3, optional1, optional2, v);
	}

	// Construction Entry Point:
	public static Builder2 required1(String v) {
		return new Builder2(new NCFoo(v, "", ""));
	}

	public static class Builder2 {
		NCFoo foo;
		public Builder2(NCFoo foo) {
			this.foo = foo;
		}
		public Builder3 required2(String v) {
			return new Builder3(foo.newRequired2(v));
		}
	}
	public static class Builder3 {
		NCFoo foo;
		public Builder3(NCFoo foo) {
			this.foo = foo;
		}
		public OptionalBuilder required3(String v) {
			return new OptionalBuilder(foo.newRequired3(v));
		}
	}
	public static class OptionalBuilder {
		NCFoo foo;
		public OptionalBuilder(NCFoo foo) {
			this.foo = foo;
		}
		public NCFoo build() {
			return foo;
		}
		public OptionalBuilder optional1(String v) {
			return new OptionalBuilder(foo.newOptional1(v));
		}
		public OptionalBuilder optional2(String v) {
			return new OptionalBuilder(foo.newOptional2(v));
		}
		public OptionalBuilder optional3(String v) {
			return new OptionalBuilder(foo.newOptional3(v));
		}
	}
}