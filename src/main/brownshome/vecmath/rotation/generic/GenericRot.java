package brownshome.vecmath.rotation.generic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.generic.GenericVec;

/**
 * An internal interface defining many of the shared methods that can be implemented generically
 *
 * @param <TARGET_VEC_TYPE> the vector type acted on by this rotation
 * @param <TARGET_MVEC_TYPE> the mutable vector type acted on by this rotation
 * @param <VEC_TYPE> the vector type acted on by this rotation
 * @param <ROT_TYPE> the type of this rotation
 */
@SuppressWarnings("unchecked")
public interface GenericRot<
		TARGET_VEC_TYPE extends GenericVec<TARGET_VEC_TYPE>,
		TARGET_MVEC_TYPE extends TARGET_VEC_TYPE,
		VEC_TYPE extends GenericVec<VEC_TYPE>,
		ROT_TYPE extends VEC_TYPE> extends GenericVec<VEC_TYPE> {
	@Override
	default ROT_TYPE add(VEC_TYPE vec) {
		return (ROT_TYPE) GenericVec.super.add(vec);
	}

	@Override
	default ROT_TYPE subtract(VEC_TYPE vec) {
		return (ROT_TYPE) GenericVec.super.subtract(vec);
	}

	@Override
	default ROT_TYPE scale(double scale) {
		return (ROT_TYPE) GenericVec.super.scale(scale);
	}

	@Override
	default ROT_TYPE scale(VEC_TYPE scale) {
		return (ROT_TYPE) GenericVec.super.scale(scale);
	}

	@Override
	default ROT_TYPE scaleAdd(VEC_TYPE vec, double scale) {
		return (ROT_TYPE) GenericVec.super.scaleAdd(vec, scale);
	}

	@Override
	default ROT_TYPE normalised() {
		return (ROT_TYPE) GenericVec.super.normalised();
	}

	@Override
	default ROT_TYPE negated() {
		return (ROT_TYPE) GenericVec.super.negated();
	}

	@Override
	default ROT_TYPE interpolated(VEC_TYPE other, double t) {
		return (ROT_TYPE) GenericVec.super.interpolated(other, t);
	}

	/**
	 * The angle of this rotation in the range [0, pi]
	 * @return an angle in radians
	 */
	double angle();

	/**
	 * The given vector rotated by this rotation
	 * @param vec the vector to rotate
	 * @return the rotated vector
	 */
	default TARGET_VEC_TYPE rotated(TARGET_VEC_TYPE vec) {
		var result = (TARGET_MVEC_TYPE) vec.copy();
		setToRotated(result);
		return result;
	}

	/**
	 * Rotates the given vector
	 * @param vec the vector to rotate
	 */
	void setToRotated(TARGET_MVEC_TYPE vec);

	/**
	 * The multiplication between this rotation and the given rotation. This represents the rotation resulting from applying the given
	 * rotation, then applying this one.
	 * @param rot the rotation to multiply
	 * @return the resulting rotation
	 */
	default ROT_TYPE multiply(ROT_TYPE rot) {
		var result = (GenericMRot<TARGET_VEC_TYPE, TARGET_VEC_TYPE, VEC_TYPE, ROT_TYPE>) copy();
		result.multiplyRightSelf(rot);
		return (ROT_TYPE) result;
	}

	/**
	 * Returns a rotation that inverts this rotation
	 * @return an inverted rotation
	 */
	default ROT_TYPE inverted() {
		var result = (GenericMRot<TARGET_VEC_TYPE, TARGET_VEC_TYPE, VEC_TYPE, ROT_TYPE>) copy();
		result.setToInverted();
		return (ROT_TYPE) result;
	}

	/**
	 * Performs a spherical interpolation from this rotation to the other rotation
	 * @param other the rotation to interpolate to
	 * @param t the interpolation factor, this must be between zero and one inclusive
	 * @return a spherically interpolated vector
	 */
	default ROT_TYPE sphericalInterpolation(ROT_TYPE other, double t) {
		var result = (GenericMRot<TARGET_VEC_TYPE, TARGET_VEC_TYPE, VEC_TYPE, ROT_TYPE>) copy();
		result.setToSphericalInterpolation(other, t);
		return (ROT_TYPE) result;
	}

	/**
	 * Performs a normalised linear interpolation from this rotation to the other rotation
	 * @param other the rotation to interpolate to
	 * @param t the interpolation factor
	 * @return a normalised, linearly interpolated vector
	 */
	default ROT_TYPE normalisedInterpolation(ROT_TYPE other, double t) {
		var result = (GenericMRot<TARGET_VEC_TYPE, TARGET_VEC_TYPE, VEC_TYPE, ROT_TYPE>) copy();
		result.setToNormalisedInterpolation(other, t);
		return (ROT_TYPE) result;
	}

	/**
	 * Gets the angle between this rotation and the given rotation in radians
	 * @param other the other rotation
	 * @return the angle between the rotations in the range [0, pi]
	 */
	default double angularDistance(ROT_TYPE other) {
		var conjugated = ((GenericRot<TARGET_VEC_TYPE, TARGET_VEC_TYPE, VEC_TYPE, ROT_TYPE>) other).multiply(inverted());
		return ((GenericRot<TARGET_VEC_TYPE, TARGET_VEC_TYPE, VEC_TYPE, ROT_TYPE>) conjugated).angle();
	}

	/**
	 * Returns a matrix R such as {@code R * p = p'}
	 */
	Matrix asMatrix();

	@Override
	ROT_TYPE copy();

	@Override
	default ROT_TYPE move() {
		return (ROT_TYPE) GenericVec.super.move();
	}
}
