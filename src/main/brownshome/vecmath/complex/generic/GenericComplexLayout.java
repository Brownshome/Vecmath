package brownshome.vecmath.complex.generic;

import brownshome.vecmath.complex.wrapped.layout.WrappedGenericComplexLayout;
import brownshome.vecmath.generic.ElementLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;

/**
 * The shared layout interface for all array-backed complex vectors
 */
public interface GenericComplexLayout extends ElementLayout {
	/**
	 * The array index of the real component of the given index
	 * @param index the index
	 * @return the array index
	 */
	int realArrayIndex(int index);

	/**
	 * The array index of the imaginary component of the given index
	 * @param index the index
	 * @return the array index
	 */
	int imaginaryArrayIndex(int index);

	/**
	 * The number of elements in this complex vector
	 * @return the number of elements
	 */
	int complexElements();

	/**
	 * Whether each element is packed
	 * @return true if the elements are packed
	 */
	boolean isElementPacked();

	/**
	 * Whether all the components of the same type (imaginary or real) are packed together
	 * @return true if all real and imaginary values are packed
	 */
	boolean isComponentPacked();

	/**
	 * Gets this layout as two rows, the first row being real and the second row being imaginary
	 * @return a layout
	 */
	default MatrixLayout asComplexRowMatrix() {
		return new WrappedGenericComplexLayout(this).transpose();
	}

	/**
	 * Gets this layout as two columns, the first column being real and the second column being imaginary
	 * @return a layout
	 */
	default MatrixLayout asComplexColumnMatrix() {
		return new WrappedGenericComplexLayout(this);
	}
}
