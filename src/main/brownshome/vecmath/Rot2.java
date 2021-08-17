package brownshome.vecmath;

import brownshome.vecmath.array.ArrayVec2;

/** Represents a 2 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else. */
public interface Rot2 extends Vec2 {
	/**
	 * A rotation of zero angle
	 */
	Rot2 IDENTITY = new BasicVec2(1, 0);

	static MRot2 fromAngle(double angle) {
		return new BasicVec2(Math.cos(angle), Math.sin(angle));
	}

	static MRot2 of(double x, double y) {
		return new BasicVec2(x, y);
	}

	/**
	 * Copies the components of a Vec2 into a rotation that rotates (1, 0) to this vector
	 * @param copy the vector to copy the components from
	 * @return a rotation
	 */
	static MRot2 of(Vec2 copy) {
		return new BasicVec2(copy);
	}

	/**
	 * Creates a rotation from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @return a rotation
	 */
	static MRot2 of(double[] array) {
		return of(array, 0);
	}

	/**
	 * Creates a rotation from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @return a rotation
	 */
	static MRot2 of(double[] array, int offset) {
		return of(array, offset, 1);
	}

	/**
	 * Creates a rotation from the components in this array. Edits to the array will change the rotation and vice-versa
	 * @param array the array
	 * @param offset the index of the first component in the array
	 * @param stride the stride of the components in the array
	 * @return a rotation
	 */
	static MRot2 of(double[] array, int offset, int stride) {
		return new ArrayVec2(array, offset, stride);
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
		//Looks terrible, but only uses one arc-tan call.
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
	 * Returns a mutable copy of this object.
	 **/
	@Override
	default MRot2 copy() {
		return new BasicVec2(this);
	}
}
