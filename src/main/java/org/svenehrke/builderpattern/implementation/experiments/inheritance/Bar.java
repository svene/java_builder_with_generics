package org.svenehrke.builderpattern.implementation.experiments.inheritance;

/**
 * Example objects constructed with Builder pattern for objects with required fields
 * with exactly one forced path to initialize the required fields.
 * Intended to construct immutable objects.
 *
 * Disadvantage: having to create constructors calling the super constructor
 *
 * Implementation note:
 * The allowed builder sequence is defined by the return value of the individual builder's init*() routines
 *
 * Sven Ehrke
 */
public class Bar {
	private final Data data;

	private Bar(FinalBuilder aBuilder) {
		data = aBuilder.data;
	}
	public int getR1() { return data.r1; }
	public int getR2() { return data.r2; }

	public int getO1() { return data.o1; }
	public int getO2() { return data.o2; }
	public int getO3() { return data.o3; }

	public static R1Builder newBuilder() {
		return new R1Builder(new Data());
	}


	public static class RequiredBuilder {
		protected final Data data;

		private RequiredBuilder(Data aData) {
			data = aData;
		}
	}

	public static class R1Builder extends RequiredBuilder {
		private R1Builder(Data aData) {
			super(aData);
		}

		public R2Builder initR1(int r1) {
			data.r1 = r1;
			return new R2Builder(data);
		}
	}

	public static class R2Builder extends RequiredBuilder {
		private R2Builder(Data aData) {
			super(aData);
		}

		public FinalBuilder initR2(int r2) {
			data.r2 = r2;
			return new FinalBuilder(data);
		}
	}

	public static class FinalBuilder {
		private final Data data;

		private FinalBuilder(Data aData) {
			data = aData;
		}

		public Bar build() {
			return new Bar(this);
		}

		public FinalBuilder withO1(int o1) {
			data.o1 = o1;
			return this;
		}
		public FinalBuilder withO2(int o2) {
			data.o2 = o2;
			return this;
		}
		public FinalBuilder withO3(int o3) {
			data.o3 = o3;
			return this;
		}
	}

	private static class Data {
		// Required parameters:
		private int r1;
		private int r2;

		// Optional parameters:
		private int o1;
		private int o2;
		private int o3;
	}
}
