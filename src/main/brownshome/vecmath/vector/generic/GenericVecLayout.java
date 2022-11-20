package brownshome.vecmath.vector.generic;

import brownshome.vecmath.generic.ElementLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;

public interface GenericVecLayout extends ElementLayout {
	/**
	 * The array index of a particular item of this vector in the backing array
	 * @param index the index
	 * @return the array index
	 */
	int arrayIndex(int index);

	/**
	 * Gets this vector layout as a row matrix
	 * @return a layout
	 */
	MatrixLayout asRowMatrix();

	/**
	 * Gets this vector layout as a column matrix
	 * @return a layout
	 */
	MatrixLayout asColumnMatrix();
}
