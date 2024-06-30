package brownshome.vecmath.complex.layout;

import brownshome.vecmath.basic.layout.BasicMatrixLayout;
import brownshome.vecmath.complex.generic.GenericComplexLayout;
import brownshome.vecmath.complex.wrapped.layout.ComponentGenericComplexLayout;
import brownshome.vecmath.complex.wrapped.layout.SubGenericComplexLayout;
import brownshome.vecmath.complex.wrapped.layout.WrappedGenericComplexLayout;
import brownshome.vecmath.vector.layout.VecNLayout;

/**
 * The layout of an array-backed arbitrary-length complex vector
 */
public interface ComplexNLayout extends GenericComplexLayout {
	/**
	 * Gets an optimal layout
	 * @param size the number of elements in this complex vector
	 * @return a layout
	 */
	static ComplexNLayout ofOptimal(int size) {
		return of(size);
	}

	/**
	 * Gets a layout with zero offset and two stride.
	 * @param size the number of elements in this complex vector
	 * @return a layout
	 */
	static ComplexNLayout of(int size) {
		return of(size, 0);
	}

	/**
	 * Gets a vector layout with a given offset with a stride of 2 and an imaginary offset of 1
	 * @param size the number of elements in this complex vector
	 * @param offset the offset
	 * @return a layout
	 */
	static ComplexNLayout of(int size, int offset) {
		return of(size, offset, 1, 2);
	}

	/**
	 * Gets a vector layout with a given offset, imaginary offset, and stride
	 * @param size the number of elements in this complex vector
	 * @param offset the offset
	 * @param imaginaryOffset the offset of the imaginary component of this layout
	 * @param stride the stride
	 * @return a layout
	 */
	static ComplexNLayout of(int size, int offset, int imaginaryOffset, int stride) {
		return new BasicMatrixLayout(size, 2, offset, stride, imaginaryOffset);
	}

	default VecNLayout real() {
		return new ComponentGenericComplexLayout.Real(this);
	}

	default VecNLayout imaginary() {
		return new ComponentGenericComplexLayout.Imaginary(this);
	}

	/**
	 * This layout as a complex layout if it only has one element.
	 * @return a layout
	 */
	default ComplexLayout asComplex() {
		assert complexElements() == 1;
		return new WrappedGenericComplexLayout(this);
	}

	default ComplexLayout asComplex(int index) {
		return new SubGenericComplexLayout(this, index, 1);
	}
}
