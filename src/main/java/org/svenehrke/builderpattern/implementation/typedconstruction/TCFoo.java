package org.svenehrke.builderpattern.implementation.typedconstruction;

import org.svenehrke.builderpattern.implementation.typedconstruction.lib.OptionalParameter;
import org.svenehrke.builderpattern.implementation.typedconstruction.lib.PrimitiveLongParameter;
import org.svenehrke.builderpattern.implementation.typedconstruction.lib.RequiredParameter;

/**
 * Example for object construction without Builders but artificial types for constructor parameters:
 *
 *   Required1, Required2, Required3, Optional1, Optional2, Optional3
 *
 *
 *  paired with routines for named construction:
 *
 *    new TCFoo(required1("1"), required2(2), required3(3), optional1("o1"), optional2("o2"), optional3("o3"));
 *
 *  Note that each parameter has it's own type on purpose even if the wrapped type is the same:
 *
 *    Optional1 -> String
 *    Optional2 -> String
 *
 * Intended to construct immutable objects.
 *
 * Features from caller's perspective:
 *
 *   Advantages:
 *   - compared to normal constructor call it is clearly visible which parameter means what and caller is forced
 *     to use the named routines for passing parameters
 *
 *   Disadvantages:
 *
 *   - strange parameter types necessary just for parameter passing
 *
 * Features from implementer's perspective:
 *
 *   Advantages:
 *   - compared to normal constructor call it is clearly visible which parameter means what and caller is forced
 *     to use the named routines for passing parameters
 *
 *   Disadvantages:
 *
 *   - not as convenient to use with code completion as chained builder routines are
 *   - overhead with artificial parameter types
 *
 * Sven Ehrke
 */
public class TCFoo {
	private final String required1;
	private final Integer required2;
	private final long required3;

	private final String optional1;
	private final String optional2;
	private final String optional3;

	public TCFoo(Required1 r1, Required2 r2, Required3 r3, Optional1 o1, Optional2 o2, Optional3 o3) {
		if (r1 == null) {
			throw new IllegalArgumentException("parameter 'r1' must not be null");
		}
		if (r2 == null) {
			throw new IllegalArgumentException("parameter 'r2' must not be null");
		}
		if (r3 == null) {
			throw new IllegalArgumentException("parameter 'r3' must not be null");
		}
		this.required1 = r1.getValue();
		this.required2 = r2.getValue();
		this.required3 = r3.getValue();

		this.optional1 = OptionalParameter.valueOf(o1);
		this.optional2 = OptionalParameter.valueOf(o2);
		this.optional3 = OptionalParameter.valueOf(o3);
	}

	public static Required1 required1(String v) {
		return new Required1().init("r1", v);
	}
	public static Required2 required2(Integer v) {
		return new Required2().init("r2", v);
	}
	public static Required3 required3(long v) {
		return new Required3().init(v);
	}
	public static Optional1 optional1(String v) {
		return new Optional1().setValue(v);
	}
	public static Optional2 optional2(String v) {
		return new Optional2().setValue(v);
	}
	public static Optional3 optional3(String v) {
		return new Optional3().setValue(v);
	}

	public String getRequired1() {
		return required1;
	}

	public Integer getRequired2() {
		return required2;
	}

	public long getRequired3() {
		return required3;
	}

	public String getOptional1() {
		return optional1;
	}

	public String getOptional2() {
		return optional2;
	}

	public String getOptional3() {
		return optional3;
	}

	public static class Required1 extends RequiredParameter<Required1, String> {}
	public static class Required2 extends RequiredParameter<Required2, Integer> {}
	public static class Required3 extends PrimitiveLongParameter<Required3> {}

	public static class Optional1 extends OptionalParameter<Optional1, String> {}
	public static class Optional2 extends OptionalParameter<Optional2, String> {}
	public static class Optional3 extends OptionalParameter<Optional3, String> {}
}