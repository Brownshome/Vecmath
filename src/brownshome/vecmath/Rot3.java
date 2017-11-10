package brownshome.vecmath;

/** Represents a 3 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else. */
public interface Rot3 extends Vec4 {
	default void rotate(MVec3 v) {
		
	}
	
	/** Finds the angle between two quaternions */
	default double angleTo(Rot3 o) {
		double dot = dot(o);
		return Math.acos(2 * dot * dot - 1);
	}
}
