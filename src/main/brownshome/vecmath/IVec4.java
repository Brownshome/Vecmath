package brownshome.vecmath;

public class IVec4 implements Vec4 {
	public static final IVec4 ZERO = new IVec4(0, 0, 0, 0);
	
	private final double x, y, z, w;
	
	public IVec4(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public IVec4(Vec4 copy) {
		this.x = copy.x();
		this.y = copy.y();
		this.z = copy.z();
		this.w = copy.w();
	}
	
	@Override
	public final double x() {
		return x;
	}

	@Override
	public final double y() {
		return y;
	}
	
	@Override
	public final double z() {
		return z;
	}
	
	@Override
	public final double w() {
		return w;
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f, %.3f, %.3f)", x(), y(), z(), w());
	}

	/**
	 * Returns an immutable version of this object.
	 **/
	@Override
	public IVec4 immutable() {
		return this;
	}
}
