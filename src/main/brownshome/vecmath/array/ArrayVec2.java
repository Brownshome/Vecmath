package brownshome.vecmath.array;

import brownshome.vecmath.MRot2;

public final class ArrayVec2 extends ArrayVec implements MRot2 {
	public ArrayVec2(double[] array, int offset, int stride) {
		super(array, offset, stride);

		assert array.length > offset + stride && offset + stride > 0;
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
	public double x() {
		return get(0);
	}

	@Override
	public double y() {
		return get(1);
	}
}
