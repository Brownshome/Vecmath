package brownshome.vecmath.vector.generic;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.basic.VecNMatrix;
import brownshome.vecmath.vector.VecN;

/**
 * An internal interface defining many of the shared methods that can be implemented generically
 *
 * @param <VEC_TYPE> the type of this vector
 */
@SuppressWarnings("unchecked")
public interface GenericVec<VEC_TYPE extends GenericVec<VEC_TYPE>> extends GenericElement<VEC_TYPE> {
	/**
	 * The normalised vector in the same direction as this vector. The behaviour of this method is undefined if this vector
	 * is the zero vector.
	 * @return the vector
	 */
	default VEC_TYPE normalised() {
		var result = (GenericMVec<VEC_TYPE>) copy();
		result.setToNormalised();
		return (VEC_TYPE) result;
	}

	/**
	 * Calculates the dot product between this and vec
	 * @param vec the other vector
	 * @return the dot product
	 */
	double dot(VEC_TYPE vec);

	/**
	 * Calculates the distance squared from this vector to another one
	 * @param position The position to calculate the distance to
	 * @return the distance squared to the given position
	 */
	default double distanceSquared(VEC_TYPE position) {
		return subtract(position).lengthSquared();
	}

	/**
	 * Calculates the distance from this vector to another one
	 * @param position The position to calculate to
	 * @return the distance to the other vector
	 */
	default double distance(VEC_TYPE position) {
		return Math.sqrt(distanceSquared(position));
	}

	/**
	 * The length of this vector squared
	 * @return the length squared
	 */
	default double lengthSquared() {
		return dot((VEC_TYPE) this);
	}

	/**
	 * Calculates the length of this vector
	 */
	default double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * Gets an angle between this vector and the given vector in radians. This angle will be positive, and always in the
	 * range [0, pi]
	 * @param vec the other vector
	 * @return the angle between the vectors
	 */
	default double angle(VEC_TYPE vec) {
		double angle = dot(vec) / Math.sqrt(lengthSquared() * vec.lengthSquared());
		angle = Math.acos(angle);
		return angle;
	}

	/**
	 * Returns this vector as a vector of unknown size. The returned vector will reflect changes to this vector
	 */
	VecN asUnknownSize();

	/**
	 * This vector as a row matrix
	 * @return a matrix
	 */
	default Matrix asRow() {
		return new VecNMatrix(asUnknownSize()).transpose();
	}

	/**
	 * This vector as a column matrix
	 * @return a matrix
	 */
	default Matrix asColumn() {
		return new VecNMatrix(asUnknownSize());
	}
}
