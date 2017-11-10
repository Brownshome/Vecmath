package brownshome.vecmath;

/** Represents a 2 dimensional rotation. Use this interface to represent a rotation that may be edited by the creator and no-one else. */
public interface Rot2 extends Vec2 {
	default void rotate(MVec2 v) {
		double x = v.x() * cos() - v.y() * sin();
		double y = v.x() * sin() + v.y() * cos();
		
		v.set(x, y);
	}
	
	/** Finds arg[ other / this ] or the angle from this rotation to
	 * the other rotation */
	default double angleTo(Rot2 o) {
		//Looks terrible, but only uses one atan call.
		//It is a simplification of arg[(o.x + i * o.y) / (x + i * y)]
		return Math.atan2(x() * o.y() - y() * o.x(), dot(o));
	}
	
	@Override
	default double x() {
		return cos();
	}
	
	@Override
	default double y() {
		return sin();
	}
	
	double sin();
	double cos();
}
