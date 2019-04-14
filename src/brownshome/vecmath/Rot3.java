package brownshome.vecmath;

/** Represents a 3 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else. */
public interface Rot3 extends Vec4 {
	/**
	 * Rotates a given vector by this rotation.
	 * @param v The vector to rotate
	 */
	default void rotate(MVec3 v) {
		// This might be slower than expanding the expression manually. Benchmark if needed.
		// Evaluate p` = qpq*

		MRot3 conj = new MRot3(this);
		conj.conj();

		Rot3 point = new IRot3(v);

		conj.multiplyLeft(point);
		conj.multiplyLeft(this);

		v.set(point.x(), point.y(), point.z());
	}
	
	/** Finds the angle between two quaternions */
	default double angleTo(Rot3 o) {
		double dot = dot(o);
		return Math.acos(2 * dot * dot - 1);
	}
}
