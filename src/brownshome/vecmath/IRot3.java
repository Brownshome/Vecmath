package brownshome.vecmath;

public class IRot3 extends IVec4 implements Rot3 {
	public IRot3(double x, double y, double z, double w) {
		super(x, y, z, w);
	}
	
	public IRot3(Vec3 axis, double angle) {
		assert false; //TODO
	}
	
	public IRot3() {
		this(0, 0, 0, 1);
	}
	
	public IRot3(Rot3 r) {
		this(r.x(), r.y(), r.z(), r.w());
	}

	@Override
	public String toString() {
		return String.format("(%.3f, %.3f, %.3f, %.3f)", x(), y(), z(), w());
	}
}
