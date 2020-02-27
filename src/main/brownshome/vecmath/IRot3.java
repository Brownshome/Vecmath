package brownshome.vecmath;

public class IRot3 extends IVec4 implements Rot3 {
	public static final IRot3 IDENTITY = new IRot3(0, 0, 0, 1);

	public IRot3(double x, double y, double z, double w) {
		super(x, y, z, w);
	}

	/**
	 * Produces a quaternion that represents a rotation by the supplied axis angle combination.
	 * @param axis The axis to rotate by
	 * @param angle The angle to rotate by. Looking along the axis, this is clockwise in a right-handed coordinate system
	 *              and counter-clockwise in a left-handed system.
	 * @return A rotation representing the rotation by the axis-angle pair.
	 */
	public static IRot3 fromAxisAngle(Vec3 axis, double angle) {
		angle = angle / 2;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		return new IRot3(axis.x() * sin, axis.y() * sin, axis.z() * sin, cos);
	}
	
	/**
	 * Converts a vec3 into a quaternion. This quaternion is a 'pure' quaternion, and not normalised unless v is normalised
	 * @param v The input vector
	 */
	public IRot3(Vec3 v) {
		this(v.x(), v.y(), v.z(), 0);
	}
	
	public IRot3(Rot3 r) {
		this(r.x(), r.y(), r.z(), r.w());
	}

	@Override
	public String toString() {
		return String.format("(%.3f, %.3f, %.3f, %.3f)", x(), y(), z(), w());
	}

	/**
	 * Returns an immutable version of this object.
	 **/
	@Override
	public IRot3 immutable() {
		return this;
	}
}
