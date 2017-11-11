package brownshome.vecmath;

public class IRot3 extends IVec4 implements Rot3 {
	public IRot3(double x, double y, double z, double w) {
		super(x, y, z, w);
	}
	
	public static IRot3 fromAxisAngle(Vec3 axis, double angle) {
		angle = angle / 2;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		return new IRot3(axis.x() * sin, axis.y() * sin, axis.z() * sin, cos);
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
