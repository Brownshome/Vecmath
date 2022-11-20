package brownshome.vecmath.vector;

import brownshome.vecmath.vector.array.ArrayVecN;
import brownshome.vecmath.vector.generic.GenericMVec;

/**
 * A mutable 3-element vector
 */
public interface MVec3 extends GenericMVec<Vec3>, Vec3 {
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
	 * Sets this vector component-wise
	 * @param x the value of x
	 * @param y the value of y
	 * @param z the value of z
	 */
	default void set(double x, double y, double z) {
		x(x);
		y(y);
		z(z);
	}

	@Override
	default void set(Vec3 v) {
		set(v.x(), v.y(), v.z());
	}

	/**
	 * Adds the amount to this vector
	 * @param x the amount to add to the x component
	 * @param y the amount to add to the y component
	 * @param z the amount to add to the z component
	 */
	default void addToSelf(double x, double y, double z) {
		set(x() + x, y() + y, z() + z);
	}

	@Override
	default void addToSelf(Vec3 vec) {
		addToSelf(vec.x(), vec.y(), vec.z());
	}

	@Override
	default void scaleSelf(double scale) {
		scaleSelf(Vec3.of(scale, scale, scale));
	}

	@Override
	default void scaleSelf(Vec3 scale) {
		set(scale.x() * x(), scale.y() * y(), scale.z() * z());
	}

	/**
	 * Sets this vector to the cross product of this one and the given vector
	 * @param vec the other vector
	 */
	default void setToRightCross(Vec3 vec) {
		set(
				y() * vec.z() - z() * vec.y(),
				z() * vec.x() - x() * vec.z(),
				x() * vec.y() - y() * vec.x()
		);
	}

	/**
	 * Sets this vector to the cross product of the given vector and this one
	 * @param vec the other vector
	 */
	default void setToLeftCross(Vec3 vec) {
		set(vec.cross(this));
	}

	@Override
	default MVecN asUnknownSize() {
		return new MVecN() {
			@Override
			public int size() {
				return 3;
			}

			@Override
			public double get(int i) {
				assert i < size();

				return switch (i) {
					case 0 -> x();
					case 1 -> y();
					default -> z();
				};
			}

			@Override
			public void set(double value, int index) {
				assert index < size();

				switch (index) {
					case 0 -> x(value);
					case 1 -> y(value);
					default -> z(value);
				}
			}

			@Override
			public MVec3 asVec3() {
				return MVec3.this;
			}

			@Override
			public ArrayVecN asArrayBacked() {
				return MVec3.this.asArrayBacked().asUnknownSize();
			}

			@Override
			public String toString() {
				return MVec3.this.toString();
			}
		};
	}

	@Override
	default MVec3 move() {
		return (MVec3) GenericMVec.super.move();
	}
}
