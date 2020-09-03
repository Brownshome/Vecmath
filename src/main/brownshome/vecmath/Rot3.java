package brownshome.vecmath;

/** Represents a 3 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else. */
public interface Rot3 extends Vec4 {
	/**
	 * Rotates a given vector by this rotation.
	 * @param v The vector to rotate
	 */
	default void rotate(MVec3 v) {
		// This might be slower than expanding the expression manually. Benchmark if needed.
		// Evaluate p` = qpq*

		MRot3 conj = new MRot3(this);
		conj.invert();

		Rot3 point = new IRot3(v);

		conj.multiplyLeft(point);
		conj.multiplyLeft(this);

		v.set(conj.x(), conj.y(), conj.z());
	}
	
	/**
	 * Finds the smallest positive angle between two quaternions. This is the smallest amount of rotation in radians that
	 * is needed to move from one orientation to the other.
	 **/
	default double angleTo(Rot3 o) {
		MRot3 difference = mutable();
		difference.invert();
		difference.multiplyLeft(o);

		// ABS to clamp the result to pi
		return 2.0 * Math.acos(Math.abs(o.w()));
	}

	/** Returns a matrix R such as R * p = p' */
	default Matrix createMatrix() {
		double x2 = x() * x(), y2 = y() * y(), z2 = z() * z();

		// Taken from https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation#Quaternion-derived_rotation_matrix
		return new Matrix(new double[] {
			1 - 2 * (y2 + z2),           2 * (x() * y() - z() * w()), 2 * (x() * z() + y() * w()),
			2 * (x() * y() + z() * w()), 1 - 2 * (x2 + z2),           2 * (y() * z() - x() * w()),
			2 * (x() * z() - y() * w()), 2 * (y() * z() + x() * w()), 1 - 2 * (x2 + y2)
		}, 3, 3);
	}

	/**
	 * Returns an immutable version of this object.
	 **/
	@Override
	default Rot3 immutable() {
		return new IRot3(this);
	}

	/**
	 * Returns an mutable copy of this object.
	 **/
	@Override
	default MRot3 mutable() {
		return new MRot3(this);
	}
}
