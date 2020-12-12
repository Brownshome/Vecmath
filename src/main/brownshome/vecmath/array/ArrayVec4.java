package brownshome.vecmath.array;

import brownshome.vecmath.MRot3;

public final class ArrayVec4 extends ArrayVec implements MRot3 {
	public ArrayVec4(double[] array, int offset, int stride) {
		super(array, offset, stride);

		assert array.length > offset + 3 * stride && offset + 3 * stride > 0;
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
	public void w(double w) {
		set(3, w);
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
}
