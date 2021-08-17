package brownshome.vecmath;

import brownshome.vecmath.array.ArrayVec3;

/** An interface representing a 3D vector. This can be used as a type representing a Vec3 that may be edited by the creator by no-one else. */
public interface Vec3 {
	/**
	 * The vector (0, 0, 0)
	 */
	Vec3 ZERO = new BasicVec3(0, 0, 0);

	/**
	 * The vector (1, 0, 0)
	 */
	Vec3 X_AXIS = new BasicVec3(1, 0, 0);

	/**
	 * The vector (0, 1, 0)
	 */
	Vec3 Y_AXIS = new BasicVec3(0, 1, 0);

	/**
	 * The vector (0, 0, 1)
	 */
	Vec3 Z_AXIS = new BasicVec3(0, 0, 1);

	/**
	 * Creates a new vector with the given components
	 * @param x the x component
	 * @param y the y component
	 * @param z the z component
	 * @return a newly created vector
	 */
	static MVec3 of(double x, double y, double z) {
		return new BasicVec3(x, y, z);
	}

	/**
	 * Creates a copy of the given vector
	 * @param vec the vector to copy
	 * @return the copy
	 */
	static MVec3 of(Vec3 vec) {
		return new BasicVec3(vec);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @return a vector
	 */
	static MVec3 of(double[] array) {
		return of(array, 0);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @return a vector
	 */
	static MVec3 of(double[] array, int offset) {
		return of(array, offset, 1);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @param stride the stride of the components in the array
	 * @return a vector
	 */
	static MVec3 of(double[] array, int offset, int stride) {
		return new ArrayVec3(array, offset, stride);
	}

	double x();
	double y();
	double z();
	
	/**
	 * Calculates the distance squared from this vector to another one
	 * @param position The position to calculate the distance to
	 */
	default double distanceSq(Vec3 position) {
		double dx = position.x() - x();
		double dy = position.y() - y();
		double dz = position.z() - z();
		return dx * dx + dy * dy + dz * dz;
	}
	
	/**
	 * Calculates the dot product between this and vec
	 */
	default double dot(Vec3 vec) {
		return vec.x() * x() + vec.y() * y() + vec.z() * z();
	}
	
	/**
	 * Calculates the length of this vector
	 */
	default double length() {
		return Math.sqrt(lengthSq());
	}
	
	/**
	 * Calculates the length of this vector squared
	 */
	default double lengthSq() {
		return x() * x() + y() * y() + z() * z();
	}
	
	/**
	 * Calculates the distance from this vector to another one
	 * @param position The position to calculate to
	 */
	default double distance(Vec3 position) {
		return Math.sqrt(position.distanceSq(this));
	}
	
	default boolean exactEquals(Vec3 other) {
		return x() == other.x() && y() == other.y() && z() == other.z();
	}

	/** Gets an angle between this vector and the given vector in radians*/
	default double angle(Vec3 vec) {
		double angle = this.dot(vec) / Math.sqrt(this.lengthSq() * vec.lengthSq());
		angle = Math.acos(angle);
		return angle;
	}

	/**
	 * Returns a mutable copy of this object.
	 **/
	default MVec3 copy() {
		return new BasicVec3(this);
	}
}
