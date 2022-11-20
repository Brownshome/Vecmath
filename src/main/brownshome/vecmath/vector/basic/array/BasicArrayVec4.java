package brownshome.vecmath.vector.basic.array;

import brownshome.vecmath.rotation.array.ArrayRot3;
import brownshome.vecmath.vector.Vec4;
import brownshome.vecmath.vector.layout.Vec4Layout;

public final class BasicArrayVec4 extends BasicArrayVec<Vec4Layout, Vec4> implements ArrayRot3 {
	public BasicArrayVec4(double[] array, Vec4Layout layout) {
		super(array, layout);
	}

	private BasicArrayVec4(BasicArrayVec4 copy) {
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
	public double w() {
		return get(3);
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
	public void w(double w) {
		set(w, 3);
	}

	@Override
	public BasicArrayVec4 copy() {
		return new BasicArrayVec4(this);
	}
}
