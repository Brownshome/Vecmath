package brownshome.vecmath.vector.basic.array;

import brownshome.vecmath.rotation.array.ArrayRot2;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.layout.Vec2Layout;

public final class BasicArrayVec2 extends BasicArrayVec<Vec2Layout, Vec2> implements ArrayRot2 {
	public BasicArrayVec2(double[] array, Vec2Layout layout) {
		super(array, layout);
	}

	private BasicArrayVec2(BasicArrayVec2 copy) {
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
	public void x(double x) {
		set(x, 0);
	}

	@Override
	public void y(double y) {
		set(y, 1);
	}

	@Override
	public BasicArrayVec2 copy() {
		return new BasicArrayVec2(this);
	}
}
