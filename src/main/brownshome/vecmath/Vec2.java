package brownshome.vecmath;

import brownshome.vecmath.array.ArrayVec2;

/** An interface representing a 2D vector. This can be used as a type representing a Vec2 that may be edited by the creator by by no-one else. */
public interface Vec2 {
	Vec2 ZERO = new BasicVec2(0, 0);
	Vec2 X_AXIS = new BasicVec2(1, 0);
	Vec2 Y_AXIS = new BasicVec2(0, 1);

	static MVec2 of(double x, double y) {
		return new BasicVec2(x, y);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @return a vector
	 */
	static MVec2 of(double[] array) {
		return of(array, 0);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @return a vector
	 */
	static MVec2 of(double[] array, int offset) {
		return of(array, offset, 1);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @param stride the stride of the components in the array
	 * @return a vector
	 */
	static MVec2 of(double[] array, int offset, int stride) {
		return new ArrayVec2(array, offset, stride);
	}

	double x();
	double y();
	
	/**
	 * Calculates the distance squared from a this vector to another one
	 * @param position The position to calculate the distance to to
	 */
	default double distanceSq(Vec2 position) {
		double dx = position.x() - x();
		double dy = position.y() - y();
		return dx * dx + dy * dy;
	}
	
	/**
	 * Calculates the dot product between this and vec
	 */
	default double dot(Vec2 vec) {
		return vec.x() * x() + vec.y() * y();
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
		return x() * x() + y() * y();
	}
	
	/**
	 * Calculates the distance from a this vector to another one
	 * @param position The position to calculate to
	 */
	default double distance(Vec2 position) {
		return Math.sqrt(position.distanceSq(this));
	}
	
	default boolean exactEquals(Vec2 other) {
		return x() == other.x() && y() == other.y();
	}

	/**
	 * Gets an angle between this vector and the given vector in radians. This angle will be positive, and always in the
	 * range [0, pi]
	 **/
	default double angle(Vec2 vec) {
		double angle = this.dot(vec) / (this.length() * vec.length());
		angle = Math.acos(angle);
		return angle;
	}

	/**
	 * Returns an mutable copy of this object.
	 **/
	default MVec2 copy() {
		return new BasicVec2(this);
	}
}
