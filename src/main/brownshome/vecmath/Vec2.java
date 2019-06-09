package brownshome.vecmath;

/** An interface representing a 2D vector. This can be used as a type representing a Vec2 that may be edited by the creator by by no-one else. */
public interface Vec2 {
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
	 * Returns an immutable version of this object.
	 **/
	default IVec2 immutable() {
		return new IVec2(this);
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
	default MVec2 mutable() {
		return new MVec2(this);
	}
}
