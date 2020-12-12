package brownshome.vecmath;

import brownshome.vecmath.array.ArrayVec3;
import brownshome.vecmath.array.ArrayVec4;

/** An interface representing a 4D vector. This can be used as a type representing a Vec4 that may be edited by the creator by by no-one else. */
public interface Vec4 {
	Vec4 ZERO = new BasicVec4(0, 0, 0, 0);

	static MVec4 of(double x, double y, double z, double w) {
		return new BasicVec4(x, y, z, w);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @return a vector
	 */
	static MVec4 of(double[] array) {
		return of(array, 0);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @return a vector
	 */
	static MVec4 of(double[] array, int offset) {
		return of(array, offset, 1);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @param stride the stride of the components in the array
	 * @return a vector
	 */
	static MVec4 of(double[] array, int offset, int stride) {
		return new ArrayVec4(array, offset, stride);
	}

	double x();
	double y();
	double z();
	double w();
	
	/**
	 * Calculates the distance squared from a this vector to another one
	 * @param position The position to calculate the distance to to
	 */
	default double distanceSq(Vec4 position) {
		double dx = position.x() - x();
		double dy = position.y() - y();
		double dz = position.z() - z();
		double dw = position.w() - w();
		return dx * dx + dy * dy + dz * dz + dw * dw;
	}
	
	/**
	 * Calculates the dot product between this and vec
	 */
	default double dot(Vec4 vec) {
		return vec.x() * x() + vec.y() * y() + vec.z() * z() + vec.w() * w();
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
		return x() * x() + y() * y() + z() * z() + w() * w();
	}
	
	/**
	 * Calculates the distance from a this vector to another one
	 * @param position The position to calculate to
	 */
	default double distance(Vec4 position) {
		return Math.sqrt(position.distanceSq(this));
	}
	
	default boolean exactEquals(Vec4 other) {
		return x() == other.x() && y() == other.y() && z() == other.z() && w() == other.w();
	}

	/** Gets an angle between this vector and the given vector in radians*/
	default double angle(Vec4 vec) {
		double angle = this.dot(vec) / (this.length() * vec.length());
		angle = Math.acos(angle);
		return angle;
	}

	/**
	 * Returns an mutable copy of this object.
	 **/
	default MVec4 copy() {
		return new BasicVec4(this);
	}
}
