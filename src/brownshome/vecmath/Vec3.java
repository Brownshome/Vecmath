package brownshome.vecmath;

/** An interface representing a 3D vector. This can be used as a type representing a Vec3 that may be edited by the creator by by no-one else. */
public interface Vec3 {
	double x();
	double y();
	double z();
	
	/**
	 * Calculates the distance squared from a this vector to another one
	 * @param position The position to calculate the distance to to
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
	 * Calculates the distance from a this vector to another one
	 * @param position The position to calculate to
	 */
	default double distance(Vec3 position) {
		return Math.sqrt(position.distanceSq(this));
	}
	
	default boolean exactEquals(Vec3 other) {
		return x() == other.x() && y() == other.y() && z() == other.z();
	}

	/**
	 * Returns an immutable version of this object.
	 **/
	default IVec3 immutable() {
		return new IVec3(this);
	}

	/**
	 * Returns an mutable version of this object. This object's changes will effect the original if it was mutable.
	 **/
	default MVec3 mutable() {
		return new MVec3(this);
	}
}
