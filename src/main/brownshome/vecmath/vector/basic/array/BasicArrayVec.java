package brownshome.vecmath.vector.basic.array;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.generic.GenericArrayVec;
import brownshome.vecmath.vector.basic.BasicVec;
import brownshome.vecmath.vector.generic.GenericVec;
import brownshome.vecmath.vector.generic.GenericVecLayout;

abstract class BasicArrayVec<
		LAYOUT_TYPE extends GenericVecLayout,
		VEC_TYPE extends GenericVec<VEC_TYPE>> extends BasicVec<VEC_TYPE> implements GenericArrayVec<LAYOUT_TYPE, VEC_TYPE> {
	private final double[] array;
	private final LAYOUT_TYPE layout;

	BasicArrayVec(double[] array, LAYOUT_TYPE layout) {
		this.array = array;
		this.layout = layout;

		assert layout.start() < array.length;
		assert layout.end() <= array.length;
	}

	BasicArrayVec(BasicArrayVec<LAYOUT_TYPE, VEC_TYPE> copy) {
		this(copy.array.clone(), copy.layout);
	}

	@Override
	public final LAYOUT_TYPE layout() {
		return layout;
	}

	@Override
	public final double[] backingArray() {
		return array;
	}

	public final double get(int i) {
		return array[layout.arrayIndex(i)];
	}

	public final void set(double value, int i) {
		array[layout.arrayIndex(i)] = value;
	}

	@Override
	public MMatrix asRow() {
		return Matrix.of(array, layout.asRowMatrix());
	}

	@Override
	public MMatrix asColumn() {
		return Matrix.of(array, layout.asColumnMatrix());
	}
}
