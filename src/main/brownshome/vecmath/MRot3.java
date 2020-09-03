package brownshome.vecmath;

public class MRot3 extends MVec4 implements Rot3 {
	/** 
	 * When the cosine of the angle between two quaternions is greater than this they will be linearly interpolated instead of
	 * slerp.
	 */
	private static final double SLERP_INTERPOLATE = 0.99995;

	/**
	 * Produces a quaternion that represents a rotation by the supplied axis angle combination.
	 * @param axis The axis to rotate by
	 * @param angle The angle to rotate by. Looking along the axis, this is clockwise in a right-handed coordinate system
	 *              and counter-clockwise in a left-handed system.
	 * @return A rotation representing the rotation by the axis-angle pair.
	 */
	public static MRot3 fromAxisAngle(Vec3 axis, double angle) {
		return IRot3.fromAxisAngle(axis, angle).mutable();
	}
	
	/**
	 * Converts a vec3 into a quaternion. This quaternion is a 'pure' quaternion, and not normalised unless v is normalised
	 * @param v The input vector
	 */
	public MRot3(Vec3 v) {
		super(v.x(), v.y(), v.z(), 0);
	}

	public MRot3(Rot3 copy) {
		super(copy);
	}

	/** Sets this to be rot * this */
	public void multiplyLeft(Rot3 rot) {
		MRot3 tmp = new MRot3(rot);
		tmp.multiplyRight(this);
		set(tmp);
	}
	
	/** Sets this to be this * rot */
	public void multiplyRight(Rot3 rot) {
		set(
				rot.w() * x() + w() * rot.x() + y() * rot.z() - z() * rot.y(),
				rot.w() * y() + w() * rot.y() + z() * rot.x() - x() * rot.z(),
				rot.w() * z() + w() * rot.z() + x() * rot.y() - y() * rot.x(),
				
				rot.w() * w() - x() * rot.x() - y() * rot.y() - z() * rot.z()
		);
	}

	/**
	 * Performs a spherical linear interpolation between this and q, driven by t.
	 * This is set to the result.
	 * @param other The rotation to interpolate to.
	 * @param t The amount to interpolate by. This must be in the range [0,1]
	 */
	public void slerp(Rot3 other, double t) {
		assert t >= 0.0 && t <= 1.0;

		// Taken from https://en.wikipedia.org/wiki/Slerp#Quaternion_Slerp
		double dot = dot(other);
		
		// This is to avoid rotating in the wrong direction
		if(dot < 0.0) {
			dot = -dot;
			scale(-1.0);
		}

		if(dot > SLERP_INTERPOLATE) {
			nLerp(other, t);
			return;
		}

		double ang = Math.acos(dot);
		double interpolatedAng = ang * t;

		double sinAng = Math.sin(ang);
		double sinInterpolatedAng = Math.sin(interpolatedAng);

		double sThis = Math.cos(interpolatedAng) - dot * sinInterpolatedAng / sinAng;
		double sOther = sinInterpolatedAng / sinAng;

		scale(sThis);
		scaleAdd(other, sOther);

		// No normalisation required, except for numerical error
	}

	/**
	 * Performs a normalised linear interpolation between this and q, driven by t.
	 * This is set to the result.
	 * @param other The rotation to interpolate to.
	 * @param t The amount to interpolate by. This must be in the range [0,1]
	 */
	public void nLerp(Rot3 other, double t) {
		double dot = dot(other);
		
		// This is to avoid rotating in the wrong direction, and to avoid issues with lerping through 0
		if(dot < 0.0) {
			dot = -dot;
			scale(-1.0);
		}
		
		lerp(other, t);
		normalize();
	}

	/** Sets this rotation to a rotation representing the opposite transformation. This is the same as the conjugate. */
	public void invert() {
		set(-x(), -y(), -z(), w());
	}
	
	public MRot3(double x, double y, double z, double w) {
		super(x, y, z, w);
	}
	
	/** The identity rotation */
	public MRot3() {
		this(0, 0, 0, 1);
	}
}
