package brownshome.vecmath;

public class IVec3 implements Vec3 {
	public static final IVec3 ZERO = new IVec3(0, 0, 0);
	
	private final double x, y, z;
	
	public IVec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public IVec3(Vec3 copy) {
		this.x = copy.x();
		this.y = copy.y();
		this.z = copy.z();
	}
	
	@Override
	public final double x() {
		return x;
	}

	@Override
	public final double y() {
		return y;
	}
	
	public final double z() {
		return z;
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f, %.3f)", x(), y(), z());
	}
}
