package brownshome.vecmath;

public class IVec3 implements Vec3 {
	public static final IVec3 ZERO = new IVec3(0, 0, 0);
	public static final IVec3 X_AXIS = new IVec3(1, 0, 0);
	public static final IVec3 Y_AXIS = new IVec3(0, 1, 0);
	public static final IVec3 Z_AXIS = new IVec3(0, 0, 1);

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

	/**
	 * Returns an immutable version of this object.
	 **/
	@Override
	public IVec3 immutable() {
		return this;
	}
}
