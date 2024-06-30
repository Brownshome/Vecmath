package brownshome.vecmath.complex.layout;

import brownshome.vecmath.basic.layout.BasicColumnLayout;
import brownshome.vecmath.complex.generic.GenericComplexLayout;
import brownshome.vecmath.complex.wrapped.layout.WrappedGenericComplexLayout;
import brownshome.vecmath.vector.layout.Vec2Layout;

/**
 * The layout of an array-backed complex number
 */
public interface ComplexLayout extends GenericComplexLayout, Vec2Layout {
	/**
	 * Gets the optimal layout for this system
	 * @return a layout
	 */
	static ComplexLayout ofOptimal() {
		return of(0);
	}

	/**
	 * Gets a packed layout for this system
	 * @return a layout
	 */
	static ComplexLayout ofPacked() {
		return of(0);
	}

	/**
	 * Gets a layout with a given offset and a stride of 1
	 * @param offset the offset
	 * @return a layout
	 */
	static ComplexLayout of(int offset) {
		return of(offset, 1);
	}

	/**
	 * Gets a layout with a given offset and stride. The stride must not be zero
	 * @param offset the offset
	 * @param stride the stride
	 * @return a layout
	 */
	static ComplexLayout of(int offset, int stride) {
		return new BasicColumnLayout(2, offset, stride);
	}

	@Override
	default int complexElements() {
		return 1;
	}

	@Override
	default boolean isComponentPacked() {
		return true;
	}

	@Override
	default ComplexLayout asComplex() {
		return this;
	}

	@Override
	default int arrayIndex(int index) {
		assert index < elements();
		return index == 0 ? realArrayIndex(0) : imaginaryArrayIndex(0);
	}

	/**
	 * Gets this layout as an arbitrary-length complex vector layout
	 * @return a layout
	 */
	default ComplexNLayout asComplexN() {
		return new WrappedGenericComplexLayout(this);
	}
}
