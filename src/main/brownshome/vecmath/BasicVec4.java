package brownshome.vecmath;

final class BasicVec4 implements MVec4, MRot3 {
	private double x, y, z, w;
	
	BasicVec4(Vec4 copy) {
		this(copy.x(), copy.y(), copy.z(), copy.w());
	}
	
	BasicVec4(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	@Override
	public double x() {
		return x;
	}
	
	@Override
	public double y() {
		return y;
	}
	
	@Override
	public double z() {
		return z;
	}
	
	@Override
	public double w() {
		return w;
	}
	
	@Override
	public void x(double x) {
		this.x = x;
	}
	
	@Override
	public void y(double y) {
		this.y = y;
	}
	
	@Override
	public void z(double z) {
		this.z = z;
	}
	
	@Override
	public void w(double w) {
		this.w = w;
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f, %.3f, %.3f)", x(), y(), z(), w());
	}
}
