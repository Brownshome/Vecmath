package brownshome.vecmath.vector.basic;

import brownshome.vecmath.rotation.MRot2;
import brownshome.vecmath.vector.Vec2;

public final class BasicVec2 extends BasicVec<Vec2> implements MRot2 {
	private double x, y;
	
	public BasicVec2(Vec2 copy) {
		this(copy.x(), copy.y());
	}
	
	public BasicVec2(double x, double y) {
		this.x = x;
		this.y = y;
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
	public void x(double x) {
		this.x = x;
	}
	
	@Override
	public void y(double y) {
		this.y = y;
	}
}
