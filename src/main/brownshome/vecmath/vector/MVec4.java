package brownshome.vecmath.vector;

import brownshome.vecmath.rotation.MRot3;
import brownshome.vecmath.rotation.Rot3;
import brownshome.vecmath.vector.array.ArrayVecN;
import brownshome.vecmath.vector.generic.GenericMVec;

/**
 * A mutable 4-element vector
 */
public interface MVec4 extends GenericMVec<Vec4>, Vec4 {
	/**
	 * Sets x
	 * @param x the value of x
	 */
	void x(double x);

	/**
	 * Sets y
	 * @param y the value of y
	 */
	void y(double y);

	/**
	 * Sets z
	 * @param z the value of z
	 */
	void z(double z);

	/**
	 * Sets w
	 * @param w the value of w
	 */
	void w(double w);

	/**
	 * Sets this vector component-wise
	 * @param x the value of x
	 * @param y the value of y
	 * @param z the value of z
	 * @param w the value of w
	 */
	default void set(double x, double y, double z, double w) {
		x(x);
		y(y);
		z(z);
		w(w);
	}

	@Override
	default void set(Vec4 v) {
		set(v.x(), v.y(), v.z(), v.w());
	}

	/**
	 * Adds an amount to this vector
	 * @param x The amount to add to the x value
	 * @param y The amount to add to the y value
	 * @param z The amount to add to the z value
	 * @param w The amount to add to the w value
	 */
	default void addToSelf(double x, double y, double z, double w) {
		set(x() + x, y() + y, z() + z, w() + w);
	}

	@Override
	default void addToSelf(Vec4 vec) {
		addToSelf(vec.x(), vec.y(), vec.z(), vec.w());
	}

	@Override
	default void scaleSelf(double scale) {
		scaleSelf(Vec4.of(scale, scale, scale, scale));
	}

	@Override
	default void scaleSelf(Vec4 scale) {
		set(scale.x() * x(), scale.y() * y(), scale.z() * z(), scale.w() * w());
	}

	@Override
	default MVecN asUnknownSize() {
		return new MVecN() {
			@Override
			public int size() {
				return 4;
			}

			@Override
			public double get(int i) {
				assert i < size();

				return switch (i) {
					case 0 -> x();
					case 1 -> y();
					case 2 -> z();
					default -> w();
				};
			}

			@Override
			public void set(double value, int index) {
				assert index < size();

				switch (index) {
					case 0 -> x(value);
					case 1 -> y(value);
					case 2 -> z(value);
					default -> w(value);
				}
			}

			@Override
			public MVec4 asVec4() {
				return MVec4.this;
			}

			@Override
			public ArrayVecN asArrayBacked() {
				return MVec4.this.asArrayBacked().asUnknownSize();
			}

			@Override
			public String toString() {
				return MVec4.this.toString();
			}
		};
	}

	@Override
	default MRot3 asRot() {
		return new MRot3() {
			@Override
			public void x(double x) {
				MVec4.this.x(x);
			}

			@Override
			public void y(double y) {
				MVec4.this.y(y);
			}

			@Override
			public void z(double z) {
				MVec4.this.z(z);
			}

			@Override
			public void w(double w) {
				MVec4.this.w(w);
			}

			@Override
			public double x() {
				return MVec4.this.x();
			}

			@Override
			public double y() {
				return MVec4.this.y();
			}

			@Override
			public double z() {
				return MVec4.this.z();
			}

			@Override
			public double w() {
				return MVec4.this.w();
			}
		};
	}

	@Override
	default MVec4 move() {
		return (MVec4) GenericMVec.super.move();
	}
}
