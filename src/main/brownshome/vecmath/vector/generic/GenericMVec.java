package brownshome.vecmath.vector.generic;

import brownshome.vecmath.generic.GenericMElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;

/**
 * An internal interface defining many of the shared methods that can be implemented generically
 *
 * @param <VEC_TYPE> the type of this vector
 */
public interface GenericMVec<VEC_TYPE extends GenericVec<VEC_TYPE>> extends GenericMElement<VEC_TYPE>, GenericVec<VEC_TYPE> {

	/**
	 * Sets this vector to the normal. The results are undefined if the length is zero
	 */
	default void setToNormalised() {
		scaleSelf(1.0 / length());
	}

	@Override
	MVecN asUnknownSize();

	@Override
	default MMatrix asRow() {
		return GenericVec.super.asRow().move();
	}

	@Override
	default MMatrix asColumn() {
		return GenericVec.super.asColumn().move();
	}
}
