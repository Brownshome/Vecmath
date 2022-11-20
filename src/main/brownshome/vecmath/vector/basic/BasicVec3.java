package brownshome.vecmath.vector.basic;

import brownshome.vecmath.vector.MVec3;
import brownshome.vecmath.vector.Vec3;

public final class BasicVec3 extends BasicVec<Vec3> implements MVec3 {
	private double x, y, z;
	
	public BasicVec3(Vec3 copy) {
		this(copy.x(), copy.y(), copy.z());
	}
	
	public BasicVec3(double x, double y, double z) {
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
}
