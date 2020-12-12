package brownshome.vecmath;

/** Represents a 2 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else. */
public interface Rot2 extends Vec2 {
	Rot2 IDENTITY = new BasicVec2(1, 0);

	static MRot2 fromAngle(double angle) {
		return new BasicVec2(Math.cos(angle), Math.sin(angle));
	}

	static MRot2 of(double x, double y) {
		return new BasicVec2(x, y);
	}

	static MRot2 of(Vec2 copy) {
		return new BasicVec2(copy);
	}

	default void rotate(MVec2 v) {
		double x = v.x() * cos() - v.y() * sin();
		double y = v.x() * sin() + v.y() * cos();
		
		v.set(x, y);
	}
	
	/**
	 * Finds arg[ other / this ] or the angle from this rotation to
	 * the other rotation. This differs from {@link #angle(Vec2)} in that
	 * it can return negative values for the angle. The result is in the range [ -pi, pi ]
	 **/
	default double angleTo(Rot2 o) {
		//Looks terrible, but only uses one atan call.
		//It is a simplification of arg[(o.x + i * o.y) / (x + i * y)]
		return Math.atan2(x() * o.y() - y() * o.x(), dot(o));
	}
	
	default double sin() {
		return y();
	}

	default double cos() {
		return x();
	}

	/**
	 * Returns an mutable copy of this object.
	 **/
	@Override
	default MRot2 copy() {
		return new BasicVec2(this);
	}
}
