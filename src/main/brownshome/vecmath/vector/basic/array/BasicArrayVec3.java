package brownshome.vecmath.vector.basic.array;

import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.array.ArrayVec3;
import brownshome.vecmath.vector.layout.Vec3Layout;

public final class BasicArrayVec3 extends BasicArrayVec<Vec3Layout, Vec3> implements ArrayVec3 {
	public BasicArrayVec3(double[] array, Vec3Layout layout) {
		super(array, layout);
	}

	private BasicArrayVec3(BasicArrayVec3 copy) {
		super(copy);
	}

	@Override
	public double x() {
		return get(0);
	}

	@Override
	public double y() {
		return get(1);
	}

	@Override
	public double z() {
		return get(2);
	}

	@Override
	public void x(double x) {
		set(x, 0);
	}

	@Override
	public void y(double y) {
		set(y, 1);
	}

	@Override
	public void z(double z) {
		set(z, 2);
	}

	@Override
	public BasicArrayVec3 copy() {
		return new BasicArrayVec3(this);
	}
}
