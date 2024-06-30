package brownshome.vecmath.vector.generic;

import brownshome.vecmath.generic.GenericArrayElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.array.ArrayVecN;

/**
 * An internal interface defining many of the shared methods that can be implemented generically
 *
 * @param <LAYOUT_TYPE> the layout type
 * @param <VEC_TYPE> the type of this vector
 */
@SuppressWarnings("unchecked")
public interface GenericArrayVec<
		LAYOUT_TYPE extends GenericVecLayout,
		VEC_TYPE extends GenericVec<VEC_TYPE>> extends GenericArrayElement<LAYOUT_TYPE, VEC_TYPE>, GenericMVec<VEC_TYPE> {
	@Override
	default double dot(VEC_TYPE vec) {
		var cast = (GenericArrayVec<LAYOUT_TYPE, VEC_TYPE>) vec;
		assert layout().isContinuous();
		assert cast.layout().equals(layout());

		double sum = 0.0;
		for (int i = layout().start(); i < layout().end(); i++) {
			sum += backingArray()[i] * cast.backingArray()[i];
		}

		return sum;
	}

	@Override
	default ArrayVecN asUnknownSize() {
		return VecN.of(backingArray(), layout().asVecN());
	}

	@Override
	default MMatrix asRow() {
		return Matrix.of(backingArray(), layout().asRowMatrix());
	}

	@Override
	default MMatrix asColumn() {
		return Matrix.of(backingArray(), layout().asColumnMatrix());
	}
}
