package brownshome.vecmath.rotation.generic;

import brownshome.vecmath.vector.generic.GenericMVec;
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
public interface GenericMRot<
		TARGET_VEC_TYPE extends GenericVec<TARGET_VEC_TYPE>,
		TARGET_MVEC_TYPE extends TARGET_VEC_TYPE,
		VEC_TYPE extends GenericVec<VEC_TYPE>,
		ROT_TYPE extends VEC_TYPE> extends GenericMVec<VEC_TYPE>, GenericRot<TARGET_VEC_TYPE, TARGET_MVEC_TYPE, VEC_TYPE, ROT_TYPE> {
	/**
	 * Sets this vector to the multiplication of this one and the given rotation. This represents the result of rotating by
	 * this vector then the given one
	 * @param right the vector to multiply by
	 */
	void multiplyRightSelf(ROT_TYPE right);

	/**
	 * Sets this vector to the multiplication of the given rotation and this one. This represents the result of rotating by
	 * the given vector then this one
	 * @param left the vector to multiply by
	 */
	default void multiplyLeftSelf(ROT_TYPE left) {
		set(((GenericRot<TARGET_VEC_TYPE, TARGET_MVEC_TYPE, VEC_TYPE, ROT_TYPE>) left).multiply((ROT_TYPE) this));
	}

	/**
	 * Sets this rotation to a rotation representing the opposite transformation. This is the same as the conjugate.
	 */
	void setToInverted();

	/**
	 * Sets this rotation to the spherical interpolation from this rotation to the other rotation
	 * @param other the rotation to interpolate to
	 * @param t the interpolation factor, this must be between zero and one inclusive
	 */
	default void setToSphericalInterpolation(ROT_TYPE other, double t) {
		final double INTERPOLATE = 0.99995;
		assert t >= 0.0 && t <= 1.0;

		// Taken from https://en.wikipedia.org/wiki/Slerp#Quaternion_Slerp
		double dot = dot(other);

		if (dot < 0.0) {
			dot = -dot;
			setToNegated();
		}

		if (dot > INTERPOLATE) {
			setToNormalisedInterpolation(other, t);
			return;
		}

		double angle = Math.acos(dot);
		double interpolatedAngle = angle * t;
		double sinAngle = Math.sqrt(1.0 - dot * dot);

		scaleSelf(Math.cos(interpolatedAngle) - dot * Math.sin(interpolatedAngle) / sinAngle);
		scaleAddToSelf(other, Math.sin(interpolatedAngle) / sinAngle);
	}

	/**
	 * Sets this rotation to a normalised linear interpolation from this rotation to the other rotation
	 * @param other the rotation to interpolate to
	 * @param t the interpolation factor
	 */
	default void setToNormalisedInterpolation(ROT_TYPE other, double t) {
		if (dot(other) < 0.0) {
			other = (ROT_TYPE) other.negated();
		}

		setToInterpolated(other, t);
		setToNormalised();
	}

	@Override
	default ROT_TYPE move() {
		return (ROT_TYPE) GenericMVec.super.move();
	}
}
