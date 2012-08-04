package org.svenehrke.builderpattern.implementation.generics;

/**
 * Example objects constructed with Builder pattern for objects with required fields
 * Intended to construct immutable objects.
 *
 * Advantage: sequence in which attributes are initialized is open to user as long as all required fields are initialized
 * Disadvantage: free sequence of advantage might be a disadvantage because there is no code completion help on what still needs to be initialized.
 *
 * Sven Ehrke
 */
public class Foo {
	private final FooData data;

	public Foo(Builder<OK, OK> aBuilder) {
		data = aBuilder.data;
	}
	public int getRequired1() { return data.required1; }
	public int getRequired2() { return data.required2; }

	public int getOptional1() { return data.optional1; }
	public int getOptional2() { return data.optional2; }
	public int getOptional3() { return data.optional3; }

	public static Builder<NOK, NOK> newBuilder() {
		return new Builder<NOK, NOK>(new FooData());
	}

	public static class Builder<R1, R2> {
		private final FooData data;

		private Builder(FooData aData) {
			this.data = aData;
		}

		public Builder<OK, R2> Required1(int r1) {
			data.required1 = r1;
			return new Builder<OK, R2>(data);
		}

		public Builder<R1, OK> Required2(int r2) {
			data.required2 = r2;
			return new Builder<R1, OK>(data);
		}

		public Builder<R1, R2> optional1(int o1) {
			data.optional1 = o1;
			return this;
		}

		public Builder<R1, R2> optional2(int o2) {
			data.optional2 = o2;
			return this;
		}

		public Builder<R1, R2> optional3(int o3) {
			data.optional3 = o3;
			return this;
		}

	}

	private static class FooData {
		private int required1;
		private int required2;

		private int optional1;
		private int optional2;
		private int optional3;
	}
	public static class OK {}
	public static class NOK {}
}
