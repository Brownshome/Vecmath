package brownshome.vecmath;

import brownshome.vecmath.array.ArrayVec4;
import brownshome.vecmath.matrix.Matrix;

/** Represents a 3 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else. */
public interface Rot3 extends Vec4 {
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
	static MRot3 fromAxisAngle(Vec3 axis, double angle) {
		angle = angle / 2;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		return new BasicVec4(axis.x() * sin, axis.y() * sin, axis.z() * sin, cos);
	}

	static MRot3 of(double x, double y, double z, double w) {
		return new BasicVec4(x, y, z, w);
	}

	static MRot3 of(Vec4 v) {
		return new BasicVec4(v);
	}

	/**
	 * Converts a vec3 into a quaternion. This quaternion is a 'pure' quaternion, and not normalised unless v is normalised
	 * @param v The input vector
	 */
	static MRot3 fromVector(Vec3 v) {
		return new BasicVec4(v.x(), v.y(), v.z(), 0);
	}

	/**
	 * Creates a rotation from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @return a rotation
	 */
	static MRot3 of(double[] array) {
		return of(array, 0);
	}

	/**
	 * Creates a rotation from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @return a rotation
	 */
	static MRot3 of(double[] array, int offset) {
		return of(array, offset, 1);
	}

	/**
	 * Creates a rotation from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @param stride the stride of the components in the array
	 * @return a rotation
	 */
	static MRot3 of(double[] array, int offset, int stride) {
		return new ArrayVec4(array, offset, stride);
	}

	/**
	 * Rotates a given vector by this rotation.
	 * @param v The vector to rotate
	 */
	default void rotate(MVec3 v) {
		// This might be slower than expanding the expression manually. Benchmark if needed.
		// Evaluate p` = qpq*

		MRot3 conj = copy();
		conj.invert();

		Rot3 point = Rot3.fromVector(v);

		conj.multiplyLeft(point);
		conj.multiplyLeft(this);

		v.set(conj.x(), conj.y(), conj.z());
	}
	
	/**
	 * Finds the smallest positive angle between two quaternions. This is the smallest amount of rotation in radians that
	 * is needed to move from one orientation to the other.
	 **/
	default double angleTo(Rot3 o) {
		MRot3 difference = copy();
		difference.invert();
		difference.multiplyLeft(o);

		// Guard against normalisation errors
		if (Math.abs(difference.w()) >= 1.0) {
			return 0.0;
		}

		// ABS to clamp the result to pi
		return 2.0 * Math.acos(Math.abs(difference.w()));
	}

	/** Returns a matrix R such as R * p = p' */
	default Matrix createMatrix() {
		double x2 = x() * x(), y2 = y() * y(), z2 = z() * z();

		// Taken from https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation#Quaternion-derived_rotation_matrix
		return Matrix.of(new double[] {
			1 - 2 * (y2 + z2),           2 * (x() * y() - z() * w()), 2 * (x() * z() + y() * w()),
			2 * (x() * y() + z() * w()), 1 - 2 * (x2 + z2),           2 * (y() * z() - x() * w()),
			2 * (x() * z() - y() * w()), 2 * (y() * z() + x() * w()), 1 - 2 * (x2 + y2)
		}, 3, 3);
	}

	/**
	 * Returns a mutable copy of this object.
	 **/
	@Override
	default MRot3 copy() {
		return new BasicVec4(this);
	}
}
