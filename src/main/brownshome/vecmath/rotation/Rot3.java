package brownshome.vecmath.rotation;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.rotation.array.ArrayRot3;
import brownshome.vecmath.rotation.generic.GenericRot;
import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.basic.array.BasicArrayVec4;
import brownshome.vecmath.vector.basic.BasicVec4;
import brownshome.vecmath.vector.layout.Vec4Layout;

/** Represents a 3 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else. */
public interface Rot3 extends GenericRot<Vec3, MVec3, Vec4, Rot3>, Vec4 {
	/**
	 * A rotation of zero angle
	 */
	Rot3 IDENTITY = new BasicVec4(0, 0, 0, 1);

	/**
	 * Produces a quaternion that represents a rotation by the supplied axis angle combination.
	 * @param axis The axis to rotate by
	 * @param angle The angle to rotate by. Looking along the axis, this is clockwise in a right-handed coordinate system
	 *              and counter-clockwise in a left-handed system.
	 * @return A rotation representing the rotation by the axis-angle pair.
	 */
	static MRot3 ofAxisAngle(Vec3 axis, double angle) {
		angle = angle / 2;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		return new BasicVec4(axis.x() * sin, axis.y() * sin, axis.z() * sin, cos);
	}

	static MRot3 of(double x, double y, double z, double w) {
		return new BasicVec4(x, y, z, w);
	}

	static MRot3 of(Rot3 v) {
		return v.copy();
	}

	/**
	 * Creates an array-backed rotation. The rotation will have an optimal array layout of the given platform.
	 * @return a rotation
	 */
	static ArrayRot3 ofArrayBacked() {
		return of(Vec4Layout.ofOptimal());
	}

	/**
	 * Creates an array-backed rotation with the given layout
	 * @param layout the layout
	 * @return a rotation
	 */
	static ArrayRot3 of(Vec4Layout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a rotation from the components in this array. Edits to the array will change this vector and vice-versa.
	 * @param array the array
	 * @param layout the layout of the array
	 * @return a rotation
	 */
	static ArrayRot3 of(double[] array, Vec4Layout layout) {
		return new BasicArrayVec4(array, layout);
	}

	@Override
	default double angle() {
		assert w() <= 1 && w() >= -1;
		return Math.acos(Math.abs(w())) * 2;
	}

	@Override
	default void setToRotated(MVec3 v) {
		var result = multiply(Rot3.of(v.x(), v.y(), v.z(), 0.0)).multiply(inverted());
		v.set(result.x(), result.y(), result.z());
	}

	@Override
	default Matrix asMatrix() {
		double x2 = x() * x(), y2 = y() * y(), z2 = z() * z();

		// Taken from https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation#Quaternion-derived_rotation_matrix
		return Matrix.of(new double[] {
			1 - 2 * (y2 + z2),           2 * (x() * y() - z() * w()), 2 * (x() * z() + y() * w()),
			2 * (x() * y() + z() * w()), 1 - 2 * (x2 + z2),           2 * (y() * z() - x() * w()),
			2 * (x() * z() - y() * w()), 2 * (y() * z() + x() * w()), 1 - 2 * (x2 + y2)
		}, MatrixLayout.ofRowMajor(3, 3));
	}

	@Override
	default ArrayRot3 asArrayBacked() {
		return (ArrayRot3) Vec4.super.asArrayBacked();
	}

	@Override
	default Rot3 asRot() {
		return this;
	}

	@Override
	default ArrayRot3 arrayBackedCopy() {
		return (ArrayRot3) Vec4.super.arrayBackedCopy();
	}

	@Override
	default ArrayRot3 arrayBackedCopy(Vec4Layout layout) {
		return (ArrayRot3) Vec4.super.arrayBackedCopy(layout);
	}

	@Override
	default MRot3 copy() {
		return (MRot3) Vec4.super.copy();
	}

	@Override
	default MRot3 move() {
		return (MRot3) GenericRot.super.move();
	}
}
