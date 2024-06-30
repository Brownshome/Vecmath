package brownshome.vecmath.vector;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.rotation.MRot3;
import brownshome.vecmath.vector.generic.GenericMVec;
import brownshome.vecmath.vector.wrapped.MVec4Wrapper;

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
		return new MVec4Wrapper.BasicToVecN(this);
	}

	@Override
	default MRot3 asRot() {
		return new MVec4Wrapper.BasicToRot3(this);
	}

	@Override
	default MMatrix asRow() {
		return (MMatrix) Vec4.super.asRow();
	}

	@Override
	default MMatrix asColumn() {
		return new MVec4Wrapper.BasicToMatrix(this);
	}

	@Override
	default MVec4 move() {
		return (MVec4) GenericMVec.super.move();
	}
}
