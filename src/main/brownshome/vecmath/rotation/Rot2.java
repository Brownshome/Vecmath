package brownshome.vecmath.rotation;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.rotation.array.ArrayRot2;
import brownshome.vecmath.rotation.generic.GenericRot;
import brownshome.vecmath.vector.MVec2;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.basic.array.BasicArrayVec2;
import brownshome.vecmath.vector.basic.BasicVec2;
import brownshome.vecmath.vector.layout.Vec2Layout;

/**
 * Represents a 2 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else.
 * <br>
 * This class is represented as a complex number stored in a Vec2, with the first component being the sin (imaginary) term, and the last component being the cos (real) term
 */
public interface Rot2 extends GenericRot<Vec2, MVec2, Vec2, Rot2>, Vec2 {
	/**
	 * A rotation of zero angle
	 */
	Rot2 IDENTITY = new BasicVec2(1, 0);

	static MRot2 ofAngle(double angle) {
		return new BasicVec2(Math.sin(angle / 2.0), Math.cos(angle / 2.0));
	}

	/**
	 * Creates a rotation
	 * @param x the imaginary component of the rotation
	 * @param y the real component of the rotation
	 * @return a rotation
	 */
	static MRot2 of(double x, double y) {
		return new BasicVec2(x, y);
	}

	/**
	 * Copies the components of a Rot2 into a rotation
	 * @param copy the rotation to copy the components from
	 * @return a rotation
	 */
	static MRot2 of(Rot2 copy) {
		return copy.copy();
	}

	/**
	 * Creates an array-backed rotation. The rotation will have an optimal array layout of the given platform.
	 * @return a rotation
	 */
	static ArrayRot2 ofArrayBacked() {
		return of(Vec2Layout.ofOptimal());
	}

	/**
	 * Creates an array-backed rotation with the given layout
	 * @param layout the layout
	 * @return a rotation
	 */
	static ArrayRot2 of(Vec2Layout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a rotation from the components in this array. Edits to the array will change this vector and vice-versa.
	 * @param array the array
	 * @param layout the layout of the array
	 * @return a rotation
	 */
	static ArrayRot2 of(double[] array, Vec2Layout layout) {
		return new BasicArrayVec2(array, layout);
	}

	@Override
	default double angle() {
		assert y() <= 1 && y() >= -1;
		return Math.acos(Math.abs(y())) * 2;
	}

	@Override
	default void setToRotated(MVec2 v) {
		var rotated = multiply(Rot2.of(v.y(), v.x())).multiply(this);
		v.set(rotated.y(), rotated.x());
	}

	default Rot2 multiply(Rot2 rot) {
		MRot2 tmp = copy();
		tmp.multiplyRightSelf(rot);
		return tmp;
	}

	@Override
	default Matrix asMatrix() {
		return Matrix.of(new double[] {
				2 * y() * y() - 1, -2 * x() * y(),
				2 * x() * y(), 2 * y() * y() - 1
		}, MatrixLayout.ofRowMajor(2, 2));
	}

	@Override
	default ArrayRot2 asArrayBacked() {
		return (ArrayRot2) Vec2.super.asArrayBacked();
	}

	@Override
	default Rot2 asRot() {
		return this;
	}

	@Override
	default ArrayRot2 arrayBackedCopy() {
		return (ArrayRot2) Vec2.super.arrayBackedCopy();
	}

	@Override
	default ArrayRot2 arrayBackedCopy(Vec2Layout layout) {
		return (ArrayRot2) Vec2.super.arrayBackedCopy(layout);
	}

	/**
	 * Returns a mutable copy of this object.
	 **/
	@Override
	default MRot2 copy() {
		return (MRot2) Vec2.super.copy();
	}

	@Override
	default MRot2 move() {
		return (MRot2) GenericRot.super.move();
	}
}
