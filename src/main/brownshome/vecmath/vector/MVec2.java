package brownshome.vecmath.vector;

import brownshome.vecmath.generic.GenericMElement;
import brownshome.vecmath.rotation.MRot2;
import brownshome.vecmath.rotation.Rot2;
import brownshome.vecmath.vector.array.ArrayVec2;
import brownshome.vecmath.vector.array.ArrayVecN;
import brownshome.vecmath.vector.generic.GenericMVec;

/**
 * A mutable 2-element vector
 */
public interface MVec2 extends GenericMVec<Vec2>, Vec2 {
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
	 * Sets this vector component-wise
	 * @param x the value of x
	 * @param y the value of y
	 */
	default void set(double x, double y) {
		x(x);
		y(y);
	}

	@Override
	default void set(Vec2 v) {
		set(v.x(), v.y());
	}

	/**
	 * Adds an amount to this vector
	 * @param x The amount to the x value
	 * @param y The amount to add to the y value
	 */
	default void addToSelf(double x, double y) {
		set(x() + x, y() + y);
	}

	@Override
	default void addToSelf(Vec2 vec) {
		addToSelf(vec.x(), vec.y());
	}

	@Override
	default void scaleSelf(double scale) {
		scaleSelf(Vec2.of(scale, scale));
	}

	@Override
	default void scaleSelf(Vec2 scale) {
		set(scale.x() * x(), scale.y() * y());
	}

	/**
	 * Sets this vector to one that is tangential to this vector. This function rotates the vector counterclockwise in the x-right, y-up coordinate system
	 */
	default void setToTangent() {
		set(-y(), x());
	}

	@Override
	default MVecN asUnknownSize() {
		return new MVecN() {
			@Override
			public int size() {
				return 2;
			}

			@Override
			public double get(int i) {
				assert i < size();

				return i == 0 ? x() : y();
			}

			@Override
			public void set(double value, int index) {
				assert index < size();

				if (index == 0) {
					x(value);
				} else {
					y(value);
				}
			}

			@Override
			public MVec2 asVec2() {
				return MVec2.this;
			}

			@Override
			public ArrayVecN asArrayBacked() {
				return MVec2.this.asArrayBacked().asUnknownSize();
			}

			@Override
			public String toString() {
				return MVec2.this.toString();
			}
		};
	}

	/**
	 * Returns this vector as a 2D-rotation
	 * @return a rotation
	 */
	@Override
	default MRot2 asRot() {
		return new MRot2() {
			@Override
			public void x(double x) {
				MVec2.this.x(x);
			}

			@Override
			public void y(double y) {
				MVec2.this.y(y);
			}

			@Override
			public double x() {
				return MVec2.this.x();
			}

			@Override
			public double y() {
				return MVec2.this.y();
			}
		};
	}

	@Override
	default MVec2 move() {
		return (MVec2) GenericMVec.super.move();
	}
}
