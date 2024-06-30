package brownshome.vecmath.vector;

import brownshome.vecmath.complex.MComplex;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.rotation.MRot2;
import brownshome.vecmath.vector.generic.GenericMVec;
import brownshome.vecmath.vector.wrapped.MVec2Wrapper;

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
		return new MVec2Wrapper.BasicToVecN(this);
	}

	/**
	 * Returns this vector as a 2D-rotation
	 * @return a rotation
	 */
	@Override
	default MRot2 asRot() {
		return new MVec2Wrapper.BasicToRot2(this);
	}

	@Override
	default MComplex asComplex() {
		return new MVec2Wrapper.BasicToComplex(this);
	}

	@Override
	default MMatrix asRow() {
		return (MMatrix) Vec2.super.asRow();
	}

	@Override
	default MMatrix asColumn() {
		return new MVec2Wrapper.BasicToMatrix(this);
	}

	@Override
	default MVec2 move() {
		return (MVec2) GenericMVec.super.move();
	}
}
