package brownshome.vecmath.array;

abstract class ArrayVec {
	private final double[] array;
	private final int offset;
	private final int stride;

	ArrayVec(double[] array, int offset, int stride) {
		this.array = array;
		this.offset = offset;
		this.stride = stride;
	}

	final double get(int i) {
		return array[i * stride + offset];
	}

	final void set(int i, double value) {
		array[i * stride + offset] = value;
	}
}
