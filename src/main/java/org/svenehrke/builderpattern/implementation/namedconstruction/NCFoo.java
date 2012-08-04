package org.svenehrke.builderpattern.implementation.namedconstruction;

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
	// Visible for tests:
	NCFoo(String required1, String required2, String required3, String optional1, String optional2, String optional3) {
		if (required1 == null) {
			throw new IllegalArgumentException("Foo5.Foo5(...): parameter 'required1' must not be null");
		}
		if (required2 == null) {
			throw new IllegalArgumentException("Foo5.Foo5(...): parameter 'required2' must not be null");
		}
		if (required3 == null) {
			throw new IllegalArgumentException("Foo5.Foo5(...): parameter 'required3' must not be null");
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
		public NCFoo required3(String v) {
			return foo.newRequired3(v);
		}
	}
}