package org.svenehrke.builderpattern.implementation.generics;

/**
 * Example objects constructed with Builder pattern for objects with required fields
 * Intended to construct immutable objects.
 *
 * Advantage: sequence in which attributes are initialized is open to user as long as all required fields are initialized
 * Disadvantage: free sequence of advantage might be a disadvantage depending on taste.
 *
 * Sven Ehrke
 */
public class Foo {
	private final FooData data;

	public Foo(Builder<OK, OK> aBuilder) {
		data = aBuilder.data;
	}
	public int getR1() { return data.r1; }
	public int getR2() { return data.r2; }

	public int getO1() { return data.o1; }
	public int getO2() { return data.o2; }
	public int getO3() { return data.o3; }

	public static Builder<NOK, NOK> newBuilder() {
		return new Builder<NOK, NOK>(new FooData());
	}

	public static class Builder<R1, R2> /*implements IBuilder<R1, R2>*/ {
		private final FooData data;

		private Builder(FooData aData) {
			this.data = aData;
		}

		public Builder<OK, R2> initR1(int r1) {
			data.r1 = r1;
			return new Builder<OK, R2>(data);
		}

		public Builder<R1, OK> initR2(int r2) {
			data.r2 = r2;
			return new Builder<R1, OK>(data);
		}

		public Builder<R1, R2> withO1(int o1) {
			data.o1 = o1;
			return this;
		}

		public Builder<R1, R2> withO2(int o2) {
			data.o2 = o2;
			return this;
		}

		public Builder<R1, R2> withO3(int o3) {
			data.o3 = o3;
			return this;
		}

	}

	private static class FooData {
		// Required parameters:
		private int r1;
		private int r2;

		// Optional parameters:
		private int o1;
		private int o2;
		private int o3;
	}
	public static class OK {}
	public static class NOK {}
}
