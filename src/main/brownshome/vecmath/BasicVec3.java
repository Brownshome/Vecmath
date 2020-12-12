package brownshome.vecmath;

final class BasicVec3 implements MVec3 {
	private double x, y, z;
	
	BasicVec3(Vec3 copy) {
		this(copy.x(), copy.y(), copy.z());
	}
	
	BasicVec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	public String toString() {
		return String.format("(%.3f, %.3f, %.3f)", x(), y(), z());
	}
}
