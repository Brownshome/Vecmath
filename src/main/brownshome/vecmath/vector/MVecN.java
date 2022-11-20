package brownshome.vecmath.vector;

import brownshome.vecmath.vector.array.ArrayVec2;
import brownshome.vecmath.vector.array.ArrayVec3;
import brownshome.vecmath.vector.array.ArrayVec4;
import brownshome.vecmath.vector.generic.GenericMVec;

/**
 * A mutable arbitrary-element vector
 */
public interface MVecN extends GenericMVec<VecN>, VecN {
	/**
	 * Sets the ith element of this vector
	 * @param value the value to set
	 * @param index the index to set
	 */
	void set(double value, int index);

	/**
	 * Sets this vector component-wise
	 * @param values the components in this vector
	 */
	default void set(double... values) {
		set(VecN.of(values));
	}

	@Override
	default void set(VecN vec) {
		assert size() == vec.size();

		for (int i = 0; i < size(); i++) {
			set(vec.get(i), i);
		}
	}

	/**
	 * Adds an amount to this vector
	 * @param values that amount to add to each element in this vector
	 */
	default void addToSelf(double... values) {
		addToSelf(VecN.of(values));
	}

	@Override
	default void addToSelf(VecN vec) {
		assert size() == vec.size();

		for (int i = 0; i < size(); i++) {
			set(get(i) + vec.get(i), i);
		}
	}

	@Override
	default void scaleSelf(double scale) {
		for (int i = 0; i < size(); i++) {
			set(get(i) * scale, i);
		}
	}

	@Override
	default void scaleSelf(VecN scale) {
		assert size() == scale.size();

		for (int i = 0; i < size(); i++) {
			set(get(i) * scale.get(i), i);
		}
	}

	@Override
	default MVecN asUnknownSize() {
		return this;
	}

	@Override
	default MVec2 asVec2() {
		assert size() == 2;

		return new MVec2() {
			@Override
			public void x(double x) {
				MVecN.this.set(x, 0);
			}

			@Override
			public void y(double y) {
				MVecN.this.set(y, 1);
			}

			@Override
			public double x() {
				return get(0);
			}

			@Override
			public double y() {
				return get(1);
			}

			@Override
			public MVecN asUnknownSize() {
				return MVecN.this;
			}

			@Override
			public ArrayVec2 asArrayBacked() {
				return MVecN.this.asArrayBacked().asVec2();
			}

			@Override
			public String toString() {
				return MVecN.this.toString();
			}
		};
	}

	@Override
	default MVec3 asVec3() {
		assert size() == 3;

		return new MVec3() {
			@Override
			public void x(double x) {
				MVecN.this.set(x, 0);
			}

			@Override
			public void y(double y) {
				MVecN.this.set(y, 1);
			}

			@Override
			public void z(double z) {
				MVecN.this.set(z, 2);
			}

			@Override
			public double x() {
				return get(0);
			}

			@Override
			public double y() {
				return get(1);
			}

			@Override
			public double z() {
				return get(2);
			}

			@Override
			public MVecN asUnknownSize() {
				return MVecN.this;
			}

			@Override
			public ArrayVec3 asArrayBacked() {
				return MVecN.this.asArrayBacked().asVec3();
			}

			@Override
			public String toString() {
				return MVecN.this.toString();
			}
		};
	}

	@Override
	default MVec4 asVec4() {
		assert size() == 4;

		return new MVec4() {
			@Override
			public void x(double x) {
				MVecN.this.set(x, 0);
			}

			@Override
			public void y(double y) {
				MVecN.this.set(y, 1);
			}

			@Override
			public void z(double z) {
				MVecN.this.set(z, 2);
			}

			@Override
			public void w(double w) {
				MVecN.this.set(w, 3);
			}

			@Override
			public double x() {
				return get(0);
			}

			@Override
			public double y() {
				return get(1);
			}

			@Override
			public double z() {
				return get(2);
			}

			@Override
			public double w() {
				return get(3);
			}

			@Override
			public MVecN asUnknownSize() {
				return MVecN.this;
			}

			@Override
			public ArrayVec4 asArrayBacked() {
				return MVecN.this.asArrayBacked().asVec4();
			}

			@Override
			public String toString() {
				return MVecN.this.toString();
			}
		};
	}

	@Override
	default MVecN move() {
		return (MVecN) GenericMVec.super.move();
	}
}
