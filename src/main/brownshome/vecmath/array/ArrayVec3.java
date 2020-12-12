package brownshome.vecmath.array;

import brownshome.vecmath.MVec3;

public final class ArrayVec3 extends ArrayVec implements MVec3 {
	public ArrayVec3(double[] array, int offset, int stride) {
		super(array, offset, stride);

		assert array.length > offset + 2 * stride && offset + 2 * stride > 0;
	}

	@Override
	public void x(double x) {
		set(0, x);
	}

	@Override
	public void y(double y) {
		set(1, y);
	}

	@Override
	public void z(double z) {
		set(2, z);
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
}
