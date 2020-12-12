package brownshome.vecmath;

/** An interface representing a 4D vector. This can be used as a type representing a Vec4 that may be edited by the creator by by no-one else. */
public interface Vec4 {
	Vec4 ZERO = new BasicVec4(0, 0, 0, 0);

	static MVec4 of(double x, double y, double z, double w) {
		return new BasicVec4(x, y, z, w);
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
