package brownshome.vecmath.vector.generic;

import brownshome.vecmath.generic.ElementLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.VecNLayout;
import brownshome.vecmath.vector.wrapped.layout.WrappedVecLayout;

public interface GenericVecLayout extends ElementLayout {
	/**
	 * The array index of a particular item of this vector in the backing array
	 * @param index the index
	 * @return the array index
	 */
	int arrayIndex(int index);

	/**
	 * Gets this vector layout as an arbitrary-length layout
	 * @return a layout
	 */
	default VecNLayout asVecN() {
		return new WrappedVecLayout(this);
	}

	/**
	 * The number of elements in this vector
	 * @return the number of elements
	 */
	int elements();

	/**
	 * Gets this vector layout as a row matrix
	 * @return a layout
	 */
	default MatrixLayout asRowMatrix() {
		return new WrappedVecLayout(this).transpose();
	}

	/**
	 * Gets this vector layout as a column matrix
	 * @return a layout
	 */
	default MatrixLayout asColumnMatrix() {
		return new WrappedVecLayout(this);
	}
}
